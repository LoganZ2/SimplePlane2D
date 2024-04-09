import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;

public class Bullet extends Body{
    public int height = 0;
    private int damage = 0;

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Bullet(){
        super();
        this.setBullet(true);
    }
    public Bullet(Body body) {
        for (BodyFixture fixture: body.getFixtures()) {
            this.addFixture(fixture);
        }
    }
}
