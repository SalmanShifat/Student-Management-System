import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public  class  main{
    public static Scanner input = new Scanner(System.in);
    public static Map<Integer, Student> studentList = new HashMap<>();

    public  static  void Input(){
        String name;
        int roll , session;
        
        System.out.print("Enter your Name: ");
        name = input.nextLine();
        name = name.toUpperCase();
        
        System.out.print("Enter your Roll: ");
        roll = input.nextInt();
        while(studentList.containsKey(roll)){
            System.out.println("Roll number already exists. Please enter a unique Roll number.");
            System.out.print("Enter your Roll: ");
            roll = input.nextInt();
        }
        input.nextLine();
        
        System.out.print("Enter your Session: ");
        session = input.nextInt();
        
        Student student  = new Student(name, roll, session); 
        studentList.put(roll, student);
    }

    public static void ShowByRoll(int Roll){
        if(studentList.containsKey(Roll)){
            Student student = studentList.get(Roll);
            System.out.println("Student Details:");
            System.out.println("Name: " + student.name);
            System.out.println("Roll: " + student.roll);
            System.out.println("Session: " + student.session);
        } else {
            System.out.println("Student with Roll " + Roll + " not found.");    
        }
    }
    
    public static void main(String[] args) {
        while(true){
            System.out.println("Welcome to student Portal:\n Enter the command\n1 -> Add student details\n2 -> Find Student\n3 -> Show all student list\n4 -> Delete Student\n5 -> Update Student\n6 -> Exit ");
            
            System.out.print("Enter command (1-6): ");
            int operation = input.nextInt();
            input.nextLine();

            if(operation == 1){
                Input();
                input.nextLine();
            }else if(operation == 2){
                System.out.print("Enter Your Roll: ");
                int roll = input.nextInt();
                ShowByRoll(roll);  
            }else if(operation == 3){
                System.out.println("All Student List:");
                for (Student student : studentList.values()) {
                    System.out.println("Name: " + student.name + ", Roll: " + student.roll + ", Session: " + student.session);
                }
            }else if(operation == 4) {
                System.out.print("Enter Roll to delete: ");
                int roll = input.nextInt();
                studentList.remove(roll);
                System.out.println("Student with Roll " + roll + " deleted.");
            }else if(operation == 5) {
                System.out.print("Enter Roll to update: ");
                int roll = input.nextInt();
                if(studentList.containsKey(roll)) {
                    Student student = studentList.get(roll);
                    System.out.println("Current Details:");
                    System.out.println("Name: " + student.name);
                    System.out.println("Roll: " + student.roll);
                    System.out.println("Session: " + student.session);

                    System.out.print("Enter new Name: ");
                    String name = input.nextLine();
                    System.out.print("Enter new Session: ");
                    int session = input.nextInt();
                    input.nextLine();

                    studentList.remove(roll);
                    Student updatedStudent = new Student(name, roll, session);
                    studentList.put(roll, updatedStudent);
                    System.out.println("Student details updated.");
                } else {
                    System.out.println("Student with Roll " + roll + " not found.");
                }
            } else if(operation == 6) {
                System.out.println("Exiting the program. Goodbye!");
                break;
            }
            else{
                System.out.println("Wrong input! \n Please input 1 to 6!");
            }
        }
    }
}