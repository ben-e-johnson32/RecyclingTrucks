package com.Ben;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    static Scanner numScanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        // Initialize an array list for integers.
        ArrayList<Integer> containers = new ArrayList<>();

        // Ask the user how many containers for each house.
        for (int x = 0; x < 8; x++)
        {
            System.out.print("How many containers for house " + x + "?: ");
            int num = numScanner.nextInt();
            containers.add(num);
        }

        // Use Collections to get the max value in the list.
        int maxContainer = Collections.max(containers);
        // Initialize new list to store house numbers that have the highest number of containers.
        ArrayList<Integer> maxIndexes = new ArrayList<>();

        // For each loop that adds the house number of each house with the max number of containers
        // to the maxIndexes list.
        int i = 0;
        for (int num : containers)
        {
            if (num == maxContainer)
            { maxIndexes.add(i); }

            i++;
        }

        // Just turn the list of indexes into a string.
        String output = maxIndexes.toString();

        // Display results.
        System.out.println("Houses " + output + " have the highest number of containers, with " + maxContainer + ".");
        // Write the info to a report file.
        WriteFile(containers, maxContainer, maxIndexes);
        System.out.println("Report file saved.");

        numScanner.close();
    }

    public static void WriteFile(ArrayList<Integer> containers, int maxContainer, ArrayList<Integer> maxIndexes)
    {
        // Try opening the file for writing.
        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter("report.txt")))
        {
            // An integer to keep track of the total number of containers.
            int total = 0;

            // A for loop to write the basic counts to the file.
            for (int x = 0; x < containers.size(); x++)
            {
                // An empty string that becomes an s if there's more than one crate (pluralizing the word).
                String plural = "";
                if (containers.get(x) > 1) { plural += "s"; }
                bufWriter.write("House " + x + " recycled " + containers.get(x) + " crate" + plural + ".\n");
                total += containers.get(x);
            }

            // Write the total.
            bufWriter.write("Total crates recycled: " + total + "\n");

            // A for loop to display the houses that recycled the most.
            bufWriter.write("Houses that recycled the most:\n");
            for (int x = 0; x < maxIndexes.size(); x++)
            {
                bufWriter.write("House " + maxIndexes.get(x) + ": " + maxContainer + " crates.\n");
            }

            bufWriter.close();
        }

        // Display a message if there's an error writing the file.
        catch (IOException ioe)
        {
            System.out.println("Error writing file.");
        }

    }
}
