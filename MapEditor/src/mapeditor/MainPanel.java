import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JPanel;

/*
 * Created on 2006/11/23
 */

public class MainPanel extends JPanel
        implements
            MouseListener,
            MouseMotionListener {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 640;

    // �`�b�v�Z�b�g�̃T�C�Y�i�P�ʁF�s�N�Z���j
    public static final int CS = 32;

    // �}�b�v
    private int[][] map;
    // �}�b�v�̑傫���i�P�ʁF�}�X�j
    private int row;
    private int col;

    // �}�b�v�`�b�v�p���b�g�ւ̎Q��
    private PaletteDialog paletteDialog;

    // �}�b�v�`�b�v�C���[�W
    private Image[] mapchipImages;

    // �}�b�v���X�V���Ė��Z�[�u�̂Ƃ�true�ƂȂ�
    // �I������Ƃ��ɕۑ����邩�������߂ɕK�v
    // �������삵����true�A�t�@�C���ɕۑ�������false�ɂ��邱��
    private boolean noSaveFlag = false;

    public MainPanel(PaletteDialog paletteDialog) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        addMouseListener(this);
        addMouseMotionListener(this);

        // �p���b�g�_�C�A���O
        this.paletteDialog = paletteDialog;
        mapchipImages = paletteDialog.getMapchipImages();

        // �}�b�v������
        initMap(20, 20);

        // �R���X�g���N�^�̏���ł͑��삵�����Ƃɂ��Ȃ�
        noSaveFlag = false;
    }

    /**
     * �}�b�v������
     * 
     * @param r �s��
     * @param c ��
     */
    public void initMap(int r, int c) {
        row = r;
        col = c;
        map = new int[row][col];

        // �p���b�g�őI������Ă���}�b�v�`�b�v�ԍ����擾
        int mapchipNo = paletteDialog.getSelectedMapchipNo();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map[i][j] = mapchipNo;
            }
        }

        noSaveFlag = true;
    }

    /**
     * �}�b�v�t�@�C������ǂݍ���
     * 
     * @param mapFile �}�b�v�t�@�C��
     */
    public void loadMap(File mapFile) {
        try {
            FileInputStream in = new FileInputStream(mapFile);

            // �s���E�񐔂�ǂݍ���
            row = in.read();
            col = in.read();

            // �}�b�v��ǂݍ���
            map = new int[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    map[i][j] = in.read();
                }
            }

            in.close();

            // �p�l���̑傫�����}�b�v�̑傫���Ɠ����ɂ���
            setPreferredSize(new Dimension(col * CS, row * CS));

            noSaveFlag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * �}�b�v���t�@�C���֏�������
     * 
     * @param mapFile �}�b�v�t�@�C��
     */
    public void saveMap(File mapFile) {
        // �}�b�v�̓o�C�i���t�@�C���Ƃ���
        // �}�b�v��1�}�X��1�o�C�g�ŕ\��
        try {
            FileOutputStream out = new FileOutputStream(mapFile);

            // �s���E�񐔂���������
            out.write(row);
            out.write(col);

            // �}�b�v����������
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    out.write(map[i][j]);
                }
            }

            out.close();

            noSaveFlag = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * �I�����Ă���}�b�v�`�b�v�Ń}�b�v��h��Ԃ�
     */
    public void fillMap() {
        // �p���b�g�őI������Ă���}�b�v�`�b�v�ԍ����擾
        int mapchipNo = paletteDialog.getSelectedMapchipNo();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map[i][j] = mapchipNo;
            }
        }
        
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // map�ɕۑ�����Ă���}�b�v�`�b�v�ԍ������Ƃɉ摜��`�悷��
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                g.drawImage(mapchipImages[map[i][j]], j * CS, i * CS, null);
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / CS;
        int y = e.getY() / CS;

        // �p���b�g����擾�����ԍ����Z�b�g
        if (x >= 0 && x < col && y >= 0 && y < row) {
            map[y][x] = paletteDialog.getSelectedMapchipNo();
        }

        noSaveFlag = true;

        repaint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / CS;
        int y = e.getY() / CS;

        // �p���b�g����擾�����ԍ����Z�b�g
        if (x >= 0 && x < col && y >= 0 && y < row) {
            map[y][x] = paletteDialog.getSelectedMapchipNo();
        }

        noSaveFlag = true;

        repaint();
    }

    public void mouseMoved(MouseEvent e) {
    }

    public boolean getNoSaveFlag() {
        return noSaveFlag;
    }
}
