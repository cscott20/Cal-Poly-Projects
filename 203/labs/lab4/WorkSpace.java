import java.util.*;
import java.awt.Color;
import java.awt.Point;

class WorkSpace
{
    private List<Shape> shapeList;

    public WorkSpace()
    {
        this.shapeList = new ArrayList<Shape>();
    }

    public void add(Shape shape)
    {
        this.shapeList.add(shape);
    }

    public Shape get(int index)
    {
        return this.shapeList.get(index);
    }

    public int size()
    {
        return this.shapeList.size();
    }

    public List<Circle> getCircles()
    {
        List<Circle> circleList = new ArrayList<Circle>(); 
        
        for (Shape shape : this.shapeList)
        {
            if (shape instanceof Circle)
            {   
                circleList.add((Circle)shape);
            }
        }
        return circleList;
    }

    public List<Rectangle> getRectangles()
    {
        List<Rectangle> rectangleList = new ArrayList<Rectangle>(); 
        
        for (Shape shape : this.shapeList)
        {
            if ( shape instanceof Rectangle)
            {   
                rectangleList.add((Rectangle)shape);
            }
        }
        return rectangleList;
    }

    public List<Triangle> getTriangles()
    {
        List<Triangle> triangleList = new ArrayList<Triangle>(); 
        
        for (Shape shape : this.shapeList)
        {
            if ( shape instanceof Triangle)
            {   
                triangleList.add((Triangle)shape);
            }
        }
        return triangleList;
    }
    
    public List<Shape> getShapesByColor(Color color)
    {
        List<Shape> coloredList = new ArrayList<Shape>(); 
        
        for (Shape shape : this.shapeList)
        {
            if (shape.getColor() == color)
            {   
                coloredList.add(shape);
            }
        }
        return coloredList;
    }


    public double getAreaOfAllShapes() 
    {
        double sum = 0.0;

        for (Shape shape : this.shapeList)
        {
            sum += shape.getArea();
        }

        return sum;
    }


    public double getPerimeterOfAllShapes()     
    {
        double sum = 0;

        for (Shape shape : this.shapeList)
        {
            sum += shape.getPerimeter();
        }
        return sum;
    }

}
