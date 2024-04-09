import java.util.HashSet;

public abstract class Weapon {
    public HashSet<Bullet> bulletSet = new HashSet<>();
    private Plane attached;
    int attackSpeed = 1;
    public double cooldown = 0;
    public Weapon(Plane plane) {
        this.attached = plane;
    }
    public abstract void fire();
    public abstract void AI();
    public Plane getAttached() {
        return this.attached;
    }
}




