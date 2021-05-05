import javax.swing.*;

public class Frame {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Board gameplay = new Board();
        obj.setBounds(10, 10, 900, 600);
        obj.setTitle("BrickBreaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
    }
}

