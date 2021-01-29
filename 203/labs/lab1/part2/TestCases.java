import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestCases
{
   @Test
   public void testPoint()
   {
    double Delta = .0001;
    
    Point p = new Point(1.7, 2.0);
    assertEquals(1.7, p.getX(), Delta);
    assertEquals(2.0, p.getY(), Delta);
    
    Point a = new Point(3.0, 4.0);
    assertEquals(5.0, a.getRadius(), Delta);
    
    Point b = new Point(4.0, 4.0);
    assertEquals(Math.PI/4, b.getAngle(), Delta);
    Point b1 = new Point(0.0, 4.0);
    assertEquals((Math.PI / 2), b1.getAngle(), Delta);
    Point b2 = new Point(4.0, 0.0);
    assertEquals(0.0, b2.getAngle(), Delta);
    Point b3 = new Point(-4.0, 0.0);
    assertEquals(Math.PI, b3.getAngle(), Delta);
    Point b4 = new Point(0.0, -5.0);
    assertEquals((-1 * Math.PI / 2), b4.getAngle(), Delta);
    Point b5 = new Point(-4.0, -4.0);
    assertEquals((-3.0 * Math.PI/4), b5.getAngle(), Delta);
    Point b6 = new Point(4.0, -4.0);
    assertEquals((-1.0 * Math.PI/4), b6.getAngle(), Delta);
    Point b7 = new Point(-4.0, 4.0);
    assertEquals((3.0 * Math.PI/4), b7.getAngle(), Delta);


    Point c = new Point(2.0, 1.0);
    Point d = c.rotate90();
    assertEquals(d.getX(), -1.0, Delta);
    assertEquals(d.getY(), 2.0 , Delta);

    }

}
