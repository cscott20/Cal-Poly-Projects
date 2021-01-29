class Rectangle{
    private Point topLeft;
    private Point bottomRight;

    public Rectangle(Point topLeft, Point bottomRight){
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
         }
    
    public Point getTopLeft(){return topLeft;}
    public Point getBottomRight(){return bottomRight;}
    
    public double perimeter()
    {
        double toplx = topLeft.getX();
        double toply = topLeft.getY();
        double botrx = bottomRight.getX();
        double botry = bottomRight.getY();
        double length = (botrx - toplx);
        if (length < 0)
        {
            length *= -1;
        }
        
        double width = (toply - botry);
        if (width < 0)
        {
            width *= -1;
        }
    return ( 2 * (width + length)); 
    }
    

}



