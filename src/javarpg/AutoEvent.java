/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javarpg;

/**
 *
 * @author cl3
 */
//public class AutoEvent extends Event {
public class AutoEvent extends Event {
    // イベントタイプ
    public int autoType;
    // メッセージ
    public String message;
    public MessageWindow messageWindow;

    
    public AutoEvent(int autoType, String message) {
        //super(autoType, message);
        // 上に乗ると移動するようにしたいのでぶつからない（false）に設定
        super(0, 0, 0, false);
        //messageWindow.setMessage(message);
        //this.destMapNo = destMapNo;
    }
    /*
    public String toString() {
        return "MOVE:" + super.toString() + ":" + destMapNo + ":" + destX + ":" + destY;
    }*/
}

