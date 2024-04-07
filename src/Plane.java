import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;

public class Plane extends Body {
    private Vector2 firePoint = new Vector2(0, 0);
    private World world;
    private Drawer drawer;
    int maxSpeed = 100;
    MachineGun weapon;
    public Plane(World world, Drawer drawer) {
        super();
        this.world = world;
        this.drawer = drawer;
        weapon = new MachineGun(this);
    }

    public World getWorld() {
        return this.world;
    }
    public Drawer getDrawer() {
        return this.drawer;
    }
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
