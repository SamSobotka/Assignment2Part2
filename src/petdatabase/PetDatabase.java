package petdatabase;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class PetDatabase {

    private static final String ALL = "all";

    public static void main(String[] args) {

        System.out.println("Pet Database Program\n");

        boolean runLoop = true;
        int choice;
        Scanner sc = new Scanner(System.in);
        ArrayList<String[]> pets = new ArrayList<>();

        int numPetsAdded = 0;

        while (runLoop) {

            System.out.println("What would you like to do?");
            System.out.println("1) View all pets");
            System.out.println("2) Add more pets");
            System.out.println("3) Search pets by name");
            System.out.println("4) Search pets by age");
            System.out.println("5) Exit program");
            System.out.print("Your choice: ");
            choice = sc.nextInt(); sc.nextLine();
            System.out.println();

            switch (choice) {

                case 1:
                    displayPets(pets);
                    break;
                case 2:
                    while (true) {

                        System.out.print("Add pet (name, age): ");
                        String data = sc.nextLine();
                        if (Objects.equals(data, "done")) {
                            System.out.printf("%d pet(s) added.\n", numPetsAdded);
                            numPetsAdded = 0;
                            break;
                        }
                        else {
                            pets.add(data.split(" "));
                            numPetsAdded++;
                        }

                    }
                    break;
                case 3:
                    System.out.print("Enter a name to search: ");
                    String name = sc.nextLine();
                    displayPetsByName(pets, name);
                    break;
                case 4:
                    System.out.print("Enter an age to search: ");
                    int age = sc.nextInt(); sc.nextLine();
                    displayPetsByAge(pets, age);
                    break;
                default:
                    runLoop = false;

            }

        }

    }

    private static void displayPets(ArrayList<String[]> pets) {

        displayHeader();

        for (int i = 0; i < pets.size(); i++)
            displayRow(pets, i);

        displayFooter(pets.size());

    }

    private static void displayPetsByName(ArrayList<String[]> pets, String name) {

        displayHeader();

        boolean validName;
        for (int i = 0; i < pets.size(); i++) {
            validName = pets.get(i)[0].equalsIgnoreCase(name) || name.equals(ALL);
            if (validName)
                displayRow(pets, i);
        }

        displayFooter(pets.size());

    }

    private static void displayPetsByAge(ArrayList<String[]> pets, int age) {

        displayHeader();

        boolean validAge;
        for (int i = 0; i < pets.size(); i++) {
            validAge = pets.get(i)[1].equals(String.valueOf(age));
            if (validAge)
                displayRow(pets, i);
        }

        displayFooter(pets.size());

    }

    private static void displayRow(ArrayList<String[]> pets, int i) {
        System.out.printf("| %2d | %-9s | %3s |\n", i, pets.get(i)[0], pets.get(i)[1]);
    }

    private static void displayHeader() {
        System.out.println("+----------------------+");
        System.out.println("| ID | NAME      | AGE |");
        System.out.println("+----------------------+");
    }

    private static void displayFooter(int rowsInSet) {
        System.out.println("+----------------------+");
        System.out.printf("%d rows in set.\n", rowsInSet);
    }

}