import java.awt.*;

public interface ShapeInterface {
    void move(int dx, int dy);
    java.awt.Shape getTransformedShape();
    Color getColor();
    void setColor(Color color);
    BasicStroke getStroke();
    void setStroke(BasicStroke stroke);
    float getTransparency();
    void setTransparency(float transparency);
    void setScale(double scale);
    void setRotation(double rotation);
}