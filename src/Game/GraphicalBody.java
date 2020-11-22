package Game;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class GraphicalBody extends Body {
    private static Color[] defaultColors = {
            Color.RED, Color.BLUE, Color.GREEN
    };
    private Color defaultColor;
    public GraphicalBody() {
        super();
        defaultColor = randDefaultColor();
    }

    private Color randDefaultColor() {
        return defaultColors[(int) Math.random() * defaultColors.length];
    }

    public GraphicalBody(Color defaultColor) {
        super();
        this.defaultColor = defaultColor;
    }

    public void draw(Graphics2D g, double pixelPerMeter) {
        AffineTransform original = g.getTransform();

        AffineTransform newTransform = new AffineTransform();
        newTransform.translate(this.transform.getTranslationX() * pixelPerMeter, this.transform.getTranslationY() * pixelPerMeter);
        newTransform.rotate(this.transform.getRotationAngle());
        g.transform(newTransform);

        for (BodyFixture f : this.fixtures) {
            if (f instanceof GraphicalFixture) {
                ((GraphicalFixture) f).draw(g, pixelPerMeter);
            } else {
                Renderer.draw(g, f.getShape(), pixelPerMeter, defaultColor);
            }
        }
        g.setTransform(original);
    }
}
