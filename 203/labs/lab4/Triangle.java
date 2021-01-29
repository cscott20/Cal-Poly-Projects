import java.awt.Point;
import java.awt.Color;

class Triangle implements Shape
{
    private Color color;
    private Point a;
    private Point b;
    private Point c;

    public Triangle(Point a, Point b, Point c, Color color)
    {
    this.a = a;
    this.b = b;
    this.c = c;
    this.color = color;
    }

    public Point getVertexA()
    {
        return this.a;
    }
    public Point getVertexB()
    {
        return this.b;
    }
    public Point getVertexC()
    {
        return this.c;
    }


    public Color getColor()
    {
        return this.color;
    }

    public void setColor(Color c)
    {
        this.color = c;
    }

    public double getArea()
    {
        return(.5 * ( ((this.a.getX() * this.b.getY()) + (this.b.getX() * this.c.getY())) - ((this.a.getY() * this.b.getX()) + (this.b.getY() * this.c.getX())) ));
    }

    public double getPerimeter()
    {
        return (dist(this.a, this.b) + dist(this.b, this.c) + dist(this.c, this.a));
    }
    
    public void translate(Point p)
    {
        this.a.setLocation(this.a.getX() + p.getX(), this.a.getY() + p.getY());
        this.b.setLocation(this.b.getX() + p.getX(), this.b.getY() + p.getY());
        this.c.setLocation(this.c.getX() + p.getX(), this.c.getY() + p.getY());
    }
    
    private double dist(Point a, Point b)
    {
        return Math.pow(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2), .5);
    }

    public boolean equals(Object t)

    {
        return (t != null && t.getClass() == this.getClass() && 
                this.color == ((Triangle)t).getColor() && this.a.equals(((Triangle)t).a) && this.b.equals(((Triangle)t).b) && this.c.equals(((Triangle)t).c));
    }
}
