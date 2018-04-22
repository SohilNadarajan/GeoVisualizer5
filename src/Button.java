
import java.awt.Color;

import maxim.shapes.*;
import processing.core.PApplet;

public class Button
{

    public static final int RECTANGLE = 1, CIRCLE = 2;
    private String name;
    private int buttonShape;
    private double x, y, width, height;
    private Color color;
    private Rectangle rectangleButton;
    private Circle circleButton;

    public Button(String name, int shape, double x, double y, double width, double height)
    {
        this.name = name;
        this.buttonShape = shape;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        color = new Color(100, 100, 255);
    }

    public void draw(PApplet marker)
    {

        if (buttonShape == RECTANGLE)
        {
            rectangleButton = new Rectangle(x, y, width, height);
            rectangleButton.setFillColor(color);
            rectangleButton.draw(marker);
        }

        if (buttonShape == CIRCLE)
        {
            circleButton = new Circle(x, y, width, height);
            circleButton.setFillColor(color);
            circleButton.draw(marker);
        }

        marker.fill(255);
        marker.textSize(15);
        marker.textAlign(marker.CENTER, marker.CENTER);
        marker.text(name, (float) (x + (width) / 2), (float) (y + (height) / 2));

    }

    public boolean isPointInside(double x, double y)
    {
        if (buttonShape == RECTANGLE)
        {
            return rectangleButton.isPointInside(x, y);
        }

        if (buttonShape == CIRCLE)
        {
            double centerX = this.x + width / 2, centerY = this.y + height / 2;
            if ((Math.pow((x - centerX), 2) / Math.pow((width / 2), 2)) 
            + (Math.pow((y - centerY), 2) / Math.pow((height / 2), 2)) <= 1) {
            return true;
            } else {
            return false;
            }
        }

        return false;
    }

    // GETTERS AND SETTERS

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double getWidth()
    {
        return width;
    }

    public void setWidth(double width)
    {
        this.width = width;
    }

    public double getHeight()
    {
        return height;
    }

    public void setHeight(double height)
    {
        this.height = height;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
