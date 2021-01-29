import java.util.List;
class Polygon{
    private List<Point> points;

    public Polygon(List<Point> points){
        this.points = points;
         }
    
    public List<Point> getPoints(){return points;}
    
    public double perimeter()
    {
        double dist = 0;
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
}



