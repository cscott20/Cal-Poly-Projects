import java.time.LocalTime;

class CourseSection
{
    private final String prefix;
    private final String number;
    private final int enrollment;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public CourseSection(final String prefix, final String number,
        final int enrollment, final LocalTime startTime, final LocalTime endTime)
    {
        this.prefix = prefix;
        this.number = number;
        this.enrollment = enrollment;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public boolean equals(Object o)
    {
        if (o == null || this.getClass() != o.getClass())
        {
            return false;
        }
        CourseSection ob = (CourseSection)o;
        if (this.prefix!= null && !(this.prefix.equals(ob.prefix)))
        {
            return false;
        }       
        else if (this.prefix == null && ob.prefix != null)
        {
            return false;
        }      
        if (this.endTime!= null && !(this.endTime.equals(ob.endTime)))
        {
            return false;
        }
        else if (this.endTime == null && ob.endTime != null)
        {
            return false;
        }      
        if (this.startTime!= null && !(this.startTime.equals(ob.startTime)))
        {
            return false;
        }       
        else if (this.startTime == null && ob.startTime != null)
        {
            return false;
        }      

        if (this.number!= null && !(this.number.equals(ob.number)))
        {
            return false;
        }       
        else if (this.number == null && ob.number != null)
        {
            return false;
        }      

        return( ob.enrollment == (this.enrollment));
    }
    
    public int hashCode()
    {
        int hash = 1;
        if (this.prefix != null)
        {
            hash += this.prefix.hashCode();
        }
        hash *= 31;
        if (this.number != null)
        {
            hash += this.number.hashCode();
        }
        hash *= 31;
        if (this.startTime != null)
        {
            hash += this.startTime.hashCode();
        }
        hash *= 31;
        if (this.endTime != null)
        {
            hash += this.endTime.hashCode();
        }
        hash *= 31;
        hash += ((Integer)this.enrollment).hashCode();
        return hash; 
   }     
                
    
    // additional likely methods not defined since they are not needed for testing
}
