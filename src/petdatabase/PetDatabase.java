package petdatabase;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PetDatabase {

    private static final String ALL = "all";

    public static void main(String[] args) {

        System.out.println("Pet Database Program");

        String filename = "petDatabase.bin";

        boolean runLoop = true;
        int choice;
        Scanner sc = new Scanner(System.in);
        ArrayList<Pet> pets = new ArrayList<>();

        int numPetsAdded = 0;

        try (
                FileInputStream file = new FileInputStream(filename);
                ObjectInputStream input = new ObjectInputStream(file)
        ) {
            //noinspection unchecked
            pets = (ArrayList<Pet>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading file; has " + filename + " been created yet?");
            System.out.println(e.getMessage());
        }

        while (runLoop) {

            System.out.println("\nWhat would you like to do?");
            System.out.println("1) View all pets");
            System.out.println("2) Add more pets");
            System.out.println("3) Search pets by name");
            System.out.println("4) Search pets by age");
            System.out.println("5) Update an existing pet");
            System.out.println("6) Remove an existing pet");
            System.out.println("7) Exit program");
            printBlankLine();
            System.out.print("Your choice: ");
            choice = sc.nextInt(); sc.nextLine();
            printBlankLine();

            switch (choice) {

                case 1:
                    displayPets(pets);
                    break;

                case 2:
                    while (true) {

                        System.out.print("Add pet (name, age): ");
                        String data = sc.nextLine();

                        if (data.equals("done")) {
                            System.out.printf("%d pet(s) added.\n", numPetsAdded);
                            numPetsAdded = 0;
                            break;
                        } else if (pets.size() >= 5) {
                            System.out.println("Error: Database is full.");
                            break;
                        } else {

                            String[] pet = data.split(" ");
                            String name = pet[0];
                            int age;

                            if (checkInfo(pet)) {
                                age = Integer.parseInt(pet[1]);
                                pets.add(new Pet(name, age));
                                numPetsAdded++;
                            }

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

                case 5:
                    displayPets(pets);
                    printBlankLine();

                    System.out.print("Enter the ID of the pet you want to update: ");
                    int idToUpdate = sc.nextInt(); sc.nextLine();

                    String oldPetInfo;

                    try {
                        oldPetInfo = pets.get(idToUpdate).getName() + " " + pets.get(idToUpdate).getAge();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.printf("Error: Pet ID %d does not exist.\n", idToUpdate);
                        break;
                    }

                    while (true) {

                        System.out.print("Enter the new name and age: ");
                        String newPetInfo = sc.nextLine();
                        String[] newPet = newPetInfo.split(" ");

                        if (checkInfo(newPet)) {

                            pets.get(idToUpdate).setName(newPet[0]);
                            pets.get(idToUpdate).setAge(Integer.parseInt(newPet[1]));

                            System.out.printf("%s has been changed to %s.\n", oldPetInfo, newPetInfo);
                            break;

                        }

                    }
                    break;

                case 6:
                    displayPets(pets);
                    printBlankLine();

                    System.out.print("Enter the ID of the pet you want to remove: ");
                    int idToRemove = sc.nextInt(); sc.nextLine();

                    String removedPetInfo;

                    try {
                        removedPetInfo = pets.get(idToRemove).getName() + " " + pets.get(idToRemove).getAge();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.printf("Error: Pet ID %d does not exist.\n", idToRemove);
                        break;
                    }

                    pets.remove(idToRemove);

                    System.out.printf("%s has been removed.\n", removedPetInfo);
                    break;

                default:
                    runLoop = false;

            }

        }

        try (
                FileOutputStream file = new FileOutputStream(filename);
                ObjectOutputStream output = new ObjectOutputStream(file)
        ) {
            output.writeObject(pets);
        } catch (IOException e) {
            System.out.println("Error: database not saved");
            e.printStackTrace();
        }

    }

    private static void printBlankLine() {
        System.out.println();
    }

    private static void displayPets(ArrayList<Pet> pets) {

        displayHeader();

        for (int i = 0; i < pets.size(); i++)
            displayRow(pets, i);

        displayFooter(pets.size());

    }

    private static void displayPetsByName(ArrayList<Pet> pets, String name) {

        displayHeader();

        boolean validName;
        int petsDisplayed = 0;
        for (int i = 0; i < pets.size(); i++) {
            validName = pets.get(i).getName().equalsIgnoreCase(name) || name.equals(ALL);
            if (validName) {
                displayRow(pets, i);
                petsDisplayed++;
            }
        }

        displayFooter(petsDisplayed);

    }

    private static void displayPetsByAge(ArrayList<Pet> pets, int age) {

        displayHeader();

        boolean validAge;
        int petsDisplayed = 0;
        for (int i = 0; i < pets.size(); i++) {
            validAge = pets.get(i).getAge() == age;
            if (validAge) {
                displayRow(pets, i);
                petsDisplayed++;
            }
        }

        displayFooter(petsDisplayed);

    }

    private static void displayRow(ArrayList<Pet> pets, int i) {
        System.out.printf("| %2d | %-9s | %3s |\n", i, pets.get(i).getName(), pets.get(i).getAge());
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

    private static boolean checkInfo(String[] petInfo) {

        String data = String.join(" ", petInfo);
        int age;

        if (petInfo.length != 2) {
            System.out.printf("Could not parse input %s. Make sure to format the input as <name age>.\n", data);
            return false;
        }

        try {
            age = Integer.parseInt(petInfo[1]);
        } catch (NumberFormatException e) {
            System.out.printf("Could not parse input %s. Make sure the age is a whole number.\n", data);
            return false;
        }

        if (age < 1 || age > 20) {
            System.out.printf("Could not parse input %s. Make sure the age is between 1 and 20.\n", data);
            return false;
        }

        return true;

    }

}