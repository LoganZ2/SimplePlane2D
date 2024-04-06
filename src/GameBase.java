import javafx.scene.paint.Color;
import org.dyn4j.dynamics.Settings;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.*;
import java.util.*;

public class GameBase implements Runnable {
    public static final int TICKRATE = 280;
    public static final double NSPERTICK = 1000000000D / TICKRATE;
    World world = new World();
    Set<Body> planeSet = new HashSet<>();

    PlayerPlane testPlane = new PlayerPlane();
    Drawer drawer = new Drawer();
    private boolean running = true;
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
        while (running) {
            long lastTime = System.nanoTime();
            while (running) {
                long now = System.nanoTime();
                if ((now - lastTime) > NSPERTICK) {
                    update();
                    lastTime += NSPERTICK;
                }
            }
        }
    }

    public void setEdge(int height, int width) {
        Vector2 topLeft = new Vector2(0, 0);
        Vector2 topRight = new Vector2(width, 0);
        Vector2 bottomLeft = new Vector2(0, height);
        Vector2 bottomRight = new Vector2(width, height);

        Edge = new Body();
        Edge.addFixture(new Segment(topLeft, topRight), 0.1, 0.0, 0);
        Edge.addFixture(new Segment(bottomLeft, bottomRight), 0.1, 0.0, 0);
        Edge.addFixture(new Segment(topLeft, bottomLeft), 0.1, 0.0, 0);
        Edge.addFixture(new Segment(topRight, bottomRight), 0.1, 0.0, 0);
        Edge.setMass(MassType.INFINITE);
        world.addBody(Edge);
    }

    public void testPlayer() {
        Rectangle rect1 = drawer.formRect(10., 30., new Vector2(25, 15), Color.DARKVIOLET);
        Rectangle rect2 = drawer.formRect(45., 10., new Vector2(25, 20), Color.DARKVIOLET);
        Rectangle rect3 = drawer.formRect(15., 10., new Vector2(30, 35), Color.DARKVIOLET);
        Rectangle rect4 = drawer.formRect(15., 10., new Vector2(20, 35), Color.DARKVIOLET);
        rect3.rotate(Math.toRadians(30), rect3.getCenter());
        rect4.rotate(Math.toRadians(330), rect4.getCenter());
        Circle circ2 = drawer.formCircle(5., new Vector2(25, 0), Color.DARKVIOLET);
        Convex[] fixtures = {rect1, rect2, rect3, rect4, circ2};
        addPlane(testPlane, 250, 250, fixtures);
        testPlane.setLinearVelocity(0, 0);
        testPlane.setMass(MassType.FIXED_ANGULAR_VELOCITY);
        testPlane.setActive(true);
    }

    public void update() {
        playerMove();
        world.update(NSPERTICK);
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
        testPlane.move(x, y, changedX, changedY);
    }
}
