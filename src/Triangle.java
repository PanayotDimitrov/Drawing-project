import java.awt.*;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class Triangle extends Polygon implements ShapeInterface {
    private Color color;
    private BasicStroke stroke;
    private float transparency;
    private double scale = 1.0;
    private double rotation = 0.0;

    public Triangle(int x, int y, int sideLength, Color color, BasicStroke stroke, float transparency, double scale, double rotation) {
        super(new int[]{x, x + sideLength / 2, x - sideLength / 2}, new int[]{y - sideLength / 2, y + sideLength / 2, y + sideLength / 2}, 3);
        this.color = color;
        this.stroke = stroke;
        this.transparency = transparency;
        this.scale = scale;
        this.rotation = rotation;
    }

    @Override
    public void move(int dx, int dy) {
        translate(dx, dy);
    }

    @Override
    public java.awt.Shape getTransformedShape() {
        AffineTransform transform = AffineTransform.getTranslateInstance(getCenterX(), getCenterY());
        transform.scale(scale, scale);
        transform.rotate(Math.toRadians(rotation));
        transform.translate(-getCenterX(), -getCenterY());
        return transform.createTransformedShape(this);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public BasicStroke getStroke() {
        return stroke;
    }

    @Override
    public void setStroke(BasicStroke stroke) {
        this.stroke = stroke;
    }

    @Override
    public float getTransparency() {
        return transparency;
    }

    @Override
    public void setTransparency(float transparency) {
        this.transparency = transparency;
    }

    @Override
    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    private double getCenterX() {
        return (double) (xpoints[0] + xpoints[1] + xpoints[2]) / 3;
    }

    private double getCenterY() {
        return (double) (ypoints[0] + ypoints[1] + ypoints[2]) / 3;
    }
}
