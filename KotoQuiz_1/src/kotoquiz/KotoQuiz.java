/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kotoquiz;

/**
 *
 * @author cl3
 */
public class KotoQuiz {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // TODO code application logic here
        //定数
        //入力・表示処理モード
        final int EXEC_MODE_QUIZ = 1;//問題処理モード
        final int EXEC_MODE_CHECK = 2;//解答処理モード
        final int QUIZ_MAX = 3;//問題数
        
        //変数
        boolean loopFlg = true;//ループフラグ
        int inputNum = 0;//入力番号
        
        int execMode = EXEC_MODE_QUIZ;//処理モード（問題処理モードで開始）
        int nowQuizNum = 0;//現在出題番号
        int correctAnswer = 0;
        int correctTotal = 0;//正解数
        
        while (loopFlg) {
            switch(execMode){
                case EXEC_MODE_QUIZ:
                    correctAnswer = quiz(nowQuizNum, correctTotal);
                
                    System.out.print("答え＞");
                    
                    execMode = EXEC_MODE_CHECK;
                    
                    break;

                case EXEC_MODE_CHECK:
                    correctTotal = check(inputNum, correctAnswer, correctTotal);
                    
                    System.out.println("");
                    System.out.println("-----------------------------------------------");
                    System.out.print("＜ 1を入力してEnterキーを押してください ＞");
                    
                    //問題を進める
                    nowQuizNum += 1;
               
                    //最大数以上なら
                    if(nowQuizNum >= QUIZ_MAX){
                        loopFlg = false;//ゲームを終了する
                    }
                    
                    execMode = EXEC_MODE_QUIZ;
                    
                    break;
            }
            int tmpInputNum = 0;//入力番号初期化
            try {
                //初期化処理
                final int IMPUT_MAX = 3;//最大入力値
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

            inputNum = tmpInputNum;

            //表示終了の区切り
            System.out.println("");
            System.out.println("######################################################");
            System.out.println("");
        }
        //結果表示
        System.out.println("*****************　ことくいず　*****************");
        System.out.println("");
        System.out.println("　　　　　　あなたの正解数は　"+ correctTotal +"　でした。");
        System.out.println("");
        System.out.println("***********************************************");
        System.out.println("");
        System.out.println("おしまい");
    }
    
    private static int quiz(int nowQuizNum, int correctTotal){
        
        int correctAnswer = 0;
        //見出し：現在の状況を表示する
        System.out.println("*****************　ことくいず　*****************");
        System.out.println("　　　　　　　　　現在、"+ (nowQuizNum + 1) +"問目です。");
        System.out.println("　　　　　　　　現在の正解数は"+ correctTotal +"です。");
        System.out.println("***********************************************");
        System.out.println("");
        System.out.println("＜　問題です　＞");

        //問題を表示する
        switch(nowQuizNum){
            case 0:
                System.out.println("変数の型でint型の最大値はいくつ？");
                System.out.println("");
                System.out.println("-----------------------------------------------");
                System.out.println("1：256　2：98776342　3：2147483647");
                correctAnswer = 3;
                break;
            case 1:
                System.out.println("変数の型を変換するのに使う方法はどれ？");
                System.out.println("");
                System.out.println("-----------------------------------------------");
                System.out.println("1：キャスト　2：スコープ　3：インクリメント");
                correctAnswer = 1;
                break;
            case 2:
                System.out.println("変数aが「4以上でかつ10以下」か「40未満」");
                System.out.println("正しい条件式はどれ？");
                System.out.println("");
                System.out.println("-----------------------------------------------");
                System.out.println("1：(a >= 4 || a < 10) && a < 40");
                System.out.println("2：(a >= 4 || a <= 10) || a < 40");
                System.out.println("3：(a >= 4 && a <= 10) || a < 40");
                correctAnswer = 3;
                break;
        }

        return correctAnswer;
    }
    
    private static int check(int inputNum, int correctAnswer, int correctTotal){
        //解答をチェックする
        if(inputNum == correctAnswer){
            System.out.println("やったね！正解");
            //Images.ImageDisplay.main(args);
             correctTotal += 1;//正解数加算
        }else{
            System.out.println("残念！不正解");
        }

        return correctTotal;
    }
}

