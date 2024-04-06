import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;

public class Plane extends Body {
    public void move(double x, double y) {
        this.applyForce(new Vector2(x * 5000, y * 5000));
    }
    public void attack(Bullet bullet) {

    }
}
