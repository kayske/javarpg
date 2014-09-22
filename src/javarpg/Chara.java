/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javarpg;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Chara implements Common {  
    
    // 移動スピード
    private static final int SPEED = 6;
    
    // 移動確率
    public static final double PROB_MOVE = 0.02;

    // イメージ
    private static BufferedImage charaImage;
    // キャラクター番号
    private int charaNo;
    // 座標
    private int x, y; // 単位：マス
    private int px, py; // 単位：ピクセル
    // 向いている方向（LEFT,RIGHT,UP,DOWNのどれか）
    private int direction;
    // アニメーションカウンタ
    private int count;
    
    
    //  移動中（スクロール中）か
    private boolean isMoving;
    //  移動中の場合の移動ピクセル数
    private int movingLength;
    
    // 移動方法
    private int moveType;
    // はなすメッセージ
    private String message;
    
    // アニメーションスレッド
    private Thread threadAnime;
    
    // マップへの参照
    private Map map;

    public Chara(int x, int y, int charaNo, int direction, int moveType, Map map) {
        this.x = x;
        this.y = y;

        px = x * CS;
        py = y * CS;

        this.charaNo = charaNo;
        this.direction = direction;
        count = 0;
        this.moveType = moveType;
        this.map = map;

        // 初回の呼び出しのみイメージをロード
        if (charaImage == null) {
            loadImage();
        }


        // キャラクターアニメーション用スレッド開始
        threadAnime = new Thread(new AnimationThread());
        threadAnime.start();
    }
    
    public void draw(Graphics g, int offsetX, int offsetY) {
        int cx = (charaNo % 8) * (CS * 2);
        int cy = (charaNo / 8) * (CS * 4);
        // countとdirectionの値に応じて表示する画像を切り替える
        g.drawImage(charaImage, px + offsetX, py + offsetY, px + offsetX + CS, py + offsetY + CS,
            cx + count * CS, cy + direction * CS, cx + CS + count * CS, cy + direction * CS + CS, null);
    }

    /**
     * 移動処理。 
     * @return 1マス移動が完了したらtrueを返す。移動中はfalseを返す。
     */
    public boolean move() {
        switch (direction) {
            case LEFT:
                if (moveLeft()) {
                    // 移動が完了した
                    return true;
                }
                break;
            case RIGHT:
                if (moveRight()) {
                    // 移動が完了した
                    return true;
                }
                break;
            case UP:
                if (moveUp()) {
                    // 移動が完了した
                    return true;
                }
                break;
            case DOWN:
                if (moveDown()) {
                    // 移動が完了した
                    return true;
                }
                break;
        }
        
        // 移動が完了していない
        return false;
    }
    
    /**
     * ���ֈړ�����B
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    private boolean moveLeft() {
        // 1マス先の座標
        int nextX = x - 1;
        int nextY = y;
        if (nextX < 0) {
            nextX = 0;
        }
        // その場所に障害物がなければ移動を開始
        if (!map.isHit(nextX, nextY)) {
            // SPEEDピクセル分移動
            px -= Chara.SPEED;
            if (px < 0) {
                px = 0;
            }
            // 移動距離を加算
            movingLength += Chara.SPEED;
            // 移動が1マス分を超えていてたら
            if (movingLength >= CS) {
                // 移動する
                x--;
                px = x * CS;
                // 移動が完了
                isMoving = false;
                return true;
            }
        } else {
            isMoving = false;
            // 元の位置に戻す
            px = x * CS;
            py = y * CS;
        }
        
        return false;
    }

    /**
     * �E�ֈړ�����B
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    private boolean moveRight() {
        // 1�}�X��̍��W
        int nextX = x + 1;
        int nextY = y;
        if (nextX > map.getCol() - 1) {
            nextX = map.getCol() - 1;
        }
        // ���̏ꏊ�ɏ�Q�����Ȃ���Έړ����J�n
        if (!map.isHit(nextX, nextY)) {
            // SPEED�s�N�Z�����ړ�
            px += Chara.SPEED;
            if (px > map.getWidth() - CS) {
                px = map.getWidth() - CS;
            }
            // �ړ����������Z
            movingLength += Chara.SPEED;
            // �ړ���1�}�X���𒴂��Ă�����
            if (movingLength >= CS) {
                // �ړ�����
                x++;
                px = x * CS;
                // �ړ�������
                isMoving = false;
                return true;
            }
        } else {
            isMoving = false;
            px = x * CS;
            py = y * CS;
        }
        
        return false;
    }

    /**
     * ��ֈړ�����B
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    private boolean moveUp() {
        // 1�}�X��̍��W
        int nextX = x;
        int nextY = y - 1;
        if (nextY < 0) {
            nextY = 0;
        }
        // ���̏ꏊ�ɏ�Q�����Ȃ���Έړ����J�n
        if (!map.isHit(nextX, nextY)) {
            // SPEED�s�N�Z�����ړ�
            py -= Chara.SPEED;
            if (py < 0) py = 0;
            // �ړ����������Z
            movingLength += Chara.SPEED;
            // �ړ���1�}�X���𒴂��Ă�����
            if (movingLength >= CS) {
                // �ړ�����
                y--;
                py = y * CS;
                // �ړ�������
                isMoving = false;
                return true;
            }
        } else {
            isMoving = false;
            px = x * CS;
            py = y * CS;
        }
        
        return false;
    }

    /**
     * ���ֈړ�����B
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    private boolean moveDown() {
        // 1�}�X��̍��W
        int nextX = x;
        int nextY = y + 1;
        if (nextY > map.getRow() - 1) {
            nextY = map.getRow() - 1;
        }
        // ���̏ꏊ�ɏ�Q�����Ȃ���Έړ����J�n
        if (!map.isHit(nextX, nextY)) {
            // SPEED�s�N�Z�����ړ�
            py += Chara.SPEED;
            if (py > map.getHeight() - CS) {
                py = map.getHeight() - CS;
            }
            // �ړ����������Z
            movingLength += Chara.SPEED;
            // �ړ���1�}�X���𒴂��Ă�����
            if (movingLength >= CS) {
                // �ړ�����
                y++;
                py = y * CS;
                // �ړ�������
                isMoving = false;
                return true;
            }
        } else {
            isMoving = false;
            px = x * CS;
            py = y * CS;
        }
        
        return false;
    }
    
    /**
     * キャラクターが向いている方向のとなりにキャラクターがいるか調べる
     * @return キャラクターがいたらそのCharaオブジェクトを返す
     */
    public Chara talkWith() {
        int nextX = 0;
        int nextY = 0;
        // キャラクターの向いている方向の1歩となりの座標
        switch (direction) {
            case LEFT:
                nextX = x - 1;
                nextY = y;
                break;
            case RIGHT:
                nextX = x + 1;
                nextY = y;
                break;
            case UP:
                nextX = x;
                nextY = y - 1;
                break;
            case DOWN:
                nextX = x;
                nextY = y + 1;
                break;
        }
        // その方向にキャラクターがいるか調べる
        Chara chara;
        chara = map.charaCheck(nextX, nextY);
        // キャラクターがいれば話しかけたキャラクターの方へ向ける
        if (chara != null) {
            switch (direction) {
                case LEFT:
                    chara.setDirection(RIGHT);
                    break;
                case RIGHT:
                    chara.setDirection(LEFT);
                    break;
                case UP:
                    chara.setDirection(DOWN);
                    break;
                case DOWN:
                    chara.setDirection(UP);
                    break;
            }
        }
        
        return chara;
    }
    
    /**
     * あしもとに宝箱があるか調べる
     * @return あしもとにあるTreasureEventオブジェクト
     */
    public TreasureEvent search() {
        Event event = map.eventCheck(x, y);
        if (event instanceof TreasureEvent) {
            return (TreasureEvent)event;
        }
        
        return null;
    }
    
    /**
     * 目の前にドアがあるか調べる
     * @return 目の前にあるDoorEventオブジェクト
     */
    public DoorEvent open() {
        int nextX = 0;
        int nextY = 0;
        // キャラクターの向いている方向の1歩となりの座標
        switch (direction) {
            case LEFT:
                nextX = x - 1;
                nextY = y;
                break;
            case RIGHT:
                nextX = x + 1;
                nextY = y;
                break;
            case UP:
                nextX = x;
                nextY = y - 1;
                break;
            case DOWN:
                nextX = x;
                nextY = y + 1;
                break;
        }
        // その方向にドアがあるか調べる
        Event event = map.eventCheck(nextX, nextY);
        if (event instanceof DoorEvent) {
            return (DoorEvent)event;
        }
        
        return null;
    }
    
    private void loadImage() {
        // キャラクターのイメージをロード
        try {
            charaImage = ImageIO.read(
              getClass().getResource("../image/chara.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public int getPx() {
        return px;
    }
    
    public int getPy() {
        return py;
    }
    
    public void setDirection(int dir) {
        direction = dir;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean flag) {
        isMoving = flag;
        // 移動距離を初期化
        movingLength = 0;
    }
    
    public String getMessage() {
        return message;
    }

    /**
     * �L�����N�^�[�̃��b�Z�[�W��Ԃ�
     * @param message ���b�Z�[�W
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public int getMoveType() {
        return moveType;
    }
    
    // アニメーションクラス
    private class AnimationThread extends Thread {
        public void run() {
            while (true) {
                // countを切り替える
                if (count == 0) {
                    count = 1;
                } else if (count == 1) {
                    count = 0;
                }
                
                // 300ミリ秒休止＝300ミリ秒おきに勇者の絵を切り替える
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } 
            }
        }
    }
}

