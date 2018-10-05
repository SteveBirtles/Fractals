import javafx.scene.canvas.GraphicsContext;

public class Tree {

    private GraphicsContext g;
    private double spread;
    private double decay;
    private double limit;

    public Tree(GraphicsContext g, double spread, double decay, double limit) {
        this.g = g;
        this.spread = spread;
        this.decay = decay;
        this.limit = limit;
    }

    private void tree(double x1, double y1, double x2, double y2) {

        double length = Math.hypot(x1 - x2, y1 - y2);

        if (length >= limit) {

            length *= decay;

            double theta = Math.atan2(x2 - x1, y2 - y1);

            tree(x2, y2, x2 + length * Math.sin(theta + spread), y2 + length * Math.cos(theta + spread));
            tree(x2, y2, x2 + length * Math.sin(theta - spread), y2 + length * Math.cos(theta - spread));

        }

        g.strokeLine(x1, y1, x2, y2);

    }

    public void render(double x, double y, double angle, double size) {

        tree(x, y, x + Math.sin(angle) * size, y - Math.cos(angle) * size);

    }

}