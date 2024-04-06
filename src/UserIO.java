import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Group;
import org.dyn4j.geometry.Vector2;


public class UserIO extends Application {
    public static int height = 720;
    public static int width = 1280;
    private Canvas canvas = new Canvas(1280, 720);
    private Group root = new Group(canvas);
    private Scene scene = new Scene(root, 1280, 720);
    volatile static boolean[] movement = {false, false, false, false, false};
    private GraphicsContext gc;
    GameBase base = new GameBase(height, width);



    @Override
    public void start(Stage primaryStage) {



        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    movement[0] = true;
                    break;
                case LEFT:
                    movement[1] = true;
                    break;
                case DOWN:
                    movement[2] = true;
                    break;
                case RIGHT:
                    movement[3] = true;
                    break;
                case SPACE:
                    movement[4] = true;
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                    movement[0] = false;
                    break;
                case LEFT:
                    movement[1] = false;
                    break;
                case DOWN:
                    movement[2] = false;
                    break;
                case RIGHT:
                    movement[3] = false;
                    break;
                case SPACE:
                    movement[4] = false;
                    break;
            }
        });

        gc = canvas.getGraphicsContext2D();
        primaryStage.setTitle("Simple Plane 2D");
        primaryStage.setScene(scene);
        primaryStage.show();
        Thread gameThread = new Thread(base);
        gameThread.start();


        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.setFill(Color.LAVENDER);
                gc.fillRect(0, 0, width, height);
                gc.setFill(Color.BLACK);
                base.drawer.draw(base.testPlane, gc);
            }
        }.start();
    }





    public static void main(String[] args) {
        launch(args);

    }
}