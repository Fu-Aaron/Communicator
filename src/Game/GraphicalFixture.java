package Game;

import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Shape;

import java.awt.*;

public class GraphicalFixture extends BodyFixture implements Drawable{
    private Color color;
    public GraphicalFixture(Convex shape) {
        super(shape);
        color = randomColor();
    }

    public GraphicalFixture(Convex shape, Color color) {
        super(shape);
        this.color = color;
    }

    /**
     * @source myself in PhysicsTest\Renderer.java
     * @return A random color.
     */
    private static Color randomColor() {
        return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    }

    @Override
    public Shape getDrawnShape() {
        return getShape();
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void draw(Graphics2D g, double pixelPerMeter) {
        Renderer.draw(g, this, pixelPerMeter);
    }
}
