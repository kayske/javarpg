/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//http://kaz.cyteen.nagoya-bunri.ac.jp/advprog1/sampleblock/sampleblock2.html
package block;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferStrategy;
import java.applet.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

public class Block {
        public static void main(String[] args) {
                fr =new JFrame("Block");
                fr.setBounds(100,100,500,500);
                fr.setLayout(null);
                fr.setIgnoreRepaint(true);
                fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                fr.setVisible(true);
                
                Block app = new Block();
        }
        
        static JFrame fr;
        BufferStrategy bufferstrategy;
        Graphics offg;
        int t; // 時刻管理用
        int mouse_x; // マウスの動き監視用
        //int mouse_b; // マウスボタンの状態確認用
        int chk = 0; // マウスのボタンを離すときの処理
        AudioClip s0,s1,s2; // 効果音
        Image img; // ボールの画像
        int rx,ry; // ラケットの座標
        int rw; // ラケットの幅
        int bx,by; // ボールの座標
        int bs; // ボールのサイズ
        int vx,vy; // ボールの進行方向
        double r = 3.0; // ボールの移動量
        int score; // 得点
        int ball; // ボールの状態 0:ラケット上 1:移動中 2:終了画面
        int fieldW = 400; // ゲーム画面の横幅
        int fieldH = 400; // ゲーム画面の高さ
        int border = 10;
        int borderx = 5;
        int bordery = 50;
        int gameX0 = border +borderx; //ゲームフィールドの左上の座標
        int gameY0 = border +bordery;
        int gameX1 = fieldW - border +borderx; //ゲームフィールドの右下の座標
        int gameY1 = fieldH - border +bordery;
        int[][] blocks = new int[4][6]; // ブロック用配列
        int blockw = 60; // ブロックのサイズ
        int blockh = 30;
        int offx = border +10 +borderx; // ブロックの左上隅の位置
        int offy = border +50 +bordery;
        Timer timer;

        Block() {
            fr.createBufferStrategy(3);
            fr.setIgnoreRepaint(true);
            bufferstrategy = fr.getBufferStrategy();

            //s0 = getAudioClip(getCodeBase(),"c18.wav"); //Appletの場合
            // クラスのルートがファイルのルート（パッケージの中）
            //s0 = Applet.newAudioClip(Block.class.getResource("c18.wav"));
            //s1 = Applet.newAudioClip(Block.class.getResource("c22.wav"));
            //s2 = Applet.newAudioClip(Block.class.getResource("c29.wav"));
            // イメージのファイル名を指定して、変数img に読み込みます。
            //img = getImage(getCodeBase(), "ball.gif"); //Appletの場合
            // プロジェクトのルートがファイルのルート（パッケージの外）
            img = Toolkit.getDefaultToolkit().getImage("ball.gif");
            // ゲーム情報の初期化
            startGame();
                            
            TimerTask task = new TimerTask() {
                public void run() {
                    gamerun(); // 移動処理
                    paint();   // 画面描画
                }
            };
         
            timer = new Timer();
            timer.schedule(task,  0L, 15L);
            
            // イベント処理
            fr.addMouseListener(new MouseAdapter() {
                // マウスのボタンを押すときの処理
                public void mouseClicked(MouseEvent e) {
                        chk = 1;
                }
            });
            fr.addMouseMotionListener(new MouseMotionAdapter() {
                // マウスが移動したときの処理
                public void mouseMoved(MouseEvent e) {
                        mouse_x = e.getX();
                }               
            });
        }
        
        // ゲーム情報の初期化
        private void startGame() {
            score = 0;
            rw = 40;
            rx = (gameX0+gameX1)/2 - rw/2;
            ry = 350+bordery;
            vx = 0;
            vy = 0;
            bs = 15;
            bx = rx + rw/2 - bs/2; // ラケットの中心に来るよう微調整
            by = ry -bs; // ラケットの上になるように調整
            ball = 0; // ボールを発射前の状態に設定
            
            // ブロック初期化
            for(int i=0; i<blocks.length; i++) {
                for(int j=0; j<blocks[i].length; j++){
                        blocks[i][j] = 1;
                }
            }
        }

