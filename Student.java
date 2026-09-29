import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    String name, session;
    int roll;

    public Student(String name, int roll, String session){
        this.name = name;
        this.roll = roll;
        this.session = session;
    }
}