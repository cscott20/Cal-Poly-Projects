import java.awt.Point;
import java.awt.Color;

class Rectangle implements Shape{
    private Point topLeft;
    private double width;
    private double height;
    private Color color;

    public Rectangle(double width, double height, Point topLeft, Color color)
    {
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    public Point getTopLeft()
    {
        return topLeft;
    }
   
    public double getWidth()
    {
        return width;
    }
    
    public double getHeight()
    {
        return height;
    }
    
    public void setWidth(double width)
    {
        this.width = width;
    }
    
    public void setHeight(double height)
    {
        this.height = height;
    }
    
    public double getPerimeter()
    {
        return (2 * (width + height)); 
    }
    
    public boolean equals(Object o)
    {
        return (o!= null && o.getClass().equals(this.getClass()) && this.topLeft.equals(((Rectangle)o).getTopLeft()) &&
        this.width == ((Rectangle)o).getWidth() && this.height == ((Rectangle)o).getHeight() && this.color.equals(((Rectangle)o).getColor()));
    }

    public void translate(Point p)
    {
    this.topLeft.setLocation(this.topLeft.getX() + p.getX(), this.topLeft.getY() + p.getY());
    } 
    
    public double getArea()
    {
        return(width * height);
    }
    
    public void setColor(Color c)
    {
        this.color = c;
    }

    public Color getColor()
    {
        return this.color ;   
    }
}

