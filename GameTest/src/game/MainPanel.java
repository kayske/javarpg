/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.*;
import java.net.*;
//import start.*;
//import game.*;
//import clear.*;
//import over.*;

public class MainPanel extends JPanel implements Runnable{
    AppletContext ac;   // アプレットのコンテキスト
    URL cb;   // HTML ファイルが存在する URL
    Dimension size;   // アプレットの大きさ
    boolean in_game = true;   // ゲーム実行中はtrue
    public int state = 0;   // ゲーム状態（0:表紙，1:ゲーム，2:クリア，3:オーバー，4:終了）
    public int level = 1;   // ゲームレベル
    int old_state = 0;   // 直前のゲーム状態
    StartPanel sp;
    GamePanel gp;
    GameClearPanel gcp;
    GameOverPanel gop;
    Thread td;
    
    // コンストラクタ
    public MainPanel(AppletContext ac1, URL cb1, Dimension size1){
        ac   = ac1;
        cb   = cb1;
        size = size1;
                                // グリッドレイアウト
        setLayout(new GridLayout(1, 1, 0, 0));
                                // ゲームパネルの生成
        sp = new StartPanel(ac, cb, size, this);   // スタート（タイトル）
        add(sp);
                                // スレッドの生成
        td = new Thread(this);
        td.start();
    }
    // ゲームの状態を変更
    public void run(){
        while (in_game) {
            try {
                td.sleep(100);   // 100 ms 毎の実施
            }catch (InterruptedException e) {}
            if (state != old_state) {
                // 前のパネルの削除
                if (old_state == 0)remove(sp);
                else if (old_state == 1)remove(gp);
                else if (old_state == 2)remove(gcp);
                else remove(gop);
                // 新しいパネルの追加
                if (state == 4)   // ゲーム終了
                    in_game = false;
                else {
                    if (state == 0) {   // StartPanel
                        sp = new StartPanel(ac, cb, size, this);
                        add(sp);
                    }
                    else if (state == 1) {   // GamePanel
                        gp = new GamePanel(ac, cb, size, this);
                        add(gp);
                    }
                    else if (state == 2) {   // GameClearPanel
                        gcp = new GameClearPanel(ac, cb, size, this);
                        add(gcp);
                    }
                    else {   // GameOverPanel
                        gop = new GameOverPanel(ac, cb, size, this);
                        add(gop);
                    }
                    validate();
                    old_state = state;
                }
            }
        }
    }
}
