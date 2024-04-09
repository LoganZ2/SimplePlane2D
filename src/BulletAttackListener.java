import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.contact.Contact;
import org.dyn4j.dynamics.contact.SolvedContact;
import org.dyn4j.world.ContactCollisionData;
import org.dyn4j.world.listener.ContactListener;
import java.util.HashSet;

public class BulletAttackListener implements ContactListener<Body> {
    public HashSet<Body> bulletObliterate = new HashSet<>();

    @Override
    public void begin(ContactCollisionData<Body> contactCollisionData, Contact contact) {

    }

    @Override
    public void persist(ContactCollisionData<Body> contactCollisionData, Contact contact, Contact contact1) {

    }

    @Override
    public void end(ContactCollisionData<Body> contactCollisionData, Contact contact) {



    }

    @Override
    public void destroyed(ContactCollisionData<Body> contactCollisionData, Contact contact) {

    }

    @Override
    public void collision(ContactCollisionData<Body> contactCollisionData) {

    }

    @Override
    public void preSolve(ContactCollisionData<Body> contactCollisionData, Contact contact) {

    }

    @Override
    public void postSolve(ContactCollisionData<Body> contactCollisionData, SolvedContact solvedContact) {
        Body body1 = contactCollisionData.getBody1();
        Body body2 = contactCollisionData.getBody2();
        if (body1 instanceof Bullet) {
            bulletObliterate.add(body1);
            if (contactCollisionData.getBody2() instanceof Plane) {
                Plane plane = (Plane)body2;
                plane.setHealth(plane.getHealth() - ((Bullet) body1).getDamage());
            }
        }
        if (body2 instanceof Bullet) {
            bulletObliterate.add(body2);
            if (contactCollisionData.getBody1() instanceof Plane) {
                Plane plane = (Plane)body1;
                plane.setHealth(plane.getHealth() - ((Bullet) body2).getDamage());
            }
        }
    }
}
