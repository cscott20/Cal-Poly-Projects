import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class PartTwoTestCases
{
   public static final double DELTA = 0.00001;

   @Test
   public void testCircleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getCenter", "getRadius", "perimeter");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Point.class, double.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[0], new Class[0]);

      verifyImplSpecifics(Circle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getTopLeft", "getBottomRight", "perimeter");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Point.class, Point.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[0], new Class[0]);

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testPolygonImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getPoints", "perimeter");

      final List<Class> expectedMethodReturns = Arrays.asList(
         List.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[0]);

      verifyImplSpecifics(Polygon.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   private static void verifyImplSpecifics(
      final Class<?> clazz,
      final List<String> expectedMethodNames,
      final List<Class> expectedMethodReturns,
      final List<Class[]> expectedMethodParameters)
      throws NoSuchMethodException
   {
      assertEquals("Unexpected number of public fields",
         0, clazz.getFields().length);

      final List<Method> publicMethods = Arrays.stream(
         clazz.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toList());

      assertEquals("Unexpected number of public methods",
         expectedMethodNames.size(), publicMethods.size());

      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodReturns.size());
      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodParameters.size());

      for (int i = 0; i < expectedMethodNames.size(); i++)
      {
         Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
            expectedMethodParameters.get(i));
         assertEquals(expectedMethodReturns.get(i), method.getReturnType());
      }
   }

    
    @Test
    public void testPerimPoly() {
        List < Point >points = new ArrayList < Point >(); 
        points.add(new Point(0,0));
        points.add(new Point(3,0));
        points.add(new Point(0,4));
        Polygon poly = new Polygon(points);
        double d = poly.perimeter();
        assertEquals(12.0, d, DELTA);
   }
    
    @Test
    public void testPerimPoly2() {
        List < Point >points = new ArrayList < Point >(); 
        points.add(new Point(0,0));
        points.add(new Point(3,4));
        points.add(new Point(6,8));
        points.add(new Point(6,0));
        Polygon poly = new Polygon(points);
        double d = poly.perimeter();
        assertEquals(24.0, d, DELTA);
   }
    @Test
    public void testPerimCir() {
        Point center = new Point(1,1);
        Circle c = new Circle(center, 0.5);
        double d = c.perimeter();
        assertEquals(Math.PI, d, DELTA);
   }
    @Test
    public void testPerimRect() {
        Point topL = new Point(-5,-8);
        Point botR = new Point(1, -1);
        Rectangle rect = new Rectangle(topL, botR);
        double d = rect.perimeter();
        assertEquals(26.0, d, DELTA);
   }
    @Test
    public void testBigger() {
        Point center = new Point(1,1);
        Point topL = new Point(-5,-8);
        Point botR = new Point(1, -1);
        List < Point >points = new ArrayList < Point >(); 
        points.add(new Point(0,0));
        points.add(new Point(3,0));
        points.add(new Point(0,4));
        Circle c = new Circle(center, .5);
        Rectangle r = new Rectangle(topL, botR);
        Polygon p = new Polygon(points);
        double d = Bigger.whichIsBigger(c, r, p);
        assertEquals(26.0, d, DELTA);
   }
    @Test
    public void testBigger2() {
        Point center = new Point(1,1);
        Point topL = new Point(-5,-8);
        Point botR = new Point(1, -1);
        List < Point >points = new ArrayList < Point >(); 
        points.add(new Point(0,0));
        points.add(new Point(30,0));
        points.add(new Point(0,40));
        Circle c = new Circle(center, .5);
        Rectangle r = new Rectangle(topL, botR);
        Polygon p = new Polygon(points);
        double d = Bigger.whichIsBigger(c, r, p);
        assertEquals(120.0, d, DELTA);
   }
    @Test
    public void testBigger3() {
        Point center = new Point(1,1);
        Point topL = new Point(-5,-8);
        Point botR = new Point(1, -1);
        List < Point >points = new ArrayList < Point >(); 
        points.add(new Point(0,0));
        points.add(new Point(3,0));
        points.add(new Point(0,4));
        Circle c = new Circle(center, 100);
        Rectangle r = new Rectangle(topL, botR);
        Polygon p = new Polygon(points);
        double d = Bigger.whichIsBigger(c, r, p);
        assertEquals(200 * Math.PI, d, DELTA);
   }
    @Test
    public void testBigger4() {
        Point center = new Point(8,-200);
        Point topL = new Point(-5,-8);
        Point botR = new Point(1, -1);
        List < Point >points = new ArrayList < Point >(); 
        points.add(new Point(0,0));
        points.add(new Point(3,0));
        points.add(new Point(0,4));
        Circle c = new Circle(center, 100);
        Rectangle r = new Rectangle(topL, botR);
        Polygon p = new Polygon(points);
        double d = Bigger.whichIsBigger(c, r, p);
        assertEquals(200 * Math.PI, d, DELTA);
   }
   
}
