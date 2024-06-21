import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

class Figure9 extends java.awt.Rectangle implements ShapeInterface {
    private Color color;
    private BasicStroke stroke;
    private float transparency;
    private double scale = 1.0;
    private double rotation = 0.0;

    public Figure9(int x, int y, int width, int height, Color color, BasicStroke stroke, float transparency, double scale, double rotation) {
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

        path.moveTo(getX(),getY());
        path.lineTo(getX()+getWidth()/2,getY()-50);

        path.moveTo(getX()+getWidth()/2,getY()-50);
        path.lineTo(getX()+getWidth(),getY());

        path.moveTo(getX()+getWidth(),getY());
        path.lineTo(getX()+50,getY()+getWidth()/2);

        path.moveTo(getX()+50,getY()+getWidth()/2);
        path.lineTo(getX(),getY());

        path.moveTo(getX(),getY());
        path.lineTo(getX()+getWidth(),getY());

        path.moveTo(getX()+getWidth()/2,getY());
        path.lineTo(getX()+50,getY()+getWidth()/2);


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
