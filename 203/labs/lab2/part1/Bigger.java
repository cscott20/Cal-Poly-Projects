import java.util.Arrays;
class Bigger{
    
    public static double whichIsBigger(Circle circ, Rectangle rect, Polygon poly)
    {
    double[] perims = {Util.perimeter(circ), Util.perimeter(rect), Util.perimeter(poly)};
    Arrays.sort(perims);
    return(perims[perims.length - 1]);
    }
}