        public void paint() {
             offg = bufferstrategy.getDrawGraphics();
             if(!bufferstrategy.contentsLost()){
                // ダブルバッファ使用時には、オフスクリーン側に先に描画する
                offg.setColor(Color.white);
                offg.fillRect(0, 0, 500, 500); //画面を消去する
                
                offg.setColor(Color.lightGray);
                // 枠の影
                offg.fillRect(0+3+borderx,0+3+bordery,fieldW,border);
                offg.fillRect(0+3+borderx,0+3+bordery,border,fieldH);
                offg.fillRect(0+3+borderx,fieldH-border+3+bordery,fieldW,border);
                offg.fillRect(fieldW-border+3+borderx,0+3+bordery,border,fieldH);
                // ボールに影をつける
                offg.fillOval(bx+3,by+3,bs,bs);
                // ラケットに影をつける
                offg.fillRect(rx+3,ry+3,rw,10);

                // 赤枠
                offg.setColor(Color.red);
                offg.fillRect(0+borderx,0+bordery,fieldW,border);
                offg.fillRect(0+borderx,0+bordery,border,fieldH);
                offg.fillRect(0+borderx,fieldH-border+bordery,fieldW,border);
                offg.fillRect(fieldW-border+borderx,0+bordery,border,fieldH);                
                // ボール
                offg.drawImage(img, bx, by,fr);
                // ラケット
                offg.setColor(Color.blue);
                offg.fillRect(rx,ry,rw,10);
                
                // ブロック
                for(int y=0; y<blocks.length; y++) {
                        for(int x=0; x<blocks[y].length; x++) {
                                if(blocks[y][x] == 1) { // ブロックが存在していたら
                                        if((y+x)%2==0) { // 市松模様に着色
                                                offg.setColor(Color.orange);
                                        } else {
                                                offg.setColor(Color.cyan);                                              
                                        }
                                        offg.fill3DRect(
                                                        offx +blockw*x,
                                                                        offy +blockh*y,
                                                        blockw, blockh, true);
                                        offg.fill3DRect(
                                                        offx +blockw*x+1,
                                                                        offy +blockh*y+1,
                                                        blockw-2, blockh-2, true);
                                }
                        }
                }
                
                //スコア表示
                offg.setColor(Color.black);
                offg.drawString("SCORE: "+score, 420,50);
                  } // contentsLost checked
                  
                  bufferstrategy.show(); // オフスクリーンを描画
                  offg.dispose();
        }

