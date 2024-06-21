import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

class Figure8 extends java.awt.Rectangle implements ShapeInterface {
    private Color color;
    private BasicStroke stroke;
    private float transparency;
    private double scale = 1.0;
    private double rotation = 0.0;

    public Figure8(int x, int y, int width, int height, Color color, BasicStroke stroke, float transparency, double scale, double rotation) {
        super(x, y, width, height);
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
        GeneralPath path = new GeneralPath();

        // Suzdavane na krug
        Ellipse2D.Double circle = new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());
        path.append(circle, false);

        // Circle center
        double centerX = getX() + getWidth() / 2.0;
        double centerY = getY() + getHeight() / 2.0;
        double radius = getWidth() / 2.0;

        // Suzdavane na diagonali
        double offset = radius / Math.sqrt(2);

        // Purvi diagonal
        path.moveTo(centerX - offset, centerY - offset);
        path.lineTo(centerX + offset, centerY + offset);

        // Vtori diagonal
        path.moveTo(centerX - offset, centerY + offset);
        path.lineTo(centerX + offset, centerY - offset);

        // Apply transformations
        AffineTransform transform = AffineTransform.getTranslateInstance(centerX, centerY);
        transform.scale(scale, scale);
        transform.rotate(Math.toRadians(rotation));
        transform.translate(-centerX, -centerY);

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
