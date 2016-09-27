package com.Ben;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    static Scanner numScanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        System.out.print("Would you like to (1) enter data, or (2) read data from the Main Street file? ");
        Integer mode = numScanner.nextInt();

        if (mode == 1)
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
                {
                    maxIndexes.add(i);
                }

                i++;
            }

            // Just turn the list of indexes into a string.
            String output = maxIndexes.toString();

            // Display results.
            System.out.println("Houses " + output + " have the highest number of containers, with " + maxContainer + ".");
            // Write the info to a report file.
            WriteFile(containers, maxContainer, maxIndexes);
            System.out.println("Report file saved.");
        }

        else
        {
            // HashMap from the method below, list of the house numbers that recycled the most, highest num of containers.
            HashMap<Integer, Integer> mainStreetValues = GetInputFromFile();
            ArrayList<Integer> houseNums = new ArrayList<>();
            int maxContainers = 0;

            // Loop through the HashMap to find the highest value.
            for (Integer x = 0; x < mainStreetValues.size(); x++)
            {
                if (mainStreetValues.get(x) > maxContainers)
                {
                    maxContainers = mainStreetValues.get(x);
                }
            }

            // Loop through the HashMap again to find all the houses that recycled the most.
            for (Integer y = 0; y < mainStreetValues.size(); y++)
            {
                if (mainStreetValues.get(y) == maxContainers)
                {
                    houseNums.add(y);
                }
            }

            // Display the result.
            System.out.println("Houses " + houseNums.toString() + " had the most containers with " + maxContainers);
        }

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

    public static HashMap<Integer, Integer> GetInputFromFile()
    {
        // A HashMap for the output, plus an ArrayList for the lines from the file.
        HashMap<Integer, Integer> output = new HashMap<>();
        ArrayList<String> lines = new ArrayList();

        // Open the main street file.
        try (BufferedReader bufReader = new BufferedReader(new FileReader("recyclingreport-mainstreet.txt")))
        {
            // Read the first line, then use a while loop to add each line of the file to the lines arraylist.
            String line = bufReader.readLine();
            while (line != null)
            {
                lines.add(line);
                line = bufReader.readLine();
            }

            // A for each loop to break the lines down and grab just the house number and crates recycled.
            for (String l : lines)
            {
                String[] words = l.split(" ");
                Integer house = Integer.valueOf(words[1]);
                Integer crates = Integer.valueOf(words[3]);
                output.put(house, crates);
            }

            bufReader.close();
            return output;
        }

        catch (IOException ioe)
        {
            System.out.println("Error reading file.");
        }

        // If for some reason we didn't reach the return statement, return an empty hashmap.
        return new HashMap<Integer, Integer>();
    }
}
