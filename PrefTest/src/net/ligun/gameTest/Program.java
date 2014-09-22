/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package net.ligun.gameTest;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;


public class Program extends JApplet {
    public void init(){
        GameMain gameMain = new GameMain();
        this.add(gameMain);
        gameMain.start();
    }
    
    public static void main(String[] args){
        //新しいフレーム(Window)を作成
        JFrame mainFrame = new JFrame();
        //Canvasクラスを継承したGameMainクラスのインスタンスを作成
        GameMain mainCanvas =new GameMain();
        //フレームのタイトルを設定
        mainFrame.setTitle("kanzume project");
        //Xボタン押下時の動作(終了)
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //ウィンドウサイズの固定
        mainFrame.setResizable(false);
        //画面表示
        mainFrame.setVisible(true);
        //フレームにキャンバスを載せる
        mainFrame.getContentPane().add(mainCanvas);
        //フレームサイズをキャンバスに合わせる
        mainFrame.pack();
        //フレームの表示位置を画面中央に置く
        mainFrame.setLocationRelativeTo(null);
        //ゲームのメイン処理スタート
        mainCanvas.start();
    }
} 

