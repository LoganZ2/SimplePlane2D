import javafx.scene.paint.Color;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import java.util.Iterator;

public class MachineGun extends Weapon{

    Iterator<Bullet> iterator = bulletSet.iterator();

    public MachineGun(Plane plane) {
        super(plane);
        Rectangle rect1 = plane.getDrawer().formRect(10., 30., new Vector2(0, 0), Color.DARKVIOLET);
        Bullet b = new Bullet();
        b.addFixture(rect1);
        bulletSet.add(b);
    }
    @Override
    public void fire() {
        this.AI();
    }
    @Override
    public void AI() {

        if (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            Bullet b = new Bullet(bullet);
            b.setMass(MassType.NORMAL);
            b.addFixture(this.getAttached().getDrawer().formCircle(5., b.getLocalCenter(), Color.AQUA));
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

        } else {
            iterator = bulletSet.iterator();
        }
    }

}
