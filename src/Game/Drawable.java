package Game;


import org.dyn4j.geometry.Shape;

import java.awt.*;

public interface Drawable {
    Shape getDrawnShape();
    Color getColor();
    void draw(Graphics2D g, double pixelPerMeter);
}
