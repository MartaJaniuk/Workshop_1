package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println(ConsoleColors.BLUE + "Please select an option");
        System.out.println(ConsoleColors.RESET + "add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
        String input = scanner.nextLine();

        while (!input.equals("add") && !input.equals("remove") && !input.equals("list") && !input.equals("exit")){
            System.out.println("Select right option: add, remove, list, exit");
            input = scanner.nextLine();
        }
        //list();


    }

    public static void list() {

        File file = new File("tasks.csv");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                System.out.println(input);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Brak pliku");
        }
    }

}
