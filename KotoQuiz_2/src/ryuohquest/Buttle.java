/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ryuohquest;
import java.io.*;
/**
 *
 * @author cl3
 */
public class Buttle {
    public static void main(int scenario) {
        // <editor-fold desc="This is my custom fold">
        // </editor-fold>
        
        //誰と戦うか決定
        // <editor-fold desc="...誰と戦うか決定...">
        String name = "";
        int hp = 0;
        int experience = 0;
        int attack = 0;
        int defense = 0;
        int speed = 0;
        
        switch(scenario){
            //パラメータ取得
            case 2:
                name = "スライム";
                hp = 20;
                experience = 10;
                attack = 15;
                defense = 5;
                speed = 4;
                //戦闘前会話
                //なし
                System.out.println("");
                break;
            case 4:
                name = "ドラゴン";
                hp = 50;
                experience = 30;
                attack = 35;
                defense = 10;
                speed = 6;
                //戦闘前会話
                System.out.println("沼地の洞窟...");
                System.out.println("魔法のカギを使って扉を開けた");
                
                break;
            case 6:
                name = "ゴーレム";
                hp = 100;
                experience = 50;
                attack = 70;
                defense = 30;
                speed = 8;
                
                break;
            case 8:
                name = "あくまのきし";
                hp = 150;
                experience = 70;
                attack = 100;
                defense = 50;
                speed = 18;
                
                break;
            case 10:
                name = "竜王";
                hp = 300;
                experience = 100;
                attack = 150;
                defense = 80;
                speed = 21;
                
                break;
            case 12:
                name = "竜王(変身後)";
                hp = 500;
                attack = 200;
                defense = 100;
                speed = 30;
                
                break;
        }
        System.out.println(name+"が現れた!");
        System.out.println("");
        try{ RyuohQuest.inputnext(); }catch (IOException e){ }
        
        // </editor-fold>
        
        //勇者のターン
        final int TURN_YUSHA = 1;
        //敵のターン
        final int TURN_ENEMY = 2;

        //勇者のターンで開始
        int turn = TURN_YUSHA;
        boolean endFlg = true;
        int command = 0;
        String turnName = "";
        int turnHp = 0;
        int offenseSpeed = 0;
        int defenseSpeed = 0;
        int turnAttack = 0;
        int turnDefense = 0;
        int winner = 0;
        
        while(endFlg){
            //コマンド（敵ターンは強制たたかう）
            // <editor-fold desc="...コマンド...">
            switch(turn){
                case TURN_YUSHA:
                    System.out.println(YushaParam.YUSHA_NAME + "のターン");
                    System.out.println("体力：" + YushaParam.hp);
                    System.out.println(" ----------- ");
                    System.out.println("| 1.たたかう |");
                    System.out.println("| 2.にげる   |");
                    System.out.println(" ----------- ");
                    System.out.print("コマンド＞");
                    turnName = YushaParam.YUSHA_NAME;
                    turnHp = hp;
                    offenseSpeed = YushaParam.speed;
                    defenseSpeed = speed;
                    turnAttack = YushaParam.attack;
                    turnDefense = defense;
                    break;
                case TURN_ENEMY:
                    System.out.println(name + "のターン");
                    //本番環境削除
                    System.out.println("体力：" + hp);
                    turnName = name;
                    turnHp = YushaParam.hp;
                    offenseSpeed = speed;
                    defenseSpeed = YushaParam.speed;
                    turnAttack = attack;
                    turnDefense = YushaParam.defense;
                    break;
            }
            // </editor-fold>
            
            //入力
            // <editor-fold desc="...入力...">
            if(turn == TURN_YUSHA){
                int tmpInputNum = 0;//入力番号初期化
                try {
                    //初期化処理
                    final int IMPUT_MAX =2;//最大入力値
                    //キー入力読込処理（int型）
                    java.util.Scanner sc = new java.util.Scanner(System.in);
                    int inputInt = sc.nextInt();
                    //入力値チェックと入力番号への代入
                    if (inputInt > 0 && inputInt <= IMPUT_MAX) {
                        tmpInputNum = inputInt;
                    } else {
                        System.out.println("※　コマンドは" + IMPUT_MAX + "以下で入力して下さい　※　");
                    }
                } catch (Exception e) {
                    System.out.println("※　数字以外は入力しないで下さい　※　");
                }
                command = tmpInputNum;
            }else{
                try{ RyuohQuest.inputnext(); }catch (IOException e){ }
            }
            // </editor-fold>
            
            //敵のターンはたたかうのみ
            if(turn == TURN_ENEMY){
                command = 1;
            }
            
            //にげる
            // <editor-fold desc="...にげる...">
            if(command == 2 && turn == TURN_YUSHA){
                //成功判定（関数）
                boolean escape = false;
                escape = escape(offenseSpeed, defenseSpeed);
                //成功
                if(escape == true){
                    //戦闘の始めに戻る
                    System.out.println(turnName + "は逃げ出した!!");
                    return;
                //失敗
                }else{
                    //敵のターンに移行
                    System.out.println(turnName + "は逃げ出した!!");
                    System.out.println("しかし、回り込まれた!!");
                }   
            }// </editor-fold>
            
            //たたかう
            // <editor-fold desc="...たたかう...">
            if(command == 1){
                System.out.println(turnName + "の攻撃!!");
            
                //回避判定（関数）
                boolean avoid = false;
                avoid = avoid(offenseSpeed, defenseSpeed);
                //回避
                if(avoid == true){
                    System.out.println("しかし、あたらなかった!!");
                    //コマンド入力に行こう
                //攻撃
                }else{
                    //ダメージ（関数）
                    turnHp = damage(turnHp, turnAttack, turnDefense);
                }
                try{ RyuohQuest.inputnext(); }catch (IOException e){ }
            }// </editor-fold>
            
            //戦闘終了判定
            if(turnHp <= 0){
                //終了
                endFlg = false;
                //勇者ターンなら勝利、敵ターンなら敗北
                if(turn == TURN_YUSHA){
                    System.out.println(YushaParam.YUSHA_NAME + "の勝利!!");
                    try{ RyuohQuest.inputnext(); }catch (IOException e){ }
                    winner = TURN_YUSHA;
                }else{
                    System.out.println(YushaParam.YUSHA_NAME + "は戦闘に負けた…");
                    winner = TURN_ENEMY;
                    
                }
            }else{
                System.out.println("");
                //相手ターンに移行
                if(turn == TURN_YUSHA){
                    hp = turnHp;
                    turn = TURN_ENEMY;
                }else{
                    YushaParam.hp = turnHp;
                    turn = TURN_YUSHA;
                }
            }
        }
        
        switch(winner){
            //勝利
            case TURN_YUSHA:
                //経験値獲得
                YushaParam.experience += experience;
                //レベルアップ（関数）
                levelup(YushaParam.experience, YushaParam.level);
            //敗北
            case TURN_ENEMY:
                //GAMEOVER
        }
        YushaParam.hp = YushaParam.maxHp;
        System.out.println("\n");
        
        //再度戦闘するか（関数）
        loopbuttle(name);
        
    }
    
