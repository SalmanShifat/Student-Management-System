import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public  class  main{
    public static Scanner input = new Scanner(System.in);
    public static Map<Integer, Student> studentList = new HashMap<>();
    private static final String FILE_NAME = "students.ser";

    public static void loadDataFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; 
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            studentList = (Map<Integer, Student>) ois.readObject();
            System.out.println("Loaded " + studentList.size() + " student record(s) from disk.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading saved student data: " + e.getMessage());
        }
    }


    public static void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(studentList);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }

    public static int validRoll(){
        while(true){
            System.out.print("Enter Your Roll(0 to 9): ");
            try {
                int value = input.nextInt();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                input.nextLine(); 
            }
        }
    }

    public static boolean NameOkey(String name){
        if(name.length() == 0) return false;
        for(var c : name.toCharArray()){
            if((c < 'A' || c > 'Z') && ( c < 'a' || c > 'z') && (c != ' ') && (c != '.')) return false;
        }
        return true;
    }

    public static String validName(){
        System.out.print("Enter your Name: ");
        String name = input.nextLine();
        while(!NameOkey(name)){
            System.out.print("Please use 'A' to 'Z' or 'a' to 'z' !\nEnter your name again: ");
            name = input.nextLine();
        }
        return name.toUpperCase();
    }

    public static int validOperation(){
        while(true){
            System.out.print("Enter an Integer Number (1-6): ");
            try {
                int value = input.nextInt();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                input.nextLine(); 
            }
        }
    }

    public static boolean SessionCheck(String session){
        for(var c : session.toCharArray()){
            if(!(c >= '0' && c <= '9') && (c != '-')) return  false;
        }
        return true;
    }

    public static String validSession(){
        String session;
        System.out.print("Enter your Session: ");
        session = input.nextLine();
        while(true){
            if(!SessionCheck(session)){
                System.out.print("Wrong input type ! \nPlease input as like (2023-2024) & Don't use any space!\n");
                session = input.nextLine();
            }else break;
        }
        return session;
    }



    public  static  void Input(){
        String name, session;
        int roll ;
        
        
        name = validName();
        roll = validRoll();
        while(studentList.containsKey(roll)){
            System.out.println("Roll number already exists. Please enter a unique Roll number.");
            roll = validRoll();
        }
        input.nextLine();
        
        session =  validSession();
        
        Student student  = new Student(name, roll, session); 
        studentList.put(roll, student);
        saveDataToFile();
        ShowByRoll(roll);
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

    public static void UpdateStudent(int roll) {
        System.out.println("Current Details:");
        ShowByRoll(roll);

        String name, session; 

        name = validName();
        session =  validSession();

        Student student  = new Student(name, roll, session); 
        studentList.put(roll, student);
        saveDataToFile();
        System.out.println("Student details updated.");
    }
    
    public static void main(String[] args) {
        loadDataFromFile(); 
        while(true){
            System.out.println("Welcome to student Portal:\n Enter the command\n1 -> Add student details\n2 -> Find Student\n3 -> Show all student list\n4 -> Delete Student\n5 -> Update Student\n6 -> Exit ");
            
            int operation = validOperation();
            input.nextLine();

            if(operation == 1){
                Input();
            }else if(operation == 2){
                int roll = validRoll();
                ShowByRoll(roll);  
            }else if(operation == 3){
                if(studentList.isEmpty()){
                    System.out.println("No students found.");
                } else {
                    System.out.println("All Student List:");
                    for (Student student : studentList.values()) {
                        System.out.println("Name: " + student.name + ", Roll: " + student.roll + ", Session: " + student.session);
                    }
                }
            }else if(operation == 4) {
                System.out.println("Enter Roll to delete");
                int roll = validRoll();
                if(studentList.containsKey(roll)){
                    studentList.remove(roll);
                    saveDataToFile();
                    System.out.println("Student with Roll " + roll + " deleted.");
                }else{
                    System.out.println("Student with Roll " + roll + " not found.");
                }                
            }else if(operation == 5) {
                System.out.print("Enter Roll & Session for update\n");
                int roll = validRoll();
                input.nextLine();

                if(studentList.containsKey(roll)) {
                    UpdateStudent(roll);
                } else {
                    System.out.println("Student with Roll " + roll + " not found.");
                }
            } else if(operation == 6) {
                saveDataToFile();
                System.out.println("Exiting the program. Goodbye!");
                break;
            }
            else{
                System.out.println("Wrong input! \nPlease input 1 to 6!");
            }
        }
    }
}