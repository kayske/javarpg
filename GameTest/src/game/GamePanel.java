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
import java.util.Random;
//import main.*;

public class GamePanel extends JPanel implements ActionListener, Runnable
{
AppletContext ac;   // アプレットのコンテキスト
URL cb;   // HTML ファイルが存在する URL
Dimension size;   // アプレットの大きさ
MainPanel mp;
JButton bt1, bt2;
Image b_image;
Random rand;
Thread td;
boolean in_game = true;
int x[], y[];   // 円の位置
// コンストラクタ
public GamePanel(AppletContext ac1, URL cb1, Dimension size1, MainPanel mp1)
{
ac   = ac1;
cb   = cb1;
size = size1;
mp   = mp1;
// レイアウトマネージャの停止
setLayout(null);
// 画像の読み込み
try {
URL url;
if (mp.level == 1)
url = new URL(cb + "game/image/game1.jpg");
else if (mp.level == 2)
url = new URL(cb + "game/image/game2.jpg");
else
url = new URL(cb + "game/image/game3.jpg");
b_image = ac.getImage(url);
}
catch (MalformedURLException me)
{
System.out.println("Bad URL");
}
// ボタンの配置
Font f = new Font("SansSerif", Font.BOLD, 20);
FontMetrics fm = getFontMetrics(f);
String str1 = "ゲームクリア";
int w1 = fm.stringWidth(str1) + 40;
int h1 = fm.getHeight() + 10;
bt1 = new JButton(str1);
bt1.setFont(f);
bt1.addActionListener(this);   // アクションリスナ
bt1.setSize(w1, h1);

String str2 = "ゲームオーバー";
int w2 = fm.stringWidth(str2) + 40;
int h2 = fm.getHeight() + 10;
bt2 = new JButton(str2);
bt2.setFont(f);
bt2.addActionListener(this);   // アクションリスナ
bt2.setSize(w2, h2);

int w = size.width / 2 - (w1 + w2 + 5) / 2;
bt1.setLocation(w, size.height-h1-20);
add(bt1);
bt2.setLocation(w+w1+5, size.height-h1-20);
add(bt2);
// ランダム変数の初期化，初期位置の決定
rand = new Random();
x    = new int [3];
y    = new int [3];
for (int i1 = 0; i1 < 3; i1++) {
x[i1] = rand.nextInt(size.width - 40);
y[i1] = rand.nextInt(size.height - 40);
}
// スレッドの生成
td = new Thread(this);
td.start();
}
// スレッドの実行
public void run()
{
while (in_game) {
try {
if (mp.level == 1)
td.sleep(500);
else if (mp.level == 2)
td.sleep(100);
else
td.sleep(20);
}
catch (InterruptedException e) {}
for (int i1 = 0; i1 < 3; i1++) {
x[i1] = rand.nextInt(size.width - 40);
y[i1] = rand.nextInt(size.height - 40);
}
repaint();
}
}
// 描画
public void paintComponent(Graphics g)
{
super.paintComponent(g);   // 親クラスの描画
g.drawImage(b_image, 0, 0, this);
for (int i1 = 0; i1 < 3; i1++) {
if (i1 == 0)
g.setColor(Color.red);
else if (i1 == 1)
g.setColor(Color.green);
else
g.setColor(Color.gray);
g.fillOval(x[i1], y[i1], 40, 40);
}
}
// ボタンがクリックされたときの処理
public void actionPerformed(ActionEvent e)
{
in_game = false;
if (e.getSource() == bt1)   // ゲームクリア
mp.state = 2;
else   // ゲームオーバー
mp.state = 3;
}
}