    //にげる成功判定（関数）
    public static boolean escape(int yushaSpeed, int enemySpeed) {
        //40% + ((攻)素早さ - (守)素早さ)*0.05% = 成功確立
        double success = 0.4 + (yushaSpeed - enemySpeed)*0.05;
        double probability = Math.random();
        System.out.println("0.4+("+ yushaSpeed + "-" + enemySpeed +")*0.05="+success +" < "+ probability);
        boolean escape = false;
        escape = probability < success;
        return escape;
    }

    //回避判定（関数）{
    public static boolean avoid(int offenseSpeed, int defenseSpeed) {
        //80% + ((攻)素早さ - (守)素早さ)*0.05% = 攻撃が当たる確立
        double success = 0.8 + (offenseSpeed - defenseSpeed)*0.05;
        double probability = Math.random();
        System.out.println("0.8+("+ offenseSpeed + "-" + defenseSpeed +")*0.05="+success +" > "+ probability);
        boolean avoid = false;
        avoid = probability > success;
        return avoid;
    }
   
    //ダメージ（関数）{
    public static int damage(int turnHp, int turnAttack, int turnDefense) {
        //ダメージ計算
        int damage = 0;
        //(攻)攻撃力 - （守)守備力
        damage = turnAttack - turnDefense;
        if(damage < 0) damage = 0;
        System.out.println(damage + "のダメージ!");
        //相手のHPから引く
        return turnHp = turnHp - damage;
    //}
    }
    
    //レベルアップ（関数）{
    public static void levelup(int experience, int level) {
        int nextLevel = level + 1;
        int nextExperience = nextLevel * nextLevel * 5;
        //経験値が一定以上ならレベルアップ
        if(experience >= nextExperience){
            System.out.println(YushaParam.YUSHA_NAME + "はレベルアップした！ レベル" + nextLevel);
            //各パラメータを加算
            YushaParam.level += 1;
            YushaParam.maxHp += 20;
            System.out.println("体力：" + YushaParam.maxHp);
            YushaParam.hp = YushaParam.maxHp;
            YushaParam.attack += 8;
            System.out.println("攻撃力：" + YushaParam.attack);
            YushaParam.defense += 6;
            System.out.println("守備力：" + YushaParam.defense);
            YushaParam.speed += 2;
            System.out.println("素早さ：" + YushaParam.speed);
            try{ RyuohQuest.inputnext(); }catch (IOException e){ }
        }
    }
    
    //再度戦闘するか（関数）
    public static void loopbuttle(String name){
        System.out.println("もう一度"+ name +"とたたかいますか？");
        System.out.println(" ----------- ");
        System.out.println("| 1.たたかう |");
        System.out.println("| 2.次へ     |");
        System.out.println(" ----------- ");
        System.out.print("＞");
        
        //入力
        // <editor-fold desc="...入力...">
        int tmpInputNum = 0;//入力番号初期化
        int inputNum = 0;
        try {
            //初期化処理
            final int IMPUT_MAX =2;//最大入力値
            //キー入力読込処理（int型）
            java.util.Scanner sc = new java.util.Scanner(System.in);
            int inputInt = sc.nextInt();
            //入力値チェックと入力番号への代入
            if (inputInt > 0 && inputInt <= IMPUT_MAX) {
                tmpInputNum = inputInt;
            } else {
                System.out.println("※　コマンドは" + IMPUT_MAX + "以下で入力して下さい　※　");
            }
        } catch (Exception e) {
            System.out.println("※　数字以外は入力しないで下さい　※　");
        }
        // </editor-fold>
        inputNum = tmpInputNum;
        
        if(inputNum == 1){
            RyuohQuest.scenario -= 1;
        }
    }


}
