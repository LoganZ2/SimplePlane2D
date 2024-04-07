import org.dyn4j.dynamics.Body;
import org.dyn4j.world.*;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ColorfulWorld extends World<Body> {
    Drawer drawer = new Drawer();
    @Override
    public boolean removeBody(Body body) {

        try {
            for (BodyFixture fixture : body.getFixtures()) {
                Convex shape = fixture.getShape();
                drawer.colorMap.remove(shape);
            }

        } catch (NullPointerException e) {}
        return super.removeBody(body);
    }
    @Override
    public boolean update(double elapsedTime) {

        List<Plane> planeList = this.getBodies().stream().filter(x -> x instanceof Plane).map(Plane.class::cast).collect(Collectors.toCollection(ArrayList::new));
        for (Plane plane : planeList) {
            plane.tick();
        }
        return super.update(elapsedTime);
    }
    @Override
    public boolean update(double elapsedTime, int steps) {
        List<Plane> planeList = this.getBodies().stream().filter(x -> x instanceof Plane).map(Plane.class::cast).collect(Collectors.toCollection(ArrayList::new));
        for (Plane plane : planeList) {
            plane.tick();
        }
        return super.update(elapsedTime, steps);
    }
}
