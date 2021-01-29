import java.util.List;
import java.util.Objects;

class Student
{
   private final String surname;
   private final String givenName;
   private final int age;
   private final List<CourseSection> currentCourses;

   public Student(final String surname, final String givenName, final int age,
      final List<CourseSection> currentCourses)
   {
      this.surname = surname;
      this.givenName = givenName;
      this.age = age;
      this.currentCourses = currentCourses;
   }
    
    public boolean equals(Object o)
    {
        if (o != null)
            if (o.getClass() == this.getClass())
                if (Objects.equals(((Student)o).surname, this.surname) && Objects.equals(((Student)o).givenName, this.givenName) && Objects.equals(((Student)o).currentCourses, this.currentCourses) && this.age == (((Student)o).age ))
                    return true;
        return false;
    }
    
    public int hashCode()
    {
        return(Objects.hash(surname, givenName, age, currentCourses));
   }     

}
