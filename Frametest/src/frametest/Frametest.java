/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package frametest;

/**
 *
 * @author cl3
 */
import java.awt.*;
import java.awt.event.*;

public class Frametest extends Frame{
        //画像を変数に入れる
        Image pi=getToolkit().getImage("sura.jpg");
     
        public static void main(String[] args) {
              //フレームの作成
              Frametest f = new Frametest();
              f.setSize(200, 100);
              f.setVisible(true);
              //リスナーの設定
              f.addWindowListener(new Ada());
        }
        public void paint(Graphics g)
        {
               //変数piの画像を座標(50,35)に表示
                g.drawImage(pi,50,35,this);
        }
}
class Ada extends WindowAdapter
{
    public void windowClosing(WindowEvent e){
//閉じるボタンが押されたときの処理
       System.exit(0);
    }
}
