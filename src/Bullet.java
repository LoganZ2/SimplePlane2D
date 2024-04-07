import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;

public class Bullet extends Body{

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
