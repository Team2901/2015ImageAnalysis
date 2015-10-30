package ftc;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageFrame extends JFrame{
    static final int COL_NUM = 2;
    static final int ROW_NUM = 2;

    JLabel[][] labels = new JLabel[COL_NUM][ROW_NUM];

    public ImageFrame() {
        super("GridLayout Test");
        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(COL_NUM, ROW_NUM));
        for(int c = 0; c < COL_NUM; c++) {
            for(int r = 0; r < ROW_NUM; r++) {
                JLabel label = new JLabel();
                labels[c][r] = label;
                add(label);
            }
        }
        pack();
        setVisible(true);
    }

    /*
     * add a image at a given index
     */
    public void addImage(int x, int y, BufferedImage image) {
        JLabel label = labels[x][y];
        ImageIcon imageIcon = new ImageIcon(image);
        label.setIcon(imageIcon);
        label.setSize(image.getWidth(), image.getHeight());
        validate();
        repaint();
        pack();
    }
}
