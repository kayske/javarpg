/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javarpg;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

public class MessageWindow {
    // 白枠の幅
    private static final int EDGE_WIDTH = 2;

    // 行間の大きさ
    protected static final int LINE_HEIGHT = 8;
    // 1行の最大文字数
    private static final int MAX_CHAR_IN_LINE = 20;
    // 1ページに表示できる最大行数
    private static final int MAX_LINES = 3;
    // 1ページに表示できる最大文字数
    private static final int MAX_CHAR_IN_PAGE = MAX_CHAR_IN_LINE * MAX_LINES;
    
    // 一番外側の枠
    private Rectangle rect;
    // 一つ内側の枠（白い枠線ができるように）
    private Rectangle innerRect;
    // 実際にテキストを描画する枠
    private Rectangle textRect;
    
    // メッセージウィンドウを表示中か
    private boolean isVisible = false;

    // カーソルのアニメーションGIF
    private Image cursorImage;
    
    // ���b�Z�[�W���i�[�����z��
    private char[] text = new char[128 * MAX_CHAR_IN_LINE];
    // �ő�y�[�W
    private int maxPage;
    // ���ݕ\�����Ă���y�[�W
    private int curPage = 0;
    // �\������������
    private int curPos;
    // ���̃y�[�W�ւ����邩�i�����\������Ă��true�j
    private boolean nextFlag = false;

    // ���b�Z�[�W�G���W��
    private MessageEngine messageEngine;
    
    // �e�L�X�g�𗬂��^�C�}�[�^�X�N
    private Timer timer;
    private TimerTask task;
    
    public MessageWindow(Rectangle rect) {
        this.rect = rect;

        innerRect = new Rectangle(
                rect.x + EDGE_WIDTH,
                rect.y + EDGE_WIDTH,
                rect.width - EDGE_WIDTH * 2,
                rect.height - EDGE_WIDTH * 2);
        
        textRect = new Rectangle(
                innerRect.x + 16,
                innerRect.y + 16,
                320,
                120);
        
        // ���b�Z�[�W�G���W�����쐬
        messageEngine = new MessageEngine();
        
        // �J�[�\���C���[�W�����[�h
        ImageIcon icon = new ImageIcon(getClass().getResource("../image/cursor.gif"));
        cursorImage = icon.getImage();
        
        timer = new Timer();
    }

    public void draw(Graphics g) {
        if (isVisible == false) return;
        
        // 枠を描く
        g.setColor(Color.WHITE);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);

        // �����̘g��`��
        g.setColor(Color.BLACK);
        g.fillRect(innerRect.x, innerRect.y, innerRect.width, innerRect.height);
        
        // 現在のページ（curPage）の1ページ分の内容を表示
        for (int i=0; i<curPos; i++) {
            char c = text[curPage * MAX_CHAR_IN_PAGE + i];
            int dx = textRect.x +
                     MessageEngine.FONT_WIDTH *
                     (i % MAX_CHAR_IN_LINE);
            int dy = textRect.y +
                     (LINE_HEIGHT + MessageEngine.FONT_HEIGHT) *
                     (i / MAX_CHAR_IN_LINE);
            messageEngine.drawCharacter(dx, dy, c, g);
        }

        // 最後のページでない場合は矢印を表示する
        if (curPage < maxPage && nextFlag) {
            int dx = textRect.x +
                     (MAX_CHAR_IN_LINE / 2) *
                     MessageEngine.FONT_WIDTH - 8;
            int dy = textRect.y +
                     (LINE_HEIGHT + MessageEngine.FONT_HEIGHT) * 3;
            g.drawImage(cursorImage, dx, dy, null);
        }
    }

    /**
     * メッセージをセットする
     * @param msg メッセージ
     */
    public void setMessage(String msg) {
        curPos = 0;
        curPage = 0;
        nextFlag = false;

        // 全角スペースで初期化
        for (int i=0; i<text.length; i++) {
            text[i] = '　';
        }

        int p = 0;  // 処理中の文字位置
        for (int i=0; i<msg.length(); i++) {
            char c = msg.charAt(i);
            if (c == '\\') {
                i++;
                if (msg.charAt(i) == 'n') {  // 改行
                    p += MAX_CHAR_IN_LINE;
                    p = (p / MAX_CHAR_IN_LINE) * MAX_CHAR_IN_LINE;
                } else if (msg.charAt(i) == 'f') {  // 改ページ
                    p += MAX_CHAR_IN_PAGE;
                    p = (p / MAX_CHAR_IN_PAGE) * MAX_CHAR_IN_PAGE;
                }
            } else {
                text[p++] = c;
            }
        }
        
        maxPage = p / MAX_CHAR_IN_PAGE;
        
        // 文字を流すタスクを起動
        task = new DrawingMessageTask();
        timer.schedule(task, 0L, 20L);
    }
    
    /**
     * メッセージを先に進める
     * @return メッセージが終了したらtrueを返す
     */
    public boolean nextMessage() {
        // 現在ページが最後のページだったらメッセージを終了する
        if (curPage == maxPage) {
            task.cancel();
            task = null;
            return true;
        }
        // ▼が表示されてなければ次ページへいけない
        if (nextFlag) {
            curPage++;
            curPos = 0;
            nextFlag = false;
        }
        return false;
    }
    
    /**
     * �E�B���h�E��\��
     */
    public void show() {
        isVisible = true;
    }

    /**
     * �E�B���h�E���B��
     */
    public void hide() {
        isVisible = false;
    }
    
    /**
     * �E�B���h�E��\������
     */
    public boolean isVisible() {
        return isVisible;
    }
    // ���b�Z�[�W��1���������ɕ`�悷��^�X�N
    class DrawingMessageTask extends TimerTask {
        public void run() {
            if (!nextFlag) {
                curPos++;  // 1�������₷
                // 1�y�[�W�̕������ɂȂ����灥��\��
                if (curPos % MAX_CHAR_IN_PAGE == 0) {
                    nextFlag = true;
                }
            }
        }
    }
}
