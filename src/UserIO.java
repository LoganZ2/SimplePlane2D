import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Group;


public class UserIO extends Application {
    public static int height = 720;
    public static int width = 1280;
    private Canvas canvas = new Canvas(1280, 720);
    private Group root = new Group(canvas);
    private Scene scene = new Scene(root, 1280, 720);
    boolean[] movement = {false, false, false, false};
    private GraphicsContext gc;
    GameBase base = new GameBase(height, width);

    @Override
    public void start(Stage primaryStage) {

        base.testPlayer();


        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    movement[0] = true;
                    break;
                case A:
                    movement[1] = true;
                    break;
                case S:
                    movement[2] = true;
                    break;
                case D:
                    movement[3] = true;
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:
                    movement[0] = false;
                    break;
                case A:
                    movement[1] = false;
                    break;
                case S:
                    movement[2] = false;
                    break;
                case D:
                    movement[3] = false;
                    break;
            }
        });

        gc = canvas.getGraphicsContext2D();
        primaryStage.setTitle("Simple Plane 2D");
        primaryStage.setScene(scene);
        primaryStage.show();


        new AnimationTimer() {
            @Override
            public void handle(long now) {


                gc.setFill(Color.LAVENDER);
                gc.fillRect(0, 0, width, height);
                gc.setFill(Color.BLACK);
                playerMove();
                base.update(gc);

            }
        }.start();
    }

    public void playerMove() {
        int x = 0;
        int y = 0;
        boolean changed = false;
        if (movement[0]) {
            y -= 100;
            changed = true;
        }
        if (movement[1]) {
            x -= 100;
            changed = true;
        }
        if (movement[2]) {
            y += 100;
            changed = true;
        }
        if (movement[3]) {
            x += 100;
            changed = true;
        }
        if (changed)
            base.move(base.testPlane, x, y);

    }

    public static void main(String[] args) {
        launch(args);
    }
}