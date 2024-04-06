import org.dyn4j.geometry.Vector2;

public class PlayerPlane extends Plane {
    public void move(double x, double y, boolean changedX, boolean changedY) {
        if (Math.abs(this.getLinearVelocity().x) <= maxSpeed || x * this.getLinearVelocity().x <= 0) {
            this.applyForce(new Vector2(x * 5000, 0));
        }
        if (Math.abs(this.getLinearVelocity().y) <= maxSpeed || y * this.getLinearVelocity().y <= 0) {
            this.applyForce(new Vector2(0, y * 5000));
        }
        if (changedX && x == 0) {
            this.applyImpulse(new Vector2(this.getLinearVelocity().getNegative().multiply(100).x, 0));
        }
        if (changedY && y == 0) {
            this.applyImpulse(new Vector2(0, this.getLinearVelocity().getNegative().multiply(100).y));
        }
    }
}
