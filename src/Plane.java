import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;

public class Plane extends Body {
    int maxSpeed = 100;
    public void move(double x, double y) {
        if (Math.abs(this.getLinearVelocity().x) <= maxSpeed || x * this.getLinearVelocity().x <= 0) {
            this.applyForce(new Vector2(x * 5000, 0));
        }
        if (Math.abs(this.getLinearVelocity().y) <= maxSpeed || y * this.getLinearVelocity().y <= 0) {
            this.applyForce(new Vector2(0, y * 5000));
        }
    }

    public void attack(Bullet bullet) {

    }
}
