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
public class TreasureEvent extends Event {
    // 宝箱に入っているアイテム名
    private String itemName;

    /**
     * @param x X座標
     * @param y Y座標
     * @param itemName 手に入るアイテム名
     */
    public TreasureEvent(int x, int y, String itemName) {
        // 宝箱のチップ番号は17でぶつからない
        super(x, y, 194, false);
        this.itemName = itemName;
    }

    /**
     * アイテム名を返す
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * イベントを文字列に変換（デバッグ用）
     */
    public String toString() {
        return "TREASURE:" + super.toString() + ":" + itemName;
    }
}
