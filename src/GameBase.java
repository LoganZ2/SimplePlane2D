import javafx.scene.paint.Color;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.Settings;
import org.dyn4j.world.*;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.*;
import java.util.*;
import java.util.stream.Collectors;

public class GameBase implements Runnable {
    public static final int TICKRATE = 165;
    public static final long NSPERTICK = 1000000000 / TICKRATE;
    ColorfulWorld world = new ColorfulWorld();
    Set<Body> planeSet = new HashSet<>();

    PlayerPlane testPlane = new PlayerPlane(world);

    public boolean running = true;
    volatile Body Edge;
    private boolean[] wasPressed = {false, false, false, false};
    private boolean[] wasReleased = {false, false, false, false};


    public GameBase(int height, int width) {
        testPlayer();
        setEdge(height, width);
        world.setGravity(new Vector2(0.0, 0));
        Settings set = new Settings();
        set.setMaximumTranslation(6.5);
        world.setSettings(set);

    }

    @Override
    public void run() {
        long lastUpdateTime = System.nanoTime();
        while (running) {
            long now = System.nanoTime();
            long elapsedNs = now - lastUpdateTime;

            if (elapsedNs > NSPERTICK) {
                update();
                lastUpdateTime = now - (elapsedNs % NSPERTICK);
            }
        }
    }

    public void setEdge(int height, int width) {
        Vector2 topLeft = new Vector2(0, 0);
        Vector2 topRight = new Vector2(width, 0);
        Vector2 bottomLeft = new Vector2(0, height);
        Vector2 bottomRight = new Vector2(width, height);

        Edge = new Body();
        Edge.addFixture(new Segment(topLeft, topRight), 0.1, 0.0, 1);
        Edge.addFixture(new Segment(bottomLeft, bottomRight), 0.1, 0.0, 1);
        Edge.addFixture(new Segment(topLeft, bottomLeft), 0.1, 0.0, 1);
        Edge.addFixture(new Segment(topRight, bottomRight), 0.1, 0.0, 1);
        Edge.setMass(MassType.INFINITE);
        world.addBody(Edge);
    }

    public void testPlayer() {
        Rectangle rect1 = world.drawer.formRect(10., 30., new Vector2(25, 15), Color.DARKVIOLET);
        Rectangle rect2 = world.drawer.formRect(45., 10., new Vector2(25, 20), Color.DARKVIOLET);
        Rectangle rect3 = world.drawer.formRect(15., 10., new Vector2(30, 35), Color.DARKVIOLET);
        Rectangle rect4 = world.drawer.formRect(15., 10., new Vector2(20, 35), Color.DARKVIOLET);
        rect3.rotate(Math.toRadians(30), rect3.getCenter());
        rect4.rotate(Math.toRadians(330), rect4.getCenter());
        Circle circ2 = world.drawer.formCircle(5., new Vector2(25, 0), Color.DARKVIOLET);
        Convex[] fixtures = {rect1, rect2, rect3, rect4, circ2};
        addPlane(testPlane, 250, 250, fixtures);
        testPlane.setLinearVelocity(0, 0);
        testPlane.setMass(MassType.FIXED_ANGULAR_VELOCITY);

    }

    public void update() {

        playerMove();
        world.update(NSPERTICK);
        List<Plane> planeList = world.getBodies().stream().filter(x -> x instanceof Plane).map(Plane.class::cast).collect(Collectors.toCollection(ArrayList::new));
        for (Plane plane : planeList) {
            plane.tick();
        }

    }

    public void addPlane(Plane plane, double x, double y, Convex[] fixtures) {
        planeSet.add(plane);
        plane.translate(x, y);
        for (Convex fixture : fixtures) {
            plane.addFixture(fixture);
        }
        world.addBody(plane);
    }

    public void playerMove() {
        int x = 0;
        int y = 0;
        boolean changedX = false;
        boolean changedY = false;
        if (UserIO.movement[0]) {
            y -= 100;
            changedY = true;
        }
        if (UserIO.movement[1]) {
            x -= 100;
            changedX = true;
        }
        if (UserIO.movement[2]) {
            y += 100;
            changedY = true;
        }
        if (UserIO.movement[3]) {
            x += 100;
            changedX = true;
        }
        if (UserIO.movement[4]) {
            testPlane.attack();
        }
        testPlane.move(x, y, changedX, changedY);
    }
}
