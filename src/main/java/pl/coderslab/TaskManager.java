package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {

        //pobranie zawartości pliku i zmiana na tablicę
        String [][] tasks = convertToArr();
        String inputOption = chooseOption();

        while (!inputOption.equals("exit")) {
            if (inputOption.equals("list")) {
                list(tasks);
            } else if (inputOption.equals("add")) {
                tasks = addRow(tasks);
            } else if (inputOption.equals("remove")) {
                
            }
            inputOption = chooseOption();
        }

            save(tasks);

        }


    //pobranie zawarości pliku i zamiana na tablicę [][]
    public static String[][] convertToArr() {

        File file = new File("tasks.csv");
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

    //dynamiczne dodawanie wierszy do tablicy dwuwymiarowej
    public static String [][] addNewElement(String [][] arr, String [] element) {
        arr = Arrays.copyOf(arr, arr.length+1);
        arr[arr.length-1] = element;
        return arr;
    }

    //wybór opcji
    public static String chooseOption(){

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
        return input;

    }

    //dodanie wiersza
    public static String [][] addRow(String [][]arr) {
        String [] newRow = {"ble ble","ble","ble"};
        arr = addNewElement(arr, newRow);
        return arr;
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

        try (FileWriter fileWriter = new FileWriter("tasks.csv")) {
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
