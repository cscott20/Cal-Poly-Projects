import java.util.Arrays;
class Bigger{
    
    public static double whichIsBigger(Circle circ, Rectangle rect, Polygon poly)
    {
    double[] perims = {circ.perimeter(), rect.perimeter(), poly.perimeter()};
    Arrays.sort(perims);
    return(perims[perims.length - 1]);
    }
}
