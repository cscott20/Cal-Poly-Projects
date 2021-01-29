import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ExampleMap
{
   public static List<String> highEnrollmentStudents(
      Map<String, List<Course>> courseListsByStudentName, int unitThreshold)
   {
      List<String> overEnrolledStudents = new LinkedList<>();
    
    for (Map.Entry<String, List<Course>> entry : courseListsByStudentName.entrySet()){
         String student = entry.getKey();
         List<Course> courses = entry.getValue();
        int Units = 0;
        for (Course class_: courses){
        Units += class_.getNumUnits();
        }
        if (Units > unitThreshold){
            overEnrolledStudents.add(student);
        }
    }  

      return overEnrolledStudents;      
   }
}
