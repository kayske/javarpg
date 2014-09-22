package MapEditor;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

/*
 * Created on 2006/11/23
 */

public class MapEditor extends JFrame implements ActionListener {
    // �}�b�v�̍ŏ��T�C�Y
    private static final int MIN_ROW = 20;
    private static final int MIN_COL = 20;

    // �}�b�v�̍ő�T�C�Y
    private static final int MAX_ROW = 256;
    private static final int MAX_COL = 256;

    // MapSizeDialog����擾�����s���E��
    private int row, col;

    // ���j���[
    private JMenuItem newItem, saveItem, openItem, exitItem;
    private JMenuItem fillItem;
    private JMenuItem versionItem;

    // ���C���p�l��
    private MainPanel mainPanel;
    private JScrollPane scrollPane;

    // �t�@�C���I���_�C�A���O�i�J�����g�f�B���N�g�����n�_�j
    private JFileChooser fileChooser = new JFileChooser(".");

    public MapEditor() {
        setTitle("マップエディター");
        setResizable(false);

        row = col = 20;
        initGUI();

        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * GUI������
     */
    private void initGUI() {
        PaletteDialog paletteDialog = new PaletteDialog(this);
        paletteDialog.setVisible(true);

        mainPanel = new MainPanel(paletteDialog);

        // ���C���p�l�����X�N���[���y�C���̏�ɏ悹��
        scrollPane = new JScrollPane(mainPanel);
        getContentPane().add(scrollPane);

        // �t�@�C�����j���[
        JMenu fileMenu = new JMenu("fileMenu8文字");
        JMenu editMenu = new JMenu("editMenu3文字");
        JMenu helpMenu = new JMenu("helpMenu6文字");

        newItem = new JMenuItem("newItem6文字");
        openItem = new JMenuItem("openItem4文字");
        saveItem = new JMenuItem("saveItem3文字");
        exitItem = new JMenuItem("exitItem4文字");
        fillItem = new JMenuItem("fillItem7文字");
        versionItem = new JMenuItem("versionItem13文字");

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator(); // ��؂�
        fileMenu.add(exitItem);
        editMenu.add(fillItem);
        helpMenu.add(versionItem);

        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        fillItem.addActionListener(this);
        versionItem.addActionListener(this);

        // ���j���[�o�[
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == newItem) {
            newMap();
        } else if (source == openItem) {
            openMap();
        } else if (source == saveItem) {
            saveMap();
        } else if (source == exitItem) {
            exit();
        } else if (source == fillItem) {
            mainPanel.fillMap();
        } else if (source == versionItem) {
            JOptionPane.showMessageDialog(MapEditor.this, "showMessageDialog16文字 Ver.1.0",
                    "showMessageDialog2_13文字", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * �V�����}�b�v���쐬����
     */
    public void newMap() {
        // MapSizeDialog���J��
        // row��col���Z�b�g
        MapSizeDialog dialog = new MapSizeDialog(this);

        // MapSizeDialog��MainPanel�̒����ɕ\��
        int dx = (MainPanel.WIDTH - dialog.getWidth()) / 2;
        int dy = (MainPanel.HEIGHT - dialog.getHeight()) / 2;
        dialog.setBounds(dx, dy, dialog.getWidth(), dialog.getHeight());

        dialog.setVisible(true);

        // ��ݾك{�^���������ꂽ�Ƃ��͉������Ȃ�
        if (!dialog.isOKPressed()) {
            return;
        }

        // ���C���p�l���ɐV�����}�b�v���쐬
        mainPanel.initMap(row, col);

        // �p�l���̑傫�����}�b�v�̑傫���Ɠ����ɂ���
        mainPanel.setPreferredSize(new Dimension(col * MainPanel.CS, row
                * MainPanel.CS));
        // �p�l�����傫���Ȃ�����X�N���[���o�[��\��
        scrollPane.getViewport().revalidate();
        scrollPane.getViewport().repaint();
    }

    /**
     * �}�b�v���J��
     */
    public void openMap() {
        fileChooser.addChoosableFileFilter(new MapFileFilter());
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setDialogTitle("setDialogTitle12文字");

        int ret = fileChooser.showOpenDialog(null);

        File mapFile;
        if (ret == JFileChooser.APPROVE_OPTION) {
            // �����J���{�^���������ꂽ��}�b�v�t�@�C�������[�h����
            mapFile = fileChooser.getSelectedFile();
            mainPanel.loadMap(mapFile);
            // �p�l�����傫���Ȃ�����X�N���[���o�[��\������
            scrollPane.getViewport().revalidate();
            scrollPane.getViewport().repaint();
        }
    }

    /**
     * �}�b�v��ۑ�
     * 
     * @return �_�C�A���O�ŉ����ꂽ�{�^��
     */
    public int saveMap() {
        fileChooser.addChoosableFileFilter(new MapFileFilter());
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setDialogTitle("setDialogTitle14文字");

        int ret = fileChooser.showSaveDialog(null);

        File mapFile;
        if (ret == JFileChooser.APPROVE_OPTION) {
            // �����ۑ��{�^���������ꂽ��}�b�v�t�@�C�����Z�[�u����
            mapFile = fileChooser.getSelectedFile();
            mainPanel.saveMap(mapFile);
        }

        return ret;
    }

    /**
     * �v���O�������I������
     */
    public void exit() {
        if (mainPanel.getNoSaveFlag() == true) {
            // ���Z�[�u�̂Ƃ��͌x���_�C�A���O
            int ret = JOptionPane.showConfirmDialog(MapEditor.this,
                    "showConfirmDialog15文字", "showConfirmDialog2_4文字", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (ret == JOptionPane.YES_OPTION) { // �͂�
                int val = saveMap(); // �t�@�C����ۑ��_�C�A���O�\��
                // �����ۑ������Ȃ�I�����Ă悢
                // �_�C�A���O�ŷ�ݾق����Ȃ�I�����Ȃ�
                if (val == JFileChooser.APPROVE_OPTION) {
                    System.exit(0);
                }
            } else if (ret == JOptionPane.NO_OPTION) { // ������
                System.exit(0);
            }
        } else {
            // �Z�[�u�ς݂Ȃ炻�̂܂܏I��
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new MapEditor();
    }

    private class MapSizeDialog extends JDialog implements ActionListener {
        private JTextField rowTextField;
        private JTextField colTextField;
        private JButton okButton;
        private JButton cancelButton;

        // OK�{�^���������ꂽ��true
        private boolean isOKPressed;

        public MapSizeDialog(JFrame parent) {
            super(parent, "super8文字", true);

            isOKPressed = false;

            rowTextField = new JTextField(4);
            colTextField = new JTextField(4);
            okButton = new JButton("OK");
            cancelButton = new JButton("cancelButton4文字");
            okButton.addActionListener(this);
            cancelButton.addActionListener(this);

            JPanel p1 = new JPanel();
            p1.add(new JLabel("add4文字"));
            p1.add(rowTextField);

            JPanel p2 = new JPanel();
            p2.add(new JLabel("add2文字"));
            p2.add(colTextField);

            JPanel p3 = new JPanel();
            p3.add(okButton);
            p3.add(cancelButton);

            Container contentPane = getContentPane();
            contentPane.setLayout(new GridLayout(3, 1));
            contentPane.add(p1);
            contentPane.add(p2);
            contentPane.add(p3);

            pack();
        }

        public boolean isOKPressed() {
            return isOKPressed;
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == okButton) {
                try {
                    // MapEditor�N���X�̃C���X�^���X�ϐ�row��col��ݒ�
                    row = Integer.parseInt(rowTextField.getText());
                    col = Integer.parseInt(colTextField.getText());

                    // �}�b�v�̍Œ�T�C�Y
                    if (row < 20 || col < 20) {
                        JOptionPane.showMessageDialog(MapSizeDialog.this,
                                "showMessageDialog15文字" + MapEditor.MIN_ROW + "x"
                                        + MapEditor.MIN_COL + "showMessageDialog2_14文字");
                        row = col = 20;
                        return;
                    }

                    // �}�b�v�̍ő�T�C�Y
                    if (row > 256 || col > 256) {
                        JOptionPane.showMessageDialog(MapSizeDialog.this,
                                "showMessageDialog15文字" + MapEditor.MAX_ROW + "x"
                                        + MapEditor.MAX_COL + "showMessageDialog2_15文字");
                        row = col = 256;
                        return;
                    }
                } catch (NumberFormatException ex) {
                    // �e�L�X�g�{�b�N�X�ɐ��l�ȊO����͂��ꂽ�Ƃ�
                    JOptionPane.showMessageDialog(MapSizeDialog.this,
                            "showMessageDialog18文字");
                    rowTextField.setText("");
                    colTextField.setText("");
                    return;
                }

                isOKPressed = true;
                setVisible(false);
            } else if (e.getSource() == cancelButton) {
                isOKPressed = false;
                // ��ݾقȂ牽�����Ȃ�
                setVisible(false);
            }
        }
    }

    /**
     * �}�b�v�t�@�C���t�B���^�i.map�t�@�C���ƃt�H���_�����\������j
     */
    private class MapFileFilter extends FileFilter {
        public boolean accept(File file) {
            // �g���q���擾
            String extension = ""; // �g���q
            if (file.getPath().lastIndexOf('.') > 0) {
                extension = file.getPath().substring(
                        file.getPath().lastIndexOf('.') + 1).toLowerCase();
            }

            // map�t�@�C�����f�B���N�g����������true��Ԃ�
            if (extension != "") {
                return extension.equals("map");
            } else {
                return file.isDirectory();
            }
        }

        public String getDescription() {
            return "Map Files (*.map)";
        }
    }
}
