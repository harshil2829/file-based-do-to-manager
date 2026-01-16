import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Todo {
    int id;
    String title;
    boolean done;

    Todo(int id, String title, boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }

    @Override
    public String toString() {
        return id + "," + title + "," + done;
    }

    static Todo fromString(String line) {
        String[] parts = line.split(",");
        return new Todo(
                Integer.parseInt(parts[0]),
                parts[1],
                Boolean.parseBoolean(parts[2])
        );
    }
}

public class day13 {

    static ArrayList<Todo> todos = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE = "todos.txt";

    public static void main(String[] args) {
        loadFromFile();

        int choice;
        do {
            System.out.println("\n===== File-Based To-Do App =====");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Done");
            System.out.println("4. Delete Task");
            System.out.println("5. Save & Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addTask();
                case 2 -> viewTasks();
                case 3 -> markDone();
                case 4 -> deleteTask();
                case 5 -> {
                    saveToFile();
                    System.out.println("Data saved. Goodbye!");
                }
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    static void addTask() {
        System.out.print("Enter Task ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Title: ");
        String title = sc.nextLine();

        todos.add(new Todo(id, title, false));
        System.out.println("Task added!");
    }

    static void viewTasks() {
        System.out.println("\nID | Title | Status");
        System.out.println("-------------------");
        for (Todo t : todos) {
            System.out.println(t.id + " | " + t.title + " | " + (t.done ? "Done" : "Pending"));
        }
    }

    static void markDone() {
        System.out.print("Enter Task ID: ");
        int id = sc.nextInt();

        for (Todo t : todos) {
            if (t.id == id) {
                t.done = true;
                System.out.println("Task marked as done!");
                return;
            }
        }
        System.out.println("Task not found!");
    }

    static void deleteTask() {
        System.out.print("Enter Task ID to delete: ");
        int id = sc.nextInt();

        todos.removeIf(t -> t.id == id);
        System.out.println("Task deleted (if existed).");
    }

    static void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            for (Todo t : todos) {
                bw.write(t.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file!");
        }
    }

    static void loadFromFile() {
        File file = new File(FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                todos.add(Todo.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading file!");
        }
    }
}
