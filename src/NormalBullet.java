import javafx.scene.paint.Color;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Vector2;

public class NormalBullet extends Bullet{
    public NormalBullet(Weapon weapon) {
        super();
        this.addFixture(weapon.getAttached().getWorld().drawer.formRect(10., 30., new Vector2(0, 0), Color.DARKVIOLET));
        this.addFixture(weapon.getAttached().getWorld().drawer.formCircle(5., this.getLocalCenter(), Color.AQUA));
    }
    public NormalBullet(Bullet bullet, Weapon weapon) {
        super();
        BulletCopy.bulletCopy(this, bullet, weapon);
    }
}
