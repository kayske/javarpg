import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Created on 2006/11/23
 */

public class PaletteDialog extends JDialog {
    private static final int WIDTH = 512;
    private static final int HEIGHT = 512;

    // �`�b�v�Z�b�g�̃T�C�Y�i�P�ʁF�s�N�Z���j
    public static final int CS = 32;

    // �`�b�v��
    private static final int NUM_CHIPS = 256;
    private static final int NUM_CHIPS_IN_ROW = 16;

    // �}�b�v�`�b�v�C���[�W
    private Image mapchipImage;
    // �}�b�v�`�b�v���`�b�v���Ƃɕ��������C���[�W
    private Image[] mapchipImages;

    // �I������Ă���}�b�v�`�b�v�ԍ�
    private int selectedMapchipNo;

    public PaletteDialog(JFrame parent) {
        // ���[�h���X�_�C�A���O
        super(parent, "�}�b�v�`�b�v�p���b�g", false);

        setBounds(650, 0, WIDTH, HEIGHT);
        setResizable(false);

        PalettePanel palettePanel = new PalettePanel();
        getContentPane().add(palettePanel);

        pack();

        // �}�b�v�`�b�v�C���[�W�����[�h
        loadImage();
    }

    /**
     * �I������Ă���}�b�v�`�b�v�ԍ���Ԃ�
     * 
     * @return �I������Ă���}�b�v�`�b�v�ԍ�
     */
    public int getSelectedMapchipNo() {
        return selectedMapchipNo;
    }

    /**
     * �������ꂽ�}�b�v�`�b�v�C���[�W��Ԃ�
     * 
     * @return �������ꂽ�}�b�v�`�b�v�C���[�W
     */
    public Image[] getMapchipImages() {
        return mapchipImages;
    }

    // �}�b�v�`�b�v�C���[�W�����[�h
    private void loadImage() {
        ImageIcon icon = new ImageIcon(getClass().getResource(
                "../map/mapchip02.png"));
        mapchipImage = icon.getImage();

        // �}�b�v�`�b�v���ƂɃC���[�W�𕪊�
        mapchipImages = new Image[NUM_CHIPS];
        for (int i = 0; i < NUM_CHIPS; i++) {
            mapchipImages[i] = createImage(CS, CS);
            int x = i % NUM_CHIPS_IN_ROW;
            int y = i / NUM_CHIPS_IN_ROW;
            Graphics g = mapchipImages[i].getGraphics();
            g.drawImage(mapchipImage, 0, 0, CS, CS, x * CS, y * CS,
                    x * CS + CS, y * CS + CS, null);
            g.dispose();
        }
    }

    private class PalettePanel extends JPanel implements MouseListener {
        public PalettePanel() {
            setPreferredSize(new Dimension(PaletteDialog.WIDTH,
                    PaletteDialog.HEIGHT));
            setFocusable(true);

            addMouseListener(this);
        }

        public void paintComponent(Graphics g) {
            g.setColor(new Color(32, 0, 0));
            g.fillRect(0, 0, PaletteDialog.WIDTH, PaletteDialog.HEIGHT);

            // �}�b�v�`�b�v�C���[�W��`��
            g.drawImage(mapchipImage, 0, 0, this);

            // �I������Ă���}�b�v�`�b�v��g�ň͂�
            int x = selectedMapchipNo % NUM_CHIPS_IN_ROW;
            int y = selectedMapchipNo / NUM_CHIPS_IN_ROW;
            g.setColor(Color.YELLOW);
            g.drawRect(x * CS, y * CS, CS, CS);
        }

        public void mouseClicked(MouseEvent e) {
            int x = e.getX() / CS;
            int y = e.getY() / CS;

            // �}�b�v�`�b�v�ԍ��͍��ォ��0, 1, 2�Ɛ�����
            int mapchipNo = y * NUM_CHIPS_IN_ROW + x;
            if (mapchipNo > NUM_CHIPS) {
                mapchipNo = NUM_CHIPS;
            }

            selectedMapchipNo = mapchipNo;

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
    }
}
