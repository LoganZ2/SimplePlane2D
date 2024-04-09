import javafx.scene.paint.Color;
import org.dyn4j.geometry.*;

import java.util.Iterator;

public class MachineGun extends Weapon{

    double cooldown = 0;

    public MachineGun(Plane plane) {
        super(plane);
        attackSpeed = 7;
    }
    @Override
    public void fire() {
        this.AI();
    }
    @Override
    public void AI() {

        NormalBullet b = new NormalBullet(this);
        b.setMass(MassType.NORMAL);

        Vector2 vel1 = new Vector2(this.getAttached().getLocalCenter().getXComponent());
        vel1 = this.getAttached().getWorldPoint(vel1);
        Vector2 vel2 = new Vector2(this.getAttached().getLocalCenter());
        vel2 = this.getAttached().getWorldPoint(vel2);
        vel2 = vel1.subtract(vel2);
        vel2.multiply(30000);

        b.setLinearVelocity(vel2);

        Rotation planeRotation = this.getAttached().getTransform().getRotation();

        Vector2 planeLocalCenter = this.getAttached().getLocalCenter();
        Vector2 planeWorldCenter = this.getAttached().getWorldCenter();
        Vector2 planeLocalTop = planeLocalCenter.getXComponent();
        Vector2 planeWorldTop = this.getAttached().getWorldPoint(planeLocalTop);

        Vector2 bulletLocalPosition = new Vector2(planeLocalTop.add(0, -b.height / 2 - 10));


        Vector2 bulletWorldPosition = this.getAttached().getWorldPoint(bulletLocalPosition);

        b.translate(bulletWorldPosition);

        b.rotate(planeRotation, b.getWorldCenter());


        this.getAttached().getWorld().addBody(b);


    }

}
