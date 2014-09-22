/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package net.ligun.gameTest;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;



/**
*
* @author cl3
*/
public class GameMain extends Canvas implements Runnable {
    //ゲームのメインループスレッド
    Thread gameLoop;
    //BufferStrategy用
    BufferStrategy bstrategy;
    //画面サイズ
    private static final int WIDTH = 100,HEIGHT = 100;
    
    public GameMain(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Jpeg();
    }
    static  Image   img;
    public void Jpeg(){
        img = getToolkit().getImage("ayu.jpg");
    }


    public void start(){
        //BufferedStrategy設定
        //バッファの数2つ(ダブルバッファリング)
        createBufferStrategy(2);
        //CanvasからBufferStrategy取得
        bstrategy = getBufferStrategy();
        //描画を自前でやる
        this.setIgnoreRepaint(true);
        //メインループ用のスレッドを作成して実行
        gameLoop = new Thread(this);
        gameLoop.start();
    }
    
    public void run(){
        //ここに描画処理
        while(true){
            //とりあえずは何もしない
            try{
                Thread.sleep(1/60*1000);
                //1/60秒スリープ
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
} 
