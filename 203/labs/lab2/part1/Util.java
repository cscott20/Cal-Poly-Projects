import java.util.List;
class Util{
    
    public static double perimeter(Rectangle rect)
    {
        Point topLeft = rect.getTopLeft();
        Point botRight = rect.getBottomRight();
        double toplx = topLeft.getX();
        double toply = topLeft.getY();
        double botrx = botRight.getX();
        double botry = botRight.getY();
        double length = Math.abs(botrx - toplx);
        
        double width = Math.abs(toply - botry);

    return ( 2 * (width + length)); 
    }

    public static double perimeter(Polygon poly)
    {
        double dist = 0;
        List<Point> points = poly.getPoints();
        int count = 0;
        Point PrevPoint = points.get(0);
        for(Point point : points)
        {
            if (count > 0)
                {
                double distBetween2;
                distBetween2 = Math.pow((Math.pow(PrevPoint.getX() - point.getX(), 2) + Math.pow(PrevPoint.getY() - point.getY(), 2)), .5);
                dist += distBetween2;
                }
            PrevPoint = point;
            count ++;
        }
        Point first = points.get(0);
        Point last = points.get(points.size() - 1);
        double distBetween2 = Math.pow(Math.pow(first.getX() - last.getX(), 2) + Math.pow(first.getY() - last.getY(), 2), .5);
        dist += distBetween2;
    return dist;
    } 

    public static double perimeter(Circle cir){
        double r = cir.getRadius();
        return(2.0 * r * Math.PI);
    }

}
