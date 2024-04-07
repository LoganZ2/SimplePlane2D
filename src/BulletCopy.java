import javafx.scene.paint.Color;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.*;

public class BulletCopy {
    public static void bulletCopy(Bullet toBeCopied, Bullet toCopy, Weapon weapon) {
        for (BodyFixture fixture : toBeCopied.getFixtures()) {
            Convex shape = fixture.getShape();
            if (shape instanceof Circle) {
                toBeCopied.addFixture(weapon.getAttached().getWorld().drawer.formCircle(shape.getRadius(), shape.getCenter(), weapon.getAttached().getWorld().drawer.colorMap.getOrDefault(shape, Color.BLACK)));
            }
            if (shape instanceof Rectangle) {
                toBeCopied.addFixture(weapon.getAttached().getWorld().drawer.formRect(((Rectangle) shape).getWidth(), ((Rectangle) shape).getHeight(), shape.getCenter(), weapon.getAttached().getWorld().drawer.colorMap.getOrDefault(shape, Color.BLACK)));
            }
            if (shape instanceof Segment) {
                toBeCopied.addFixture(weapon.getAttached().getWorld().drawer.formSegment(((Segment) shape).getPoint1(), ((Segment) shape).getPoint2(), shape.getCenter(), weapon.getAttached().getWorld().drawer.colorMap.getOrDefault(shape, Color.BLACK)));
            }
            if (shape instanceof Polygon) {
                toBeCopied.addFixture(weapon.getAttached().getWorld().drawer.formPolygon(((Polygon) shape).getVertices(), shape.getCenter(), weapon.getAttached().getWorld().drawer.colorMap.getOrDefault(shape, Color.BLACK)));
            }
        }
    }
}
