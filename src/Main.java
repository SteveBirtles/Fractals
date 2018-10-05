import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final boolean FULLSCREEN = true;

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Steve's Recursive Fractals Demonstration");
        stage.setResizable(false);
        stage.setFullScreen(FULLSCREEN);
        stage.setScene(scene);
        stage.setOnCloseRequest(we -> System.exit(0));
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) System.exit(0);
        });
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.show();

        Canvas canvas = new Canvas();
        canvas.setWidth(WIDTH);
        canvas.setHeight(HEIGHT);
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setLineWidth(2);
        root.getChildren().add(canvas);

        Snowflake s = new Snowflake(g, 0.287, 2);
        g.setStroke(Color.BLUE);
        s.render(WIDTH / 4, HEIGHT / 3, 0, 300);

        Tree t = new Tree(g, 0.2, 0.8, 2);
        g.setStroke(Color.GREEN);
        t.render(3 * WIDTH / 4, 5 * HEIGHT / 8, 0, 125);

        Sierpinski i = new Sierpinski(g, 2);
        g.setStroke(Color.RED);
        i.render(WIDTH / 2, 3 * HEIGHT / 4, 0, 300);

    }

    public static void main(String[] args) {
        launch(args);
    }

}