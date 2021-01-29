import java.awt.Color;
import java.awt.Point;

class Circle implements Shape
{
    double radius;
    Point center;
    Color color;

    public Circle(double radius, Point center, Color color) 
    {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }
    
    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }
    
    public double getArea()
    {
        return (Math.PI * Math.pow(this.radius, 2));
    }
    
    public double getPerimeter()
    {
        return(2.0 * this.radius * Math.PI);
    }

    public void translate(Point p)
    {
        this.center.setLocation(center.getX() + p.getX(), center.getY() + p.getY());
    }

    public double getRadius() 
    {
        return radius;
    }
 
    public void setRadius(double radius) 
    {
        this.radius = radius;
    }

    public Point getCenter() 
    {
        return center;
    }

    public boolean equals(Object o) 
    {
        return (o!= null && o.getClass().equals(this.getClass()) &&
        this.center.equals(((Circle)o).getCenter()) && this.radius == ((Circle)o).getRadius() && this.color.equals(((Circle)o).getColor()));
    }
}
