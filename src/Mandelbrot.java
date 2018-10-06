import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Mandelbrot {

    private final int THREADS = 8;

    class Complex {

        private double r;
        private double i;

        Complex(double real, double imaginary) {
            r = real;
            i = imaginary;
        }

        double getReal() {
            return r;
        }

        double getImaginary() {
            return i;
        }

        Complex square() {
            double realPart = Math.pow(r, 2) - Math.pow(i, 2);
            double imaginaryPart = r * i * 2;
            r = realPart;
            i = imaginaryPart;
            return this;
        }

        Complex plus(Complex c) {
            r += c.getReal();
            i += c.getImaginary();
            return this;
        }

    }

    private class Calculator implements Runnable {

        private ArrayList<Double> value;
        double x0;
        double y0;
        int x1;
        int x2;
        int y1;
        int y2;
        double y;
        double scale;

        Calculator(int x1, int y1, int x2, int y2, double x0, double y0, double y, double scale) {
            this.value = new ArrayList<>();
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
            this.x0 = x0;
            this.y0 = y0;
            this.y = y;
            this.scale = scale;
        }

        int mandelbrotR(Complex z, Complex c, int depth) {

            if (depth == maxDepth) return maxDepth;

            boolean infinite = z.getReal() > 100000000 || z.getImaginary() > 100000000;

            if (infinite) {
                return depth;
            } else {
                return mandelbrotR((z.square()).plus(c), c, depth + 1);
            }

        }

        int mandelbrotI(Complex c) {

            int depth = 0;
            boolean infinite = false;
            Complex z = new Complex(0, 0);

            while (!infinite && depth < maxDepth) {
                infinite = z.getReal() > 100000000 || z.getImaginary() > 100000000;
                z = (z.square()).plus(c);
                depth++;
            }

            return depth;

        }

        public void run() {

            int xc = (x1 + x2) / 2;
            int yc = (y1 + y2) / 2;

            for (int x = x1; x < x2; x++) {

                Complex c = new Complex(x0 + (x - xc) / scale, y0 + (y - yc) / scale);
                int i;
                if (recursive) {
                    Complex z = new Complex(0, 0);
                    i = mandelbrotR(z, c, 0);
                } else {
                    i = mandelbrotI(c);
                }

                value.add(6 * Math.abs((double) i / (double) maxDepth));

            }
        }


    }

    private PixelWriter p;
    private int maxDepth;
    private boolean recursive;

    public Mandelbrot(GraphicsContext g, int maxDepth, boolean recursive) {
        this.p = g.getPixelWriter();
        this.maxDepth = maxDepth;
        this.recursive = recursive;
    }

    public Color getColor(double value) {
        if (value < 1) {
            return Color.rgb((int) (256 * value), 0, 255);
        } else if (value < 2) {
            value -= 1;
            return Color.rgb(255, (int) (255 * value), (int) (255 * (1 - value)));
        } else if (value < 3) {
            value -= 2;
            return Color.rgb((int) (255 * (1 - value)), 255, 0);
        } else if (value < 4) {
            value -= 3;
            return Color.rgb(0, 255, (int) (255 * value));
        } else if (value < 5) {
            value -= 4;
            return Color.rgb(0, (int) (255 * (1 - value)), 255);
        } else {
            value -= 5;
            return Color.rgb(0, 0, (int) (255 * (1 - value)));
        }
    }


    void render(int x1, int y1, int x2, int y2, double x0, double y0, double scale) {

        Calculator[] calc = new Calculator[THREADS];
        Thread[] thread = new Thread[THREADS];

        for (int y = y1; y < y2; y += THREADS) {

            for (int t = 0; t < THREADS; t++) {
                calc[(y + t) % THREADS] = new Calculator(x1, y1, x2, y2, x0, y0, y + t, scale);
                thread[(y + t) % THREADS] = new Thread(calc[(y + t) % THREADS]);
                thread[(y + t) % THREADS].start();
            }

            for (int t = 0; t < THREADS; t++) {

                try {
                    thread[(y + t) % THREADS].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int x = x1; x < x2; x++) {
                    p.setColor(x, y + t, getColor(calc[(y + t) % THREADS].value.get(x)));
                }
            }
        }

    }


}