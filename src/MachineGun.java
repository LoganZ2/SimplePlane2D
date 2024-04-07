import javafx.scene.paint.Color;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import java.util.Iterator;

public class MachineGun extends Weapon{

    double cooldown = 0;

    public MachineGun(Plane plane) {
        super(plane);
    }
    @Override
    public void fire() {
        this.AI();
    }
    @Override
    public void AI() {

        Bullet b = new NormalBullet(this);
        b.setMass(MassType.NORMAL);

        Vector2 vel1 = new Vector2(this.getAttached().getLocalCenter().getXComponent());
        vel1 = this.getAttached().getWorldPoint(vel1);
        Vector2 vel2 = new Vector2(this.getAttached().getLocalCenter());
        vel2 = this.getAttached().getWorldPoint(vel2);
        vel2 = vel1.subtract(vel2);
        vel2.multiply(10);

        b.setLinearVelocity(vel2);
        Vector2 pos = this.getAttached().getWorldPoint(new Vector2(0, -50));
        b.translate(pos);
        b.translate(this.getAttached().getWorldCenter().x - b.getWorldCenter().x, 0);
        b.getTransform().setRotation(this.getAttached().getTransform().getRotation());
        this.getAttached().getWorld().addBody(b);


    }

}
