/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javatest01;
import java.io.*;
/**
 *
 * @author cl3
 */
public class JavaTest01 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Test.test01(args);
        int attack = 1000;
        int specialPoint = 400;
        
        if(attack >= 900 && attack <= 1000){
            if(specialPoint >= 200 && specialPoint <= 500){
                System.out.println("必殺めろめろ弾発射！！");
            }
        }else if(attack > 1000 || specialPoint >= 1000){
            System.out.println("超必殺らぶらぶ弾発射！！");
        }
        
        int ans = 0;
        
        ans = 3;
        
        System.out.println("音痴\n"
                + "1.aa\n"
                + "2.bb\n"
                + "3.cc\n"
                + "あなたの選んだ番号:" + ans
        );
        switch(ans){
            case 1:
                System.out.println("不正解");
                System.out.println("カラオケ嫌い。鼻歌大好き");
                break;
            case 2:
                System.out.println("正解！！");
                System.out.println("まるで南極、遭難注意！");
                break;
            case 3:
                System.out.println("不正解");
                System.out.println("プロばりに歌が上手くてボイン" +
                        "なんだってば。");
                break;
            default:
                System.out.println("番号を1～3から選んでね");
                break;
        }
        
        //probability(args);

    }
    
    














    
    
}


