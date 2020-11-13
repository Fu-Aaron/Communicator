package PhysicsTest;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Dyn4JCanvas extends JComponent {
    private final int WIDTH;
    private final int HEIGHT;
    private static final double NANO_TO_BASE = 1.0e9;
    private World<Body> world;
    private long lastTime;

    public Dyn4JCanvas(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        init();
    }

    private void init() {
        //this.setIgnoreRepaint(true);
        initWorld();
        lastTime = System.nanoTime();
        Thread thread = new Looper();
        thread.start();
    }

    private void update() {
        repaint();
        /*long time = System.nanoTime();
        long diff = time - lastTime;
        lastTime = time;
        double elapsedTime = (double) diff / NANO_TO_BASE;
        world.update(elapsedTime);*/
        world.update(0.1);
    }


    private class Looper extends Thread {
        public void run() {
            while (true) {
                try {
                    update();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Using billiards initWorld from source:
     * @source: https://github.com/dyn4j/dyn4j-samples/blob/master/src/main/java/org/dyn4j/samples/Billiards.java
     * Accessed: 11/12/2020
     */
    private void initWorld() {
        world = new World<>();
        this.world.setGravity(World.ZERO_GRAVITY);

        // create all your bodies/joints

        Body wallr = new Body();
        wallr.addFixture(Geometry.createRectangle(0.2, 10));
        wallr.translate(2, 0);
        wallr.setMass(MassType.INFINITE);
        world.addBody(wallr);

        Body ball1 = new Body();
        ball1.addFixture(Geometry.createCircle(0.028575), //  2.25 in diameter = 0.028575 m radius
                217.97925, 								  //  0.126 oz/in^3 = 217.97925 kg/m^3
                0.08,
                0.9);
        ball1.translate(-1, 0);
        ball1.setAngularVelocity(51.0);
        //ball1.setLinearVelocity(5.36448, 0.0); 		  // 12 mph = 5.36448 m/s
        ball1.setLinearVelocity(2, 0);					  //  so we can see the bouncing
        ball1.setMass(MassType.NORMAL);
        this.world.addBody(ball1);

        Body ball2 = new Body();
        ball2.addFixture(Geometry.createCircle(0.028575), 217.97925, 0.08, 0.9);
        ball2.translate(1, 0);
        ball2.setMass(MassType.NORMAL);
        this.world.addBody(ball2);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        gr.transform(AffineTransform.getScaleInstance(1, -1));
        gr.transform(AffineTransform.getTranslateInstance(WIDTH / 2, -HEIGHT / 2));
        AffineTransform ot = gr.getTransform();
        for (int i = 0; i < world.getBodyCount(); i++) {
            Body b = world.getBody(i);
            AffineTransform lt = new AffineTransform();
            lt.translate(b.getTransform().getTranslationX() * 300, b.getTransform().getTranslationY() * 300);
            lt.rotate(b.getTransform().getRotationAngle());
            gr.transform(lt);
            for (BodyFixture bf :b.getFixtures()) {
                Renderer.draw(gr, bf.getShape(), 300);
            }
            gr.setTransform(ot);
        }
    }
}
