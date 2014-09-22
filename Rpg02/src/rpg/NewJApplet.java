/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rpg;

/**
 *
 * @author cl3
 */
// RPG02 スケルトン（骨格）作り
// 2004/07/10

import java.awt.*;
import java.applet.*;

public class Rpg02 extends Applet implements Runnable{
   Image buffer;//イメージバッファ
   Graphics bufferg;//バックバッファ

   //初期化
   public void init(){
      Thread t;
      //スレッドを開始する
      t = new Thread(this);
      t.start();
      
      //バッファを作成する
      Dimension d = getSize();
      buffer = createImage(d.width,d.height);
   }
   
   //実行
   public void run(){
      try{
         //ここがゲームループ
         while(true){
            action();//処理
            input_key();//キー入力
            repaint();//再描画

            //ちょっと休憩（0.01秒）
            Thread.sleep(10);
         }
      }
      catch(Exception e){
      }
   }
   
   //描画更新
   public void update(Graphics g){
      paint(g);
   }

   //描画
   public void paint(Graphics g){
      //バッファのグラフィックコンテキストを取得する
      if(bufferg == null){
         bufferg = buffer.getGraphics();
      }
      
      //バッファを描画する
      Dimension d = getSize();
      bufferg.setColor(Color.white);
      bufferg.fillRect(0,0,d.width,d.height);

      bufferg.setColor(Color.black);
      bufferg.drawString("RPGを作ろう 第二回",20,100);
      //ウインドウを更新する
      g.drawImage(buffer,0,0,this);
   }


   //処理
   public void action(){
      //まだ何もなし
   }

   //キー入力
   public void input_key(){
      //まだ何もなし
   }



    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
