package Game;

import javax.swing.*;
import java.awt.*;

public class Launcher {
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 800;
    private JFrame frame;
    private JComponent canvas;
    private Dimension size;

    public Launcher(int width, int height) {
        size = new Dimension(width, height);
        initCanvas();
        initFrame();
    }

    private void initCanvas() {
        canvas = new GameCanvas(size);
        canvas.setPreferredSize(size);
        canvas.setMinimumSize(size);
        canvas.setMaximumSize(size);
    }

    private void initFrame() {
        frame = new JFrame("Game Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(size);
        frame.add(canvas);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Launcher game = new Launcher(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
