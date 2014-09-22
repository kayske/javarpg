/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ryuohquest;
//http://nasu-b.nsf.jp/DQ/word1.html

import java.io.IOException;

/**
 *
 * @author cl3
 */
public class Conversation {
    public static void main(int scenario , String YUSHA_NAME) {
        //シナリオナンバー
        switch(scenario){
            //1.王様と会話
            case 1:
                System.out.println("ラダトーム王と会話");
                System.out.print("おお　"+ YUSHA_NAME + "　ゆうしゃロトの　ちをひくものよ！　そなたのくるのをまっておったぞ。\n" +
                        "その　むかし　ゆうしゃロトが　カミから　ひかりのたまをさずかり　まものたちをふうじこめたという。\n" +
                        "しかし　いずこともなくあらわれた　あくまのけしん　りゅうおうが　そのたまを　やみにとざしたのじゃ\n" +
                        "このちに　ふたたびへいわをっ！\n");
                try{ RyuohQuest.inputnext(); }catch (IOException e){ }
                System.out.print("ゆうしゃ　"+ YUSHA_NAME + "よ！　りゅうおうをたおし　そのてから　ひかりのたまをとりもどしてくれ！\n" +
                        "わしからの　おくりものじゃ！　そなたのよこにある　たからのはこを　とるがよい！\n" +
                        "そして　このへやにいる　へいしにきけば　たびのちしきを　おしえてくれよう。\n" +
                        "では　また　あおう！　ゆうしゃ　"+ YUSHA_NAME + "　よ！」");
                try{ RyuohQuest.inputnext(); }catch (IOException e){ }
                break;
            //3.
            case 3:
                System.out.println("よしりーんと会話");
                System.out.println("わたしは　よしりーん。　マイラのおふろから　みなみに４つ　あるき　しらべるがよい。");
                try{ RyuohQuest.inputnext(); }catch (IOException e){ }
                break;
            //5.
            case 5:
                System.out.println("ローラ姫と会話");
                System.out.println("ローラは　"+ YUSHA_NAME + "さまに　おくりものを　しとうございます。\n" +
                        ""+ YUSHA_NAME + "さまを　あいする　わたしの　こころ。　どうか　うけとってくださいませ。\n" +
                        "ああ！　たとえ　はなれて　いても　ローラは　いつも　あなたと　ともに　あります。\n" +
                        "では　"+ YUSHA_NAME + "さま……");
                try{ RyuohQuest.inputnext(); }catch (IOException e){ }
                break;
            //7.
            case 7:
                System.out.println("雨のほこらの老人と会話");
                System.out.println("そなたが　まことの　ゆうしゃか　どうか　ためさせてほしい。\n" +
                        "このちの　どこかに　まものたちを　よびよせる　ぎんのたてごとが　あるときく。\n" +
                        "それを　もちかえったとき　そなたを　ゆうしゃと　みとめ　あまぐものつえを　さずけよう。\n" +
                        "おお　"+ YUSHA_NAME + "！　たてごとを　もってきたな！\n" +
                        "わしは　まっておった。　そなたのような　わかものが　あらわれることを…。\n"+
                        "さあ　たからのはこを　とるがよい。\n");
                try{ RyuohQuest.inputnext(); }catch (IOException e){ }
                break;
            //11.
            case 11:
                System.out.println("竜王と会話");
                System.out.println("おろかものめ！　おもいしるがよい！\n");
                try{ RyuohQuest.inputnext(); }catch (IOException e){ }
                break;
            //13.
            case 13:
                System.out.println("ラダトーム王と会話");
                System.out.println("おお　"+ YUSHA_NAME + "！　すべては　ふるい　いいつたえの　ままで　あった！\n" +
                        "すなわち　そなたこそは　ゆうしゃロトの　ちをひくもの！\n" +
                        "そなたこそ　このせかいを　おさめるに　ふさわしい　おかた　なのじゃ！\n" +
                        "わしに　かわって　このくにを　おさめてくれるな？」\n\n" +
                        "しかし　"+ YUSHA_NAME + "は　いいました。\n" +
                        ""+ YUSHA_NAME + "「いいえ。　わたしの　おさめる　くにが　あるなら　それは　わたしじしんで　さがしたいのです」");
                try{ RyuohQuest.inputnext(); }catch (IOException e){ }
                break;
            //14.
            case 14:
                System.out.println("ローラ姫と会話");
                System.out.println("まってくださいませ。\n\n" +
                        "その　あなたの　たびに　ローラも　おともしとうございます。\n" +
                        "このローラも　つれてって　くださいますわね？\n" +
                        "うれしゅうございます。　ぽっ");
                try{ RyuohQuest.inputnext(); }catch (IOException e){ }
                break;
        }
        
            
    }
}
