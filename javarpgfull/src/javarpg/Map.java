/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javarpg;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Map implements Common {
    // マップ
    private int[][] map;
    
    // マップの行数・列数（単位：マス）
    private int row;
    private int col;
    
    // マップ全体の大きさ（単位：ピクセル）
    private int width;
    private int height;
    
    private static BufferedImage chipImage;
    // このマップにいるキャラクターたち
    private Vector charas = new Vector();
    // このマップにあるイベント
    private Vector events = new Vector();
        
    // メインパネルへの参照
    private MainPanel panel;
    // マップファイル名
    private String mapFile;
    // BGM名
    private String bgmName;

    /**
     * コンストラクタ
     * 
     * @param mapFile マップファイル名
     * @param eventFile イベントファイル名
     * @param bgmNo BGM番号
     * @param panel パネルへの参照
     */
    public Map(String mapFile, String eventFile, String bgmName, MainPanel panel) {
        this.mapFile = mapFile;
        this.bgmName = bgmName;

        // マップをロード
        load(mapFile);
        
        // イベントをロード
        loadEvent(eventFile);

        // 初回の呼び出しのみイメージをロード
        if (chipImage == null) {
            loadImage();
        }
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        // �I�t�Z�b�g�����ɕ`��͈͂����߂�
        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX + pixelsToTiles(MainPanel.WIDTH) + 1;
        // �`��͈͂��}�b�v�̑傫�����傫���Ȃ�Ȃ��悤�ɒ���
        lastTileX = Math.min(lastTileX, col);
        
        int firstTileY = pixelsToTiles(-offsetY);
        int lastTileY = firstTileY + pixelsToTiles(MainPanel.HEIGHT) + 1;
        // �`��͈͂��}�b�v�̑傫�����傫���Ȃ�Ȃ��悤�ɒ���
        lastTileY = Math.min(lastTileY, row);
        
        for (int i = firstTileY; i < lastTileY; i++) {
            for (int j = firstTileX; j < lastTileX; j++) {
                int mapChipNo = map[i][j];
                // イメージ上の位置を求める
                // マップチップイメージは16x16を想定
                int cx = (mapChipNo % 16) * CS;
                int cy = (mapChipNo / 16) * CS;
                g.drawImage(chipImage, tilesToPixels(j) + offsetX,
                        tilesToPixels(i) + offsetY, tilesToPixels(j) + offsetX
                                + CS, tilesToPixels(i) + offsetY + CS, cx, cy,
                        cx + CS, cy + CS, panel);
                // (j, i) にあるイベントを描画
                for (int n=0; n<events.size(); n++) {
                    Event event = (Event)events.get(n);
                    // イベントが(j, i)にあれば描画
                    if (event.x == j && event.y == i) {
                        mapChipNo = event.chipNo;
                        cx = (mapChipNo % 16) * CS;
                        cy = (mapChipNo / 16) * CS;
                        g.drawImage(chipImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY,
                                tilesToPixels(j) + offsetX + CS, tilesToPixels(i) + offsetY + CS,
                                cx, cy, cx + CS, cy + CS, panel);
                    }
                }
            }
            
        }
        
        // このマップにいるキャラクターを描画
        for (int n=0; n<charas.size(); n++) {
            Chara chara = (Chara)charas.get(n);
            chara.draw(g, offsetX, offsetY);
        }
        
    }

    /**
     * (x,y)にぶつかるものがあるか調べる。
     * @param x マップのx座標
     * @param y マップのy座標
     * @return (x,y)にぶつかるものがあったらtrueを返す。
     */
    public boolean isHit(int x, int y) {
        // マップチップの5-8行目のマップチップはぶつかる
        if (map[y][x] >= 64 && map[y][x] < 127) {
            return true;
        }
        
        // 他のキャラクターがいたらぶつかる
        for (int i = 0; i < charas.size(); i++) {
            Chara chara = (Chara) charas.get(i);
            if (chara.getX() == x && chara.getY() == y) {
                return true;
            }
        }
        
        // �Ԃ���C�x���g�����邩
        for (int i = 0; i < events.size(); i++) {
            Event event = (Event)events.get(i);
            if (event.x == x && event.y == y) {
                return event.isHit;
            }
        }
        
        // なければぶつからない
        return false;
    }

    /**
     * キャラクターをこのマップに追加する
     * @param chara キャラクター
     */
    public void addChara(Chara chara) {
        charas.add(chara);
    }
    
    /**
     * �L�����N�^�[�����̃}�b�v����폜����
     * @param chara �L�����N�^�[
     */
    public void removeChara(Chara chara) {
        charas.remove(chara);
    }
    
    /**
     * (x,y)にキャラクターがいるか調べる
     * @param x X座標
     * @param y Y座標
     * @return (x,y)にいるキャラクター、いなかったらnull
     */
    public Chara charaCheck(int x, int y) {
        for (int i=0; i<charas.size(); i++) {
            Chara chara = (Chara)charas.get(i);
            if (chara.getX() == x && chara.getY() == y) {
                return chara;
            }
        }
        
        return null;
    }
    
    /**
     * (x,y)�ɃC�x���g�����邩���ׂ�
     * @param x X���W
     * @param y Y���W
     * @return (x,y)�ɂ���C�x���g�A���Ȃ�������null
     */
    public Event eventCheck(int x, int y) {
        for (int i=0; i<events.size(); i++) {
            Event event = (Event)events.get(i);
            if (event.x == x && event.y == y) {
                return event;
            }
        }
        
        return null;
    }

    /**
     * �o�^����Ă���C�x���g���폜����
     * @param event �폜�������C�x���g
     */
    public void removeEvent(Event event) {
        events.remove(event);
    }
    
    public static int pixelsToTiles(double pixels) {
        return (int)Math.floor(pixels / CS);
    }
    
    /**
     * �}�X�P�ʂ��s�N�Z���P�ʂɕύX����
     * @param tiles �}�X�P��
     * @return �s�N�Z���P��
     */
    public static int tilesToPixels(int tiles) {
        return tiles * CS;
    }
    
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public Vector getCharas() {
        return charas;
    }
    
    /**
     * ファイルからマップを読み込む
     * 
     * @param filename 読み込むマップデータのファイル名
     */
    private void load(String filename) {
        try {
            InputStream in = getClass().getResourceAsStream(filename);
            row = in.read();
            col = in.read();
            // マップサイズを設定
            width = col * CS;
            height = row * CS;
            // マップを作成
            map = new int[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    map[i][j] = in.read();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * イベントをロードする
     * @param filename イベントファイル
     */
    private void loadEvent(String filename) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    getClass().getResourceAsStream(filename), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                // 空行は読み飛ばす
                if (line.equals("")) continue;
                // コメント行は読み飛ばす
                if (line.startsWith("#")) continue;
                StringTokenizer st = new StringTokenizer(line, ",");
                // イベント情報を取得する
                // イベントタイプを取得してイベントごとに処理する
                String eventType = st.nextToken();
                if (eventType.equals("CHARA")) {  // キャラクターイベント
                    makeCharacter(st);
                } else if (eventType.equals("TREASURE")) {  // 宝箱イベント
                    makeTreasure(st);
                } else if (eventType.equals("DOOR")) {  // ドアイベント
                    makeDoor(st);
                } else if (eventType.equals("MOVE")) {  // 移動イベント
                    makeMove(st);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadImage() {
        // マップチップのイメージをロード
        try {
            chipImage = ImageIO.read(getClass().getResource("../image/mapchip.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * キャラクターイベントを作成
     */
    private void makeCharacter(StringTokenizer st) {
        // イベントの座標
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        // キャラクタ番号
        int charaNo = Integer.parseInt(st.nextToken());
        // 向き
        int dir = Integer.parseInt(st.nextToken());
        // 移動タイプ
        int moveType = Integer.parseInt(st.nextToken());
        // メッセージ
        String message = st.nextToken();
        // キャラクターを作成
        Chara c = new Chara(x, y, charaNo, dir, moveType, this);
        // メッセージを登録
        c.setMessage(message);
        // キャラクターベクトルに登録
        charas.add(c);
    }
    
    /**
     * 宝箱イベントを作成
     */
    private void makeTreasure(StringTokenizer st) {
        // 宝箱の座標
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        // アイテム名
        String itemName = st.nextToken();
        // 宝箱イベントを作成
        TreasureEvent t = new TreasureEvent(x, y, itemName);
        // 宝箱イベントを登録
        events.add(t);
    }
    
    /**
     * とびらイベントを作成
     */
    private void makeDoor(StringTokenizer st) {
        // とびらの座標
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        // とびらイベントを作成
        DoorEvent d = new DoorEvent(x, y);
        // とびらイベントを登録
        events.add(d);
    }
    
    /**
     * �ړ��C�x���g���쐬
     */
    private void makeMove(StringTokenizer st) {
        // �ړ��C�x���g�̍��W
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        // �`�b�v�ԍ�
        int chipNo = Integer.parseInt(st.nextToken());
        // �ړ���̃}�b�v�ԍ�
        int destMapNo = Integer.parseInt(st.nextToken());
        // �ړ����X���W
        int destX = Integer.parseInt(st.nextToken());
        // �ړ����Y���W
        int destY = Integer.parseInt(st.nextToken());
        // �ړ��C�x���g���쐬
        MoveEvent m = new MoveEvent(x, y, chipNo, destMapNo, destX, destY);
        // �ړ��C�x���g��o�^
        events.add(m);
    }
    
    /**
     * マップをコンソールに表示。デバッグ用。
     */
    public void show() {
        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
    
    /**
     * このマップのBGM名を返す
     * 
     * @return BGM名
     */
    public String getBgmName() {
        return bgmName;
    }
    public String getMapName() {
        return mapFile;
    }
}
