import java.awt.*;
import java.awt.geom.*;

class Figure3 extends Rectangle2D.Double implements ShapeInterface {
    private Color color;
    private BasicStroke stroke;
    private float transparency;
    private double scale = 1.0;
    private double rotation = 0.0;

    public Figure3(int x, int y, int width, int height, Color color, BasicStroke stroke, float transparency, double scale, double rotation) {
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

        // suzdavane na kruga
        Ellipse2D circle = new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());
        path.append(circle, false);

        // namirane na centura
        double centerX = getCenterX();
        double centerY = getCenterY();

        // namirane na radius
        double radius = getWidth() / 2.0;

        // chertaene na linii ot centura kum rubovete
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i);
            double endX = centerX + radius * Math.cos(angle);
            double endY = centerY - radius * Math.sin(angle);
            path.moveTo(centerX, centerY);
            path.lineTo(endX, endY);
        }

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