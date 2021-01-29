public class Buy
{
    private String product_id;
    private int price;
    private int count;
   
    public Buy(String product_id, int price, int count)
    {
        this.product_id = product_id;
        this.price = price;
        this.count = 0;
    }

    public void incrementCount(){this.count ++;}
    public int getPrice(){return this.price;}
    public String getProductID(){return this.product_id;}
}
