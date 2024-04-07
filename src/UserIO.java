import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.scene.Group;
import org.dyn4j.dynamics.Body;

public class UserIO extends Application {
    public static int height = 1080;
    public static int width = 1920;
    private Canvas canvas = new Canvas(width, height);
    private Group root = new Group(canvas);
    private Scene scene = new Scene(root, width, height);
    volatile static boolean[] movement = {false, false, false, false, false};
    private GraphicsContext gc;
    GameBase base = new GameBase(height, width);

    @Override
    public void start(Stage primaryStage) {
        Scale scale = new Scale(1280/width, 1920/width, 0, 0);
        root.getTransforms().add(scale);
        scale.xProperty().bind(Bindings.divide(scene.widthProperty(), width));
        scale.yProperty().bind(Bindings.divide(scene.heightProperty(), height));



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
                case F11:
                    primaryStage.setFullScreen(true);
                    primaryStage.setMinWidth(1920);
                    primaryStage.setMinHeight(1080);
                    primaryStage.setMaxWidth(1920);
                    primaryStage.setMaxHeight(1080);
                    break;
                case ESCAPE:
                    primaryStage.setFullScreen(false);
                    primaryStage.setMinWidth(1280);
                    primaryStage.setMinHeight(720);
                    primaryStage.setMaxWidth(1280);
                    primaryStage.setMaxHeight(720);
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
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(720);
        primaryStage.setMaxWidth(1280);
        primaryStage.setMaxHeight(720);
        primaryStage.show();
        Thread gameThread = new Thread(base);
        gameThread.start();

        primaryStage.setOnCloseRequest(event -> {
            base.running = false;
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.setFill(Color.LAVENDER);
                gc.fillRect(0, 0, width, height);
                gc.setFill(Color.BLACK);
                int i = 0;
                for (Body body :base.world.getBodies()) {
                    base.drawer.draw(body, gc);
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}