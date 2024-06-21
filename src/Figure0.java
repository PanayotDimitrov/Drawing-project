import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

class Figure0 extends Rectangle2D.Double implements ShapeInterface {
    private Color color;
    private BasicStroke stroke;
    private float transparency;
    private double scale = 1.0;
    private double rotation = 0.0;

    public Figure0(int x, int y, int width, int height, Color color, BasicStroke stroke, float transparency, double scale, double rotation) {
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

        // Create the main trapezoid
        path.moveTo(getX(), getY() );
        path.lineTo(getX() + getWidth(), getY());
        path.lineTo(getX() + getWidth() - getWidth() / 6, getY() + getHeight());
        path.lineTo(getX() + getWidth() / 6, getY() + getHeight());
        path.closePath();
        path.moveTo(getX()+getWidth()/2,getY());
        path.lineTo(getCenterX(),getCenterY());
        path.moveTo(getCenterX(),getCenterY());
        path.lineTo(getX()+33,getY()+getHeight());
        path.moveTo(getCenterX(),getCenterY());
        path.lineTo(getCenterX()+84,getY()+getHeight()/2);


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
