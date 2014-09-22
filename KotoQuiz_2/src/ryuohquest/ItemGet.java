/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ryuohquest;

/**
 *
 * @author cl3
 */
public class ItemGet {
    public static boolean main(String[] args) {
        boolean rainbow = false;
        if(YushaParam.level >= 10){
            System.out.println("ロトのしるし、虹のしずくを手に入れた");
            rainbow = true;
        }else{
            System.out.println("竜王の城へ渡れない！レベルが足りない！");
            rainbow = false;
        }
        return rainbow;
    }
}
