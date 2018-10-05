import javafx.scene.canvas.GraphicsContext;

public class Sierpinski {

    private GraphicsContext g;
    private double limit;

    public Sierpinski(GraphicsContext g, double limit) {
        this.g = g;
        this.limit = limit;
    }

    private void sierpinski(double x1, double y1, double x2, double y2, double x3, double y3) {

        double length = Math.hypot(x1 - x2, y1 - y2);

        if (length >= limit) {

            sierpinski(x1, y1, x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2, x1 + (x3 - x1) / 2, y1 + (y3 - y1) / 2);
            sierpinski(x2, y2, x2 + (x3 - x2) / 2, y2 + (y3 - y2) / 2, x2 + (x1 - x2) / 2, y2 + (y1 - y2) / 2);
            sierpinski(x3, y3, x3 + (x2 - x3) / 2, y3 + (y2 - y3) / 2, x3 + (x1 - x3) / 2, y3 + (y1 - y3) / 2);

        }

        g.strokeLine(x1, y1, x2, y2);
        g.strokeLine(x2, y2, x3, y3);
        g.strokeLine(x3, y3, x1, y1);

    }

    public void render(double x, double y, double angle, double size) {

        sierpinski(x + Math.sin(angle) * size,
                y - Math.cos(angle) * size,
                x + Math.sin(angle + Math.PI * 2 / 3) * size,
                y - Math.cos(angle + Math.PI * 2 / 3) * size,
                x + Math.sin(angle + Math.PI * 4 / 3) * size,
                y - Math.cos(angle + Math.PI * 4 / 3) * size);

    }

}