      public void gamerun() {
                t = t + 1; // 時刻管理変数更新

                // ゲーム開始時に効果音2種再生
                //if (t == 10) s0.play();
                //if (t == 20) s1.play();

                // ラケットの移動
                if (ball != 2) { // 終了画面以外でラケット移動可能
                        rx = mouse_x - rw / 2;
                }
                // ラケットの移動範囲の制限
                if (rx < gameX0) {
                        rx = gameX0;
                } else if (rx + rw > gameX1) {
                        rx = gameX1 - rw;
                }

                //ゲーム再開判定
                if (ball == 2 && chk == 1) {
                        chk = 0; // マウスボタンのクリック処理用
                        startGame();
                        ball = 0;
                }
                //ゲームオーバー画面
                if (ball == 2 && chk == 0) {
                        return;
                }

                // ボールの発射判定
                // chk はマウスのボタンを一度離してから押したことの確認用
                if (ball == 0 && chk == 1) { // ボールが発射前で、マウスボタンが押されたら発射
                        chk = 0;
                        ball = 1;
                        // ボールの移動方向設定
                        double d = 45.0; // 角度の指定
                        vx = (int) (r * Math.cos(d * Math.PI / 180));
                        vy = (int) (-r * Math.sin(d * Math.PI / 180));

                        //s0.play();
                }

                // ボールの移動
                if (ball == 0) { // ボールが停止状態
                        bx = rx + rw / 2 - bs / 2;
                        by = ry - bs;
                } else if (ball == 1) { // ボールが発射状態
                        bx += vx;
                        by += vy;
                }

                // 壁とボールの衝突処理
                // 左右の壁
                if (bx < gameX0 || bx + bs > gameX1) {
                        bx -= vx * 2;
                        vx = -vx; // 移動方向反転

                        //s1.play();
                }

                if (by < gameY0) {
                        by -= vy * 2;
                        vy = -vy; // 移動方向反転

                        //s1.play();
                }

                if (by + bs > gameY1) { // ミス
                        ball = 2;
                        chk = 0;
                        //s2.play();
                }

                // ラケットとの衝突処理
                // ラケットの部位を3つに分け、打ち返す角度に変化をつける
                if (by + bs > ry && by + bs < ry + r && by>0) {
                        // ラケットの縦方向の位置にボールが侵入
                        int bc = bx + bs / 2; // ボールの中心
                        if (bc > rx && bc < rx + rw / 4) {
                                // 左端で打った
                                hit(30.0);
                        } else if (bc > rx + rw * 3 / 4 && bc < rx + rw) {
                                // 右端で打った
                                hit(30.0);
                        } else if (bc >= rx + rw / 4 && bc <= rx + rw * 3 / 4) {
                                // 中央で打った
                                hit(45.0);
                        }
                }

                // ブロックとの衝突判定
                for (int y = 0; y < blocks.length; y++) {
                        for (int x = 0; x < blocks[y].length; x++) {
                                if (blocks[y][x] == 1) { // ブロックが存在していたら
                                        int bc = bx + bs / 2;
                                        int bc2 = by + bs / 2;
                                        if (bc > offx + blockw * x
                                                        && // ブロックの幅に入っている
                                                        bc < offx + blockw * (x + 1)
                                                        && (
                                                        // 下面に衝突
                                                        (by < offy + blockh * (y + 1) && by > offy + blockh
                                                                        * y) ||
                                                        // 上面に衝突
                                                        (by + bs > offy + blockh * y && by + bs < offy
                                                                        + blockh * (y + 1)))) {
                                                blocks[y][x] = 0;
                                                by -= vy;
                                                vy = -vy;
                                                //s1.play();
                                                score += 100;
                                        } else
                                        // ブロックの左右のチェック
                                        if (bc2 > offy + blockh * y
                                                        && // ブロックの幅に入っている
                                                        bc2 < offy + blockh * (y + 1)
                                                        && (
                                                        // 右面に衝突
                                                        (bx < offx + blockw * (x + 1) && bx > offx + blockw
                                                                        * x) ||
                                                        // 左面に衝突
                                                        (bx + bs > offx + blockw * x && bx + bs < offx
                                                                        + blockw * (x + 1)))) {
                                                blocks[y][x] = 0;
                                                bx -= vx;
                                                vx = -vx;
                                                //s1.play();
                                                score += 100;
                                        }
                                }
                        }
                }

                //クリア判定
                int sum = 0;
                for (int x = 0; x < blocks[0].length; x++) {
                        for (int y = 0; y < blocks.length; y++) {
                                sum += blocks[y][x];
                        }
                }
                if (sum == 0) {
                        // ブロックが全て消去された場合
                        ball = 2;
                        chk = 0;
                        //s2.play();
                }
        }

        // 引数に角度を指定してボールの移動量を決定
        private void hit(double d) {
                by -= vy * 2;
                int vvx = (int) (r * Math.cos(d * Math.PI / 180));
                vy = (int) (-r * Math.sin(d * Math.PI / 180));
                if (vvx * vx > 0) { // 符号が同じなら
                        vx = vvx;
                } else {
                        vx = -vvx;
                }
                //s0.play();
        }
}
 
