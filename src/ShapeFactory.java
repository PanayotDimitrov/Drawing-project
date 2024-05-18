import java.awt.*;

class ShapeFactory {
    private ShapeType shapeType;

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public ShapeInterface createShape(int x, int y, Color color, BasicStroke stroke, float transparency, double scaling, int rotation) {
        switch (shapeType) {
            case RECTANGLE:
                return new Rectangle(x, y, 50, 50, color, stroke, transparency, scaling, rotation);
            case CIRCLE:
                return new Circle(x, y, 30, color, stroke, transparency, scaling, rotation);
            case LINE:
                return new Line(x, y, x + 50, y + 50, color, stroke, transparency, scaling, rotation);
            case POINT:
                return new Point(x, y, color, stroke, transparency, scaling, rotation);
            case TRIANGLE:
                return new Triangle(x, y, 50, color, stroke, transparency, scaling, rotation); // Adjust the triangle's size here
            case ELLIPSE:
                return new Ellipse(x, y, 80, 50, color, stroke, transparency, scaling, rotation);
            default:
                throw new IllegalArgumentException("Unknown shape type");
        }
    }
}

