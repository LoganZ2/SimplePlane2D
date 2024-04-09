import org.dyn4j.world.World;
import org.dyn4j.geometry.Vector2;

public class PlayerPlane extends Plane {
    Vector2 currentDirection = new Vector2 (0, -1);
    public PlayerPlane(ColorfulWorld world) {
        super(world);
        this.setWeapon(new MachineGun(this));


    }
    public void move(double step) {
        if (step < 0) return;
        Vector2 vv = this.getLinearVelocity().copy().project(this.currentDirection);
        Vector2 vvv = this.getLinearVelocity().copy().project(this.currentDirection.copy().getRightHandOrthogonalVector());
        Double vd = vv.getMagnitude();
        if (vv.dot(this.currentDirection) <= 0) {
            vd *= -1;
        }

        if (vd < this.maxSpeed) {


            applyForce(new Vector2(currentDirection).multiply(500000));
        }
        applyForce(vvv.copy().multiply(-5000));

    }

    public void tilt(double angularSpeed, boolean tiltLeft, boolean tiltRight) {
        if (tiltLeft) {
            this.rotate(-angularSpeed / 100, this.getWorldCenter());

        } else if (tiltRight) {
            this.rotate(angularSpeed / 100, this.getWorldCenter());
        } else {
            this.setAngularVelocity(0);
        }
        currentDirection = this.getWorldPoint(this.getLocalCenter().copy().add(new Vector2(0,  -1))).subtract(this.getWorldCenter());





    }
    @Override
    public void tick() {
        if (getWeapon().cooldown > 0) {
            getWeapon().cooldown--;
        }



    }
}
