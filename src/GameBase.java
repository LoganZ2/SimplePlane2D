import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.contact.ContactAdapter;
import org.dyn4j.dynamics.contact.ContactListener;
import org.dyn4j.dynamics.contact.ContactPoint;
import org.dyn4j.geometry.*;

import java.util.*;
import java.util.stream.Collectors;

public class GameBase {
    public final int TICKRATE = 60;
    World world = new World();
    Set<Body> planeSet= new HashSet<>();

    Plane testPlane = new Plane();

    Drawer drawer = new Drawer();


    public GameBase(int height, int width) {
        setEdge(height, width);
        world.setGravity(new Vector2(0.0, 5.0));
    }

    public void setEdge(int height, int width) {
        Vector2 topLeft = new Vector2(0, 0);
        Vector2 topRight = new Vector2(width, 0);
        Vector2 bottomLeft = new Vector2(0, height);
        Vector2 bottomRight = new Vector2(width, height);

        Body Edge = new Body();
        Edge.addFixture(new Segment(topLeft, topRight), 0.1, 0.0, .1);
        Edge.addFixture(new Segment(bottomLeft, bottomRight), 0.1, 0.0, .1);
        Edge.addFixture(new Segment(topLeft, bottomLeft), 0.1, 0.0, .1);
        Edge.addFixture(new Segment(topRight, bottomRight), 0.1, 0.0, .1);
        Edge.setMass(MassType.INFINITE);

        world.addBody(Edge);

    }
    public void testPlayer() {

        Rectangle rect1 = drawer.formRect(10., 50., new Vector2(25, 25), Color.BLUE);
        Rectangle rect2 = drawer.formRect(50., 10., new Vector2(25, 25), Color.BLUE);
        Rectangle rect3 = drawer.formRect(30., 10., new Vector2(25, 45), Color.GRAY);

        Convex[] fixtures = new Convex[3];
        fixtures[0] = rect1;
        fixtures[1] = rect2;
        fixtures[2] = rect3;
        addPlane(testPlane, 250, 250, fixtures);
        testPlane.setMass(MassType.NORMAL);
        testPlane.setGravityScale(0);


    }
    public void update(GraphicsContext graphicsContext) {
        world.update(1.0 / 60.0);
        drawer.draw(testPlane, graphicsContext);
    }
    public void addPlane(Plane plane, double x, double y, Convex[] fixtures) {

        planeSet.add(plane);
        plane.translate(x, y);
        for (Convex fixture : fixtures) {
            plane.addFixture(fixture);
        }
        world.addBody(plane);
    }




}
