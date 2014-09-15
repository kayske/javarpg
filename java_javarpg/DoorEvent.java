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
public class DoorEvent extends Event {
    /**
     * @param x X座標
     * @param y Y座標
     */
    public DoorEvent(int x, int y) {
        // とびらのチップ番号は18でぶつかる
        super(x, y, 18, true);
    }
    
    /**
     * イベントを文字列に変換（デバッグ用）
     */
    public String toString() {
        return "DOOR:" + super.toString();
    }
}