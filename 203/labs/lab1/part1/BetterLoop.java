class BetterLoop
{
   public static boolean contains(int [] values, int v)
   {

    for (int f : values)
    {
        if (v == f)
            return true;
    }
    return false;
   }
}
