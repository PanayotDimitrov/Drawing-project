import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.Shape;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingFrame extends JFrame {
    private JPanel drawingPanel;
    private List<ShapeInterface> shapes = new ArrayList<>();
    private ShapeFactory shapeFactory = new ShapeFactory();
    private Color currentColor = Color.BLACK;
    private boolean moveMode = false;
    private ShapeInterface selectedShape = null;
    private java.awt.geom.Point2D.Double lastMousePosition = null;
    private float transparency = 1.0f;
    private BasicStroke stroke = new BasicStroke(1.0f);

    private JComboBox<Double> scalingComboBox;
    private JComboBox<Integer> rotationComboBox;

    public DrawingFrame() {
        setTitle("Drawing Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Suzdavane na butonite
        JCheckBox moveCheckBox = new JCheckBox("Move");
        JButton rectangleButton = new JButton("Rectangle");
        JButton circleButton = new JButton("Circle");
        JButton lineButton = new JButton("Line");
        JButton pointButton = new JButton("Point");
        JButton triangleButton = new JButton("Triangle");
        JButton ellipseButton = new JButton("Ellipse");
        JButton clearButton = new JButton("Clear");

        // Suzdavane na radio butoni
        JRadioButton blueButton = new JRadioButton("Blue");
        JRadioButton redButton = new JRadioButton("Red");
        JRadioButton yellowButton = new JRadioButton("Yellow");

        // Suzdavane na butoni za smqna na cveta
        ButtonGroup colorGroup = new ButtonGroup();
        colorGroup.add(blueButton);
        colorGroup.add(redButton);
        colorGroup.add(yellowButton);

        // Sldier za prozrachnost
        JSlider transparencySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
        transparencySlider.setMajorTickSpacing(10);
        transparencySlider.setPaintTicks(true);
        transparencySlider.setPaintLabels(true);

        // combo box za debelina na liniqta
        Double[] thicknessOptions = {1.0, 2.0, 3.0, 4.0, 5.0};
        JComboBox<Double> thicknessComboBox = new JComboBox<>(thicknessOptions);

        // combo box za scaling i rotation
        scalingComboBox = new JComboBox<>(new Double[]{1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0});
        rotationComboBox = new JComboBox<>(new Integer[]{0, 45, 90, 135, 180, 225, 270, 315, 360});

        // Panel za butonite
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(moveCheckBox);
        buttonPanel.add(rectangleButton);
        buttonPanel.add(circleButton);
        buttonPanel.add(lineButton);
        buttonPanel.add(pointButton);
        buttonPanel.add(triangleButton);
        buttonPanel.add(ellipseButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(blueButton);
        buttonPanel.add(redButton);
        buttonPanel.add(yellowButton);
        buttonPanel.add(new JLabel("Transparency:"));
        buttonPanel.add(transparencySlider);
        buttonPanel.add(new JLabel("Line Thickness:"));
        buttonPanel.add(thicknessComboBox);
        buttonPanel.add(new JLabel("Scaling:"));
        buttonPanel.add(scalingComboBox);
        buttonPanel.add(new JLabel("Rotation:"));
        buttonPanel.add(rotationComboBox);

        // Panel za risuvane
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                for (ShapeInterface shape : shapes) {
                    g2d.setColor(shape.getColor());
                    g2d.setStroke(shape.getStroke());
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, shape.getTransparency()));
                    g2d.draw(shape.getTransformedShape());
                }
            }
        };
        drawingPanel.addMouseListener(new DrawingMouseListener());
        drawingPanel.addMouseMotionListener(new DrawingMouseMotionListener());
        drawingPanel.setBackground(Color.GRAY);

        //
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);
        setExtendedState(MAXIMIZED_BOTH); // Open in fullscreen

        // action listeners
        rectangleButton.addActionListener(new ShapeSelectionListener(ShapeType.RECTANGLE));
        circleButton.addActionListener(new ShapeSelectionListener(ShapeType.CIRCLE));
        lineButton.addActionListener(new ShapeSelectionListener(ShapeType.LINE));
        pointButton.addActionListener(new ShapeSelectionListener(ShapeType.POINT));
        triangleButton.addActionListener(new ShapeSelectionListener(ShapeType.TRIANGLE));
        ellipseButton.addActionListener(new ShapeSelectionListener(ShapeType.ELLIPSE));
        moveCheckBox.addActionListener(new MoveCheckBoxListener());
        clearButton.addActionListener(new ClearButtonListener());
        blueButton.addActionListener(new ColorSelectionListener(Color.BLUE));
        redButton.addActionListener(new ColorSelectionListener(Color.RED));
        yellowButton.addActionListener(new ColorSelectionListener(Color.YELLOW));
        transparencySlider.addChangeListener(new TransparencySliderListener());
        thicknessComboBox.addActionListener(new ThicknessComboBoxListener());

        setVisible(true);
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shapes.clear();
            drawingPanel.repaint();
        }
    }


    private class ShapeSelectionListener implements ActionListener {
        private ShapeType shapeType;

        public ShapeSelectionListener(ShapeType shapeType) {
            this.shapeType = shapeType;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            shapeFactory.setShapeType(shapeType);
            moveMode = false; // Exit move mode when selecting a shape
        }
    }

    private class ColorSelectionListener implements ActionListener {
        private Color color;

        public ColorSelectionListener(Color color) {
            this.color = color;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            currentColor = color;
            if (selectedShape != null) {
                selectedShape.setColor(currentColor);
                drawingPanel.repaint();
            }
        }
    }
    private class MoveCheckBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            moveMode = ((JCheckBox) e.getSource()).isSelected();
            selectedShape = null;
        }
    }

    private class TransparencySliderListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            transparency = (float) source.getValue() / 100.0f;
            if (selectedShape != null) {
                selectedShape.setTransparency(transparency);
                drawingPanel.repaint();
            }
        }
    }

    private class ThicknessComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<Double> source = (JComboBox<Double>) e.getSource();
            stroke = new BasicStroke(source.getItemAt(source.getSelectedIndex()).floatValue());
            if (selectedShape != null) {
                selectedShape.setStroke(stroke);
                drawingPanel.repaint();
            }
        }
    }

    private class DrawingMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (moveMode) {

                selectedShape = (ShapeInterface) getSelectedShape(e.getPoint());
                lastMousePosition = new java.awt.geom.Point2D.Double(e.getX(), e.getY());
                if (selectedShape != null) {
                    shapes.remove(selectedShape);
                    shapes.add(selectedShape);
                    drawingPanel.repaint();
                }
            } else {
                double scaling = (double) scalingComboBox.getSelectedItem();
                int rotation = (int) rotationComboBox.getSelectedItem();
                Shape newShape = (Shape) shapeFactory.createShape(e.getX(), e.getY(), currentColor, stroke, transparency, scaling, rotation);
                shapes.add((ShapeInterface) newShape);
                drawingPanel.repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (moveMode && selectedShape != null) {
                lastMousePosition = null;
                selectedShape = null;
            }
        }
    }

    private class DrawingMouseMotionListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            if (moveMode && selectedShape != null && lastMousePosition != null) {
                int dx = e.getX() - (int) lastMousePosition.getX();
                int dy = e.getY() - (int) lastMousePosition.getY();
                selectedShape.move(dx, dy);
                lastMousePosition.setLocation(e.getX(), e.getY());
                drawingPanel.repaint();
            }
        }
    }

    private Shape getSelectedShape(java.awt.Point point) {
        for (ShapeInterface shape : shapes) {
            if (shape.getTransformedShape().contains(point)) {
                return (Shape) shape;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DrawingFrame());
    }
}
