import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Class to  create a file for knapsack problem
 */
public class createkn01 {

    /**
     * fucntion to create a txt file
     * @param args : filename as argument
     */
    public static void createFile(String[] args){

        Random random = new Random();
        int n = random.nextInt(6) + 5;

        int totalWeight = 0;
        StringBuilder content = new StringBuilder();


        for (int i = 0; i < n; i++) {
            int profit = random.nextInt(21) + 10;
            int weight = random.nextInt(16) + 5;
            totalWeight += weight;
            content.append("item"+ (i + 1)).append(" ").append(profit).append(" ").append(weight).append("\n");
        }

        int capacity = (int) Math.floor(0.6 * totalWeight);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[0]))) {
            writer.write(n+" "+capacity+ "\n");
            writer.write(content.toString());
            System.out.println("File has been generated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Driver function
     * @param args : gets filename from cmd line
     */
    public static void main(String[] args){
        if (args.length != 1 ){
            System.out.println("Pass the file name to store the knapsack problem");
        }
        else{
            createFile(args);
        }

    }
}
