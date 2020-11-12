package PhysicsTest;

import javax.swing.*;
import java.awt.*;

public class LaunchDyn4j {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    public static void main(String[] args) {

        JFrame frame = new JFrame("Tester");
        Dyn4JCanvas canvas = new Dyn4JCanvas(WIDTH, HEIGHT);
        Dimension size = new Dimension(WIDTH, HEIGHT);

        canvas.setPreferredSize(size);
        canvas.setMinimumSize(size);
        canvas.setMaximumSize(size);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(size);
        frame.add(canvas);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
