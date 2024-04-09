import org.dyn4j.dynamics.Body;
import org.dyn4j.world.*;
import org.dyn4j.geometry.Vector2;

public abstract class Plane extends Body {
    private int health = 100;
    private Vector2 firePoint = new Vector2(0, 0);
    private ColorfulWorld world;
    int maxSpeed = 200;


    private Weapon weapon;

    public Plane(ColorfulWorld world) {
        super();
        this.world = world;
    }
    public int getHealth() {
        return this.health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public Weapon getWeapon() {
        return this.weapon;
    }
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
    public ColorfulWorld getWorld() {
        return this.world;
    }
    public void move(double x, double y) {
        if (Math.abs(this.getLinearVelocity().x) <= maxSpeed || x * this.getLinearVelocity().x <= 0) {
            this.applyForce(new Vector2(x * 5000, 0));
        }
        if (Math.abs(this.getLinearVelocity().y) <= maxSpeed || y * this.getLinearVelocity().y <= 0) {
            this.applyForce(new Vector2(0, y * 5000));
        }
    }

    public void attack() {
        if (weapon.cooldown <= 0) {
            weapon.cooldown = GameBase.TICKRATE / weapon.attackSpeed;
            weapon.fire();
        }
    }

    public void tick() {
        if (weapon.cooldown > 0) {
            weapon.cooldown--;
        }

    }
}
