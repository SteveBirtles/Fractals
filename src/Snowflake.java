import javafx.scene.canvas.GraphicsContext;

public class Snowflake {

    private GraphicsContext g;
    private double spike;
    private double limit;

    public Snowflake(GraphicsContext g, double spike, double limit) {
        this.spike = spike;
        this.limit = limit;
        this.g = g;
    }

    private void snowflake(double x1, double y1, double x2, double y2) {

        double length = Math.hypot(x1 - x2, y1 - y2);

        if (length < limit) {

            g.strokeLine(x1, y1, x2, y2);

        } else {

            double xn = (y2 - y1) * spike;
            double yn = (x1 - x2) * spike;
            double x0 = x1 + (x2 - x1) / 2 + xn;
            double y0 = y1 + (y2 - y1) / 2 + yn;

            snowflake(x1, y1, x1 + (x2 - x1) / 3, y1 + (y2 - y1) / 3);
            snowflake(x1 + (x2 - x1) / 3, y1 + (y2 - y1) / 3, x0, y0);
            snowflake(x0, y0, x1 + 2 * (x2 - x1) / 3, y1 + 2 * (y2 - y1) / 3);
            snowflake(x1 + 2 * (x2 - x1) / 3, y1 + 2 * (y2 - y1) / 3, x2, y2);

        }
    }


    public void render(double x, double y, double angle, double size) {


        snowflake(x + Math.sin(angle) * size,
                y - Math.cos(angle) * size,
                x + Math.sin(angle + Math.PI * 2 / 3) * size,
                y - Math.cos(angle + Math.PI * 2 / 3) * size);

        snowflake(x + Math.sin(angle + Math.PI * 2 / 3) * size,
                y - Math.cos(angle + Math.PI * 2 / 3) * size,
                x + Math.sin(angle + Math.PI * 4 / 3) * size,
                y - Math.cos(angle + Math.PI * 4 / 3) * size);

        snowflake(x + Math.sin(angle + Math.PI * 4 / 3) * size,
                y - Math.cos(angle + Math.PI * 4 / 3) * size,
                x + Math.sin(angle) * size,
                y - Math.cos(angle) * size);

    }

}