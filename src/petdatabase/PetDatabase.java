package petdatabase;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class PetDatabase {

    public static void main(String[] args) {

        System.out.println("Pet Database Program\n");

        boolean runLoop = true;
        int choice;
        Scanner sc = new Scanner(System.in);
        ArrayList<String[]> pets = new ArrayList<>();

        while (runLoop) {

            System.out.println("What would you like to do?");
            System.out.println("1) View all pets");
            System.out.println("2) Add more pets");
            System.out.println("3) Exit program");
            System.out.print("Your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    displayPets(pets);
                    break;
                case 2:
                    while (true) {

                        System.out.print("Add pet (name, age): ");
                        String data = sc.nextLine();
                        if (Objects.equals(data, "done")) break;
                        else pets.add(data.split(" "));

                    }
                    break;
                default:
                    runLoop = false;

            }

        }

    }

    private static void displayPets(ArrayList<String[]> pets) {

        System.out.println("+----------------------+");
        System.out.println("| ID | NAME      | AGE |");
        System.out.println("+----------------------+");

        for (int i = 0; i < pets.size(); i++)
            System.out.printf("| %2d | %-9s | %3s |\n", i, pets.get(i)[0], pets.get(i)[1]);

        System.out.println("+----------------------+");

    }

}