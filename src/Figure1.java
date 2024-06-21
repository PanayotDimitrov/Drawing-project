import java.awt.*;
import java.awt.geom.*;

class Figure1 extends Rectangle2D.Double implements ShapeInterface {
    private Color color;
    private BasicStroke stroke;
    private float transparency;
    private double scale = 1.0;
    private double rotation = 0.0;

    public Figure1(int x, int y, int width, int height, Color color, BasicStroke stroke, float transparency, double scale, double rotation) {
        super(x, y, width, height);
        this.color = color;
        this.stroke = stroke;
        this.transparency = transparency;
        this.scale = scale;
        this.rotation = rotation;
    }

    @Override
    public void move(int dx, int dy) {
        setFrame(getX() + dx, getY() + dy, getWidth(), getHeight());
    }

    @Override
    public Shape getTransformedShape() {
        GeneralPath path = new GeneralPath();

        // Create the main circle
        Ellipse2D circle = new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());
        path.append(circle, false);

        // Create the inner horizontal line
        Line2D horizontalLine = new Line2D.Double(getX(), getCenterY(), getX() + getWidth(), getCenterY());
        path.append(horizontalLine, false);

        // Create the vertical lines
        Line2D leftVerticalLine = new Line2D.Double(getX(), getY(), getX(), getY() + getHeight());
        Line2D rightVerticalLine = new Line2D.Double(getX() + getWidth(), getY(), getX() + getWidth(), getY() + getHeight());
        path.append(leftVerticalLine, false);
        path.append(rightVerticalLine, false);

        AffineTransform transform = AffineTransform.getTranslateInstance(getCenterX(), getCenterY());
        transform.scale(scale, scale);
        transform.rotate(Math.toRadians(rotation));
        transform.translate(-getCenterX(), -getCenterY());
        return transform.createTransformedShape(path);
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