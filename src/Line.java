import java.awt.*;
import java.awt.geom.AffineTransform;

public class Line extends java.awt.geom.Line2D.Double implements ShapeInterface {
    private Color color;
    private BasicStroke stroke;
    private float transparency;
    private double scale = 1.0;
    private double rotation = 0.0;

    public Line(int x1, int y1, int x2, int y2, Color color, BasicStroke stroke, float transparency, double scale, double rotation) {
        super(x1, y1, x2, y2);
        this.color = color;
        this.stroke = stroke;
        this.transparency = transparency;
        this.scale = scale;
        this.rotation = rotation;
    }

    @Override
    public void move(int dx, int dy) {
        setLine(getX1() + dx, getY1() + dy, getX2() + dx, getY2() + dy);
    }

    @Override
    public java.awt.Shape getTransformedShape() {
        AffineTransform transform = AffineTransform.getTranslateInstance(getX1(), getY1());
        double angle = Math.atan2(getY2() - getY1(), getX2() - getX1());
        transform.rotate(angle);
        transform.scale(scale, scale);
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
}
