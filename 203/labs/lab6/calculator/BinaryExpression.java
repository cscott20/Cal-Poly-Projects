abstract class BinaryExpression implements Expression
{
    private final Expression lft;
    private final Expression rht;
    private final String operation;

    public BinaryExpression(final Expression lft, final Expression rht, String operation)
    {
        this.lft = lft;
        this.rht = rht;
        this.operation = operation;
   }

    public String toString()
    {
        return "(" + lft + " " + operation + " " + rht + ")";
    }

    public double evaluate(final Bindings bindings)
    {
        return(_applyOperator(lft.evaluate(bindings), rht.evaluate(bindings))); 
    }

    protected abstract double _applyOperator(double lft, double rht);
}
