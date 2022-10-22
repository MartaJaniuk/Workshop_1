package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    private static final String fileName = "tasks.csv";
    private static final String listOption = "list";
    private static final String addOption = "add";
    private static final String removeOption = "remove";
    private static final String exitOption = "exit";
    private static final String [] options = {addOption, removeOption, listOption, exitOption};
    private static String [][] tasks;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //pobranie zawartości pliku i zmiana na tablicę
        tasks = convertToArr();
        //wybór opcji
        String inputOption = chooseOption(scanner);

        while (!inputOption.equals(exitOption)) {
            if (inputOption.equals(listOption)) {
                list(tasks);
            } else if (inputOption.equals(addOption)) {
                tasks = addRow(tasks, scanner);
            } else if (inputOption.equals(removeOption)) {
                tasks = removeRow(tasks, scanner);
            }
            inputOption = chooseOption(scanner);
        }
            save(tasks);
        }

    //pobranie zawartości pliku i zamiana na tablicę [][]
    public static String[][] convertToArr() {

        File file = new File(fileName);
        String [][] tasksArr = new String [0][3];

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                String [] rowArr = row.split(",");
                tasksArr = addNewElement(tasksArr, rowArr);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Brak pliku");
        }
        return tasksArr;
    }

    // dodawanie wierszy do tablicy dwuwymiarowej
    public static String [][] addNewElement(String [][] arr, String [] element) {
        arr = Arrays.copyOf(arr, arr.length+1);
        arr[arr.length-1] = element;
        return arr;
    }

    //wybór opcji
    public static String chooseOption(Scanner scanner){

        System.out.println(ConsoleColors.BLUE + "Please select an option" + ConsoleColors.RESET);
        for (String singleOption: options) {
            System.out.println(singleOption);
        }

        String input = scanner.nextLine();

        while (!input.equals(addOption) && !input.equals(removeOption) && !input.equals(listOption) && !input.equals(exitOption)){
            System.out.println("Select right option: add, remove, list, exit");
            input = scanner.nextLine();
        }
        return input;
    }

    //dodanie wiersza
    public static String [][] addRow(String [][]arr, Scanner scanner) {

        System.out.println("Add task description");
        String description = scanner.nextLine();
        System.out.println("Add task due date");
        String date = scanner.nextLine();

        System.out.println("Is your task important: true/false");
        String importance = scanner.nextLine();
        while(!importance.equals("true") && !importance.equals("false")) {
            System.out.println("You can only choose true or false.");
            System.out.println("Is your task important: true/false");
            importance = scanner.nextLine();
        }

        String [] newRow = {description,date,importance};
        arr = addNewElement(arr, newRow);
        System.out.println("Row [" + description + "," + date + "," + importance + "] added");
        return arr;
    }

    //usunięcie wiersza
    public static String [][] removeRow(String arr[][], Scanner scanner) {

        System.out.println("Please select number to remove. Number must be between 0 and " + (arr.length-1));
        String input = scanner.nextLine();
        while (!isGreaterEqualZero(input)) {
            System.out.println("Incorrect number. Give correct number");
            input = scanner.nextLine();
        }
        int numberToRemove = Integer.parseInt(input);

        arr = ArrayUtils.remove(arr, numberToRemove);
        System.out.println("Row " + numberToRemove + " succcessfully removed");
        return arr;
    }

    public static boolean isGreaterEqualZero(String str){
        if (NumberUtils.isParsable(str)) {
            if (Integer.parseInt(str)>=0 && Integer.parseInt(str)<tasks.length) {
                return true;
            }
        }
        return false;
    }

    //wyświetlenie zawartości tablicy
    public static void list(String arr[][]) {
        int rowNumber = 0;
        for (int i=0; i< arr.length; i++) {
            String row = String.join(",",arr[i]);
            System.out.println(rowNumber + " : " + row);
            rowNumber ++;
        }
    }

    //zapisanie tablicy do pliku
    public static void save(String [][] arr) {

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            for (int i=0; i<arr.length; i++) {
                String row = String.join(",", arr[i]);
                fileWriter.append(row).append("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
