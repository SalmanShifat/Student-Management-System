import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class main {
    public static Scanner input = new Scanner(System.in);
    public static Map<Integer, Student> studentList = new HashMap<>();
    private static final String FILE_NAME = "students.ser";


    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";

 
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void loadDataFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            studentList = (Map<Integer, Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            printBoxedMessage("Error loading saved student data: " + e.getMessage(), RED);
        }
    }

    public static void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(studentList);
        } catch (IOException e) {
            printBoxedMessage("Error saving student data: " + e.getMessage(), RED);
        }
    }


    public static void printBoxedMessage(String msg, String color) {
        int width = msg.length() + 4;
        String line = "─".repeat(width);
        System.out.println(color + "┌" + line + "┐");
        System.out.println("│  " + msg + "  │");
        System.out.println("└" + line + "┘" + RESET);
    }


    public static void printMenu() {
        System.out.println(CYAN + "╔═════════════════════════════════════════════╗");
        System.out.println("║ " + BOLD + "          STUDENT PORTAL DASHBOARD          " + RESET + CYAN + "║");
        System.out.println("╠═════════════════════════════════════════════╣");
        System.out.println("║  [1] Add Student Details                    ║");
        System.out.println("║  [2] Find Student by Roll                   ║");
        System.out.println("║  [3] Show All Student List                  ║");
        System.out.println("║  [4] Delete Student                         ║");
        System.out.println("║  [5] Update Student                         ║");
        System.out.println("║  [6] Exit                                   ║");
        System.out.println("╚═════════════════════════════════════════════╝" + RESET);
    }

    public static int validRoll() {
        while (true) {
            System.out.print(YELLOW + "Enter Roll Number (0 to 9): " + RESET);
            try {
                return input.nextInt();
            } catch (InputMismatchException e) {
                printBoxedMessage("Invalid input! Please enter a valid number.", RED);
                input.nextLine();
            }
        }
    }

    public static boolean NameOkey(String name) {
        if (name.trim().isEmpty()) return false;
        for (char c : name.toCharArray()) {
            if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z') && (c != ' ') && (c != '.')) {
                return false;
            }
        }
        return true;
    }

    public static String validName() {
        System.out.print(YELLOW + "Enter Student Name: " + RESET);
        String name = input.nextLine();
        while (!NameOkey(name)) {
            System.out.print(RED + "Invalid name! Use A-Z, a-z, spaces, or dots.\nEnter name again: " + RESET);
            name = input.nextLine();
        }
        return name.toUpperCase();
    }

    public static boolean SessionCheck(String session) {
        for (char c : session.toCharArray()) {
            if (!(c >= '0' && c <= '9') && (c != '-')) return false;
        }
        return true;
    }

    public static String validSession() {
        System.out.print(YELLOW + "Enter Session (e.g. 2023-2024): " + RESET);
        String session = input.nextLine();
        while (!SessionCheck(session) || session.trim().isEmpty()) {
            System.out.print(RED + "Invalid format! Use format like (2023-2024) without spaces:\n" + RESET);
            session = input.nextLine();
        }
        return session;
    }

    public static int validOperation() {
        while (true) {
            System.out.print(BOLD + "Select Option (1-6): " + RESET);
            try {
                return input.nextInt();
            } catch (InputMismatchException e) {
                printBoxedMessage("Invalid option! Enter a number between 1 and 6.", RED);
                input.nextLine();
            }
        }
    }

    public static void Input() {
        clearScreen();
        System.out.println(CYAN + "┌─────────────────────────────────────────┐");
        System.out.println("│             ADD NEW STUDENT             │");
        System.out.println("└─────────────────────────────────────────┘" + RESET);

        int roll = validRoll();
        while (studentList.containsKey(roll)) {
            System.out.println(RED + "Roll number already exists! Try another." + RESET);
            roll = validRoll();
        }
        input.nextLine();

        String name = validName();
        String session = validSession();

        Student student = new Student(name, roll, session);
        studentList.put(roll, student);
        saveDataToFile();
        clearScreen();

        printBoxedMessage("Student Added Successfully!", GREEN);
        ShowByRoll(roll);

    }

    public static void ShowByRoll(int roll) {
        System.out.println("\n");
        if (studentList.containsKey(roll)) {
            Student s = studentList.get(roll);
            System.out.println(CYAN + "\n┌───────────┬──────────────────────────┬─────────────┐");
            System.out.println("│ Roll      │ Name                     │ Session     │");
            System.out.println("├───────────┼──────────────────────────┼─────────────┤");
            System.out.printf("│ %-9d │ %-24s │ %-11s │\n", s.roll, s.name, s.session);
            System.out.println("└───────────┴──────────────────────────┴─────────────┘" + RESET);
        } else {
            printBoxedMessage("Student with Roll " + roll + " not found.", RED);
        }
    }

    public static void ShowAllStudents() {
        clearScreen();
        if (studentList.isEmpty()) {
            printBoxedMessage("No student records found.", YELLOW);
            return;
        }

        System.out.println(CYAN + "┌───────────┬──────────────────────────┬─────────────┐");
        System.out.println("│ " + BOLD + "Roll     " + RESET + CYAN + " │ " + BOLD + "Name                    " + RESET + CYAN + " │ " + BOLD + "Session    " + RESET + CYAN + " │");
        System.out.println("├───────────┼──────────────────────────┼─────────────┤");

        for (Student s : studentList.values()) {
            System.out.printf("│ %-9d │ %-24s │ %-11s │\n", s.roll, s.name, s.session);
        }

        System.out.println("└───────────┴──────────────────────────┴─────────────┘" + RESET);
    }

    public static void UpdateStudent(int roll) {
        ShowByRoll(roll);
        input.nextLine();
        
        String name = validName();
        String session = validSession();

        Student student = new Student(name, roll, session);
        studentList.put(roll, student);
        saveDataToFile();

        printBoxedMessage("Student details updated successfully!", GREEN);
    }

    public static void main(String[] args) {
        loadDataFromFile();

        while (true) {
            printMenu();
            int operation = validOperation();
            input.nextLine(); 

            switch (operation) {
                case 1 -> Input();
                case 2 -> {
                    int roll = validRoll();
                    ShowByRoll(roll);
                    input.nextLine();
                }
                case 3 -> ShowAllStudents();
                case 4 -> {
                    int roll = validRoll();
                    if (studentList.containsKey(roll)) {
                        studentList.remove(roll);
                        saveDataToFile();
                        printBoxedMessage("Student with Roll " + roll + " deleted.", GREEN);
                    } else {
                        printBoxedMessage("Student with Roll " + roll + " not found.", RED);
                    }
                    input.nextLine();
                }
                case 5 -> {
                    int roll = validRoll();
                    if (studentList.containsKey(roll)) {
                        UpdateStudent(roll);
                    } else {
                        printBoxedMessage("Student with Roll " + roll + " not found.", RED);
                        input.nextLine();
                    }                    
                }
                case 6 -> {
                    saveDataToFile();
                    printBoxedMessage("Exiting program. Goodbye!", GREEN);
                    return;
                }
                default -> printBoxedMessage("Invalid choice! Choose between 1 and 6.", RED);
            }

            System.out.println("\nPress Enter to continue...");
            input.nextLine();
            clearScreen();
        }
    }
}