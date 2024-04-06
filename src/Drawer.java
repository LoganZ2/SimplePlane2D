import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.*;
import java.util.HashMap;
import java.util.List;

public class Drawer {
    HashMap<Convex, Color> colorMap = new HashMap<>();
    public Rectangle formRect(Double width, Double height, Vector2 translation, Color color) {
        Rectangle rect = new Rectangle(width, height);
        colorMap.put(rect, color);
        rect.translate(translation);
        return rect;
    }
    public Circle formCircle(Double radius, Vector2 translation, Color color) {
        Circle circ = new Circle(radius);
        colorMap.put(circ, color);
        circ.translate(translation);
        return circ;
    }
    public Segment formSegment(Vector2 start, Vector2 end, Vector2 translation, Color color) {
        Segment seg = new Segment(start, end);
        colorMap.put(seg, color);
        seg.translate(translation);
        return seg;
    }
    public Polygon formPolygon(Vector2[] vectorList, Vector2 translation, Color color) {
        Polygon poly = new Polygon(vectorList);
        colorMap.put(poly, color);
        poly.translate(translation);
        return poly;
    }
    public void draw(Body body, GraphicsContext graphicsContext) {
        List<BodyFixture> fixtures = body.getFixtures();
        for (BodyFixture fixture : fixtures) {
            Convex shape = fixture.getShape();
            graphicsContext.setFill(colorMap.getOrDefault(shape, Color.BLACK));
            if (shape instanceof Circle) {
                Circle circle = (Circle) shape;
                Vector2 center = body.getWorldCenter().add(circle.getCenter()).subtract(body.getLocalCenter());
                double radius = circle.getRadius();
                graphicsContext.fillOval(center.x - radius, center.y - radius, radius * 2, radius * 2);
            } else if (shape instanceof Segment) {
                Segment segment = (Segment) shape;
                Vector2 point1 = body.getWorldPoint(segment.getPoint1());
                Vector2 point2 = body.getWorldPoint(segment.getPoint2());
                graphicsContext.strokeLine(point1.x, point1.y, point2.x, point2.y);
            } else if (shape instanceof Polygon || shape instanceof Rectangle) {
                Polygon polygon = (shape instanceof Polygon) ? (Polygon) shape : new Polygon(((Rectangle) shape).getVertices());
                Vector2[] vertices = polygon.getVertices();
                double[] xPoints = new double[vertices.length];
                double[] yPoints = new double[vertices.length];
                for (int i = 0; i < vertices.length; i++) {
                    Vector2 worldVertex = body.getWorldPoint(vertices[i]);
                    xPoints[i] = worldVertex.x;
                    yPoints[i] = worldVertex.y;
                }
                graphicsContext.fillPolygon(xPoints, yPoints, vertices.length);
            }
        }
    }
}
