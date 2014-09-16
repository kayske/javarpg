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
public class MoveEvent extends Event {
    // 移動先のマップ番号
    public int destMapNo;
    // 移動先のX座標
    public int destX;
    // 移動先のY座標
    public int destY;
    
    public MoveEvent(int x, int y, int chipNo, int destMapNo,
                     int destX, int destY) {
        // 上に乗ると移動するようにしたいのでぶつからない（false）に設定
        super(x, y, chipNo, false);
        this.destMapNo = destMapNo;
        this.destX = destX;
        this.destY = destY;
    }
    
    public String toString() {
        return "MOVE:" + super.toString() + ":" + destMapNo + ":" + destX + ":" + destY;
    }
}
