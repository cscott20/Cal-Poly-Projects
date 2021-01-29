class Point{
    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
         }
    
    public double getX(){return x;}
     
    public double getY(){return y;}

    public double getRadius(){return (Math.pow((x*x + y*y), .5));}

    public double getAngle(){
        if( x != 0 && y != 0){
            if (x > 0){
            return (Math.atan(y/x));}
            if (y > 0){
            return(Math.atan(y/(x*-1)) + (Math.PI / 2));
            }
            else{
            return(Math.atan(y/(x*-1)) - (Math.PI / 2));
                }}
        else{
            if (x == 0){
                if (y > 0){ 
                    return  (Math.PI / 2.0);
                      }
                if (y < 0){
                    return (-1.0 * Math.PI / 2.0);
                    }
                else{
                    return 0.0;
                    }
                }
            else {
                if (x > 0){
                    return 0.0;
                }
                else{
                    return Math.PI;
                    }
                    }
            }
                            }
 
    public Point rotate90(){Point newPoint = new Point(-y, x); return newPoint;}


}
