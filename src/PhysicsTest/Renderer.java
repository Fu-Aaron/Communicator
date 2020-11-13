package PhysicsTest;

import org.dyn4j.geometry.*;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;


/**
 * @source
 * Author: William Bittle  http://www.dyn4j.org/
 * Location: https://github.com/dyn4j/dyn4j-samples/blob/master/src/main/java/org/dyn4j/samples/UsingGraphics2D.java
 * Date: 11/11/2020
 */
public class Renderer {
    public static void draw(Graphics2D g, Shape shape, double pixelPerMeter, Color color) {
        if (color == null) {
            color = randomColor();
        }
        if (shape instanceof Polygon) {
            drawPolygon(g, (Polygon) shape, pixelPerMeter, color);
        } else if (shape instanceof Segment) {
            drawSegment(g, (Segment) shape, pixelPerMeter, color);
        } else if (shape instanceof Ellipse) {
            drawEllipse(g, (Ellipse) shape, pixelPerMeter, color);
        } else if (shape instanceof Circle) {
            drawCircle(g, (Circle) shape, pixelPerMeter, color);
        }else {
            //throw new IllegalArgumentException("Shape is wrong.");
        }
    }

    public static void draw(Graphics2D g, Shape shape, double pixelPerMeter) {
        draw(g, shape, pixelPerMeter, randomColor());
    }

    /**
     * Copy-pasted as well, see below.
     * @param g
     * @param circle
     * @param pixelPerMeter
     * @param color
     */
    private static void drawCircle(Graphics2D g, Circle circle, double pixelPerMeter, Color color) {
        double radius = circle.getRadius();
        Vector2 center = circle.getCenter();

        double radius2 = 2.0 * radius;
        Ellipse2D.Double c = new Ellipse2D.Double(
                (center.x - radius) * pixelPerMeter,
                (center.y - radius) * pixelPerMeter,
                radius2 * pixelPerMeter,
                radius2 * pixelPerMeter);

        //System.out.println(center);

        // fill the shape
        g.setColor(color);
        g.fill(c);
        // draw the outline
        g.setColor(complement(color));
        g.draw(c);

        // draw a line so that rotation is visible
        Line2D.Double l = new Line2D.Double(
                center.x * pixelPerMeter,
                center.y * pixelPerMeter,
                (center.x + radius) * pixelPerMeter,
                center.y * pixelPerMeter);
        g.draw(l);
    }

    /**
     * Copy-pasted from source.
     * @source https://github.com/dyn4j/dyn4j-samples/blob/0074fac86b30bb5a50bb1b8ac6951a7548b0d0fc/src/main/java/org/dyn4j/samples/framework/Graphics2DRenderer.java#L51
     * 11/11/20
     * @param g
     * @param polygon
     * @param pixelPerMeter
     * @param color
     */
    private static void drawPolygon(Graphics2D g, Polygon polygon, double pixelPerMeter, Color color) {
        Vector2[] vertices = polygon.getVertices();

        // create the awt polygon
        Path2D.Double p = new Path2D.Double();
        p.moveTo(vertices[0].x * pixelPerMeter, vertices[0].y * pixelPerMeter);
        for (int i = 1; i < vertices.length; i++) {
            p.lineTo(vertices[i].x * pixelPerMeter, vertices[i].y * pixelPerMeter);
        }
        p.closePath();

        // fill the shape
        g.setColor(color);
        g.fill(p);
        // draw the outline
        g.setColor(complement(color));
        g.draw(p);
    }

    /**
     * Copy-pasted from source.
     * @source https://github.com/dyn4j/dyn4j-samples/blob/0074fac86b30bb5a50bb1b8ac6951a7548b0d0fc/src/main/java/org/dyn4j/samples/framework/Graphics2DRenderer.java#L51
     * @param g
     * @param segment
     * @param pixelPerMeter
     * @param color
     */
    private static void drawSegment(Graphics2D g, Segment segment, double pixelPerMeter, Color color) {
        Vector2[] vertices = segment.getVertices();

        Line2D.Double l = new Line2D.Double(
                vertices[0].x * pixelPerMeter,
                vertices[0].y * pixelPerMeter,
                vertices[1].x * pixelPerMeter,
                vertices[1].y * pixelPerMeter);

        // draw the outline
        g.setColor(color);
        g.draw(l);
    }

    /**
     * Copy-pasted from source.
     * @source https://github.com/dyn4j/dyn4j-samples/blob/0074fac86b30bb5a50bb1b8ac6951a7548b0d0fc/src/main/java/org/dyn4j/samples/framework/Graphics2DRenderer.java#L51
     * @param g
     * @param ellipse
     * @param pixelPerMeter
     * @param color
     */
    private static void drawEllipse(Graphics2D g, Ellipse ellipse, double pixelPerMeter, Color color) {
        // get the local rotation and translation
        double rotation = ellipse.getRotationAngle();
        Vector2 center = ellipse.getCenter();

        // save the old transform
        AffineTransform oTransform = g.getTransform();
        g.translate(center.x * pixelPerMeter, center.y * pixelPerMeter);
        g.rotate(rotation);

        double width = ellipse.getWidth();
        double height = ellipse.getHeight();
        Ellipse2D.Double c = new Ellipse2D.Double(
                (-width * 0.5) * pixelPerMeter,
                (-height * 0.5) * pixelPerMeter,
                width * pixelPerMeter,
                height * pixelPerMeter);

        // fill the shape
        g.setColor(color);
        g.fill(c);
        // draw the outline
        g.setColor(complement(color));
        g.draw(c);

        // re-instate the old transform
        g.setTransform(oTransform);
    }

    private static Color randomColor() {
        return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    }

    private static Color complement(Color color) {
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
    }
}
