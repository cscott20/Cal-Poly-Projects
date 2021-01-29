public class View
{
    private String product_id;
    private int price;
    private int viewcount;
 
    public View(String product_id, int price)
    {
        this.product_id = product_id;
        this.price = price;
        this.viewcount = 0;
    }

    public void incrementViews(){this.viewcount ++;}
    public int getPrice(){return this.price;}
    public String getProductID(){return this.product_id;}
}
