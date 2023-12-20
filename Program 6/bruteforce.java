import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Class for item unit
 * has profit ,weight, name as attributes
 */
class unit {
    int profit;
    int weight;
    String name;

    /**
     * constructor with following parameters
     * @param name :
     * @param profit :
     * @param weight :
     */
    unit(String name, int profit, int weight ){
        this.name= name;
        this.profit = profit;
        this.weight = weight;
    }

    //getters
    public int getProfit() {
        return profit;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }
}


/**
 * Class for bruteforce knapsack functions
 */
public class bruteforce {

    static int capacity ;

    /**
     * function to retieve the unitItem list from file
     * @param fileName : file with items data
     * @return : List of objects (unit)
     * @throws Exception
     */
    public static List<unit> getItems(String fileName) throws Exception {
        List<unit> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int itemCount = 0;
            String line  = br.readLine();
            StringTokenizer st = new StringTokenizer(line);
            itemCount = Integer.parseInt(st.nextToken());
            capacity = Integer.parseInt(st.nextToken());
            int lineNumber = 0;
            String lineData;
            while ((lineData = br.readLine()) != null && lineNumber< itemCount)
            {
                    StringTokenizer stt = new StringTokenizer(lineData);
                    String itemName = stt.nextToken();
                    int profit = Integer.parseInt(stt.nextToken());
                    int weight = Integer.parseInt(stt.nextToken());
                    items.add(new unit(itemName, profit, weight));
                    lineNumber++;
                }

        } catch (IOException e) {
           throw new Exception("File not found");
        }
    return items;
    }

    /**
     * Write results to file
     * @param totalProfit : total profit from knapsack problem
     * @param totalWeight : total Weight of knapsack
     * @param size : number of selected items
     * @param content : String with data of selected items (weight, profit , name)
     */
    public static void writeItems(int totalProfit, int totalWeight, int size, StringBuilder content){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output1.txt"))) {
            writer.write(size + " ");
            writer.write( totalProfit + " ");
            writer.write( totalWeight + "\n");
            writer.write(content.toString());
            System.out.println("output in 'output1.txt'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to create a bruteforce Algorithm for knapsack selection with weight as constraint
     * @param items : List of all available items
     * @param capacity : Max capacity of knapsack
     * @return : List of selected Items
     */
    private static List<unit> knapsackbruteforce(List<unit> items, int capacity) {
        int profit = 0;
        int size = items.size();
        int limit = (1 << size) - 1;
        ArrayList<unit> finalSubset = new ArrayList<>();
        for (int i = 1; i <= limit; i++) {
            int index = size - 1;
            int start = i;
            List<unit> subsetTobeChecked = new ArrayList<>();
            int currentWeight = 0;
            int currentProfit = 0;
            while (start > 0) {
                if ((start & 1) == 1) {
                    subsetTobeChecked.add(items.get(index));
                    currentWeight += items.get(index).weight;
                    currentProfit += items.get(index).profit;
                }
                index--;
                start >>= 1;
            }
            //tempPrint(subsetTobeChecked,currentProfit,currentWeight);
            if (currentProfit > profit && currentWeight<= capacity){
                profit= currentProfit;
                finalSubset = new ArrayList<>(subsetTobeChecked);
            }
        }
    return finalSubset;
    }

    /**
     * Print the selected Items
     * @param items
     * @param profit
     * @param weight
     */
    public static void tempPrint(List<unit> items, int profit, int weight ){
        System.out.println("Profit + Weight " + profit + " " + weight);
        for(int i=0;i< items.size();i++){
            System.out.println(items.get(i).name + " "+ items.get(i).profit + " "+ items.get(i).weight);
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    /**
     * Print results to file
     * @param finalItemList : list of selected Items
     */
    static void printToFile(List<unit> finalItemList){
        StringBuilder content = new StringBuilder();
        int totalProfit = 0;
        int totalWeight = 0;

        for (unit item : finalItemList) {
            content.append(item.name).append(" ").append(item.profit).append(" ").append(item.weight).append("\n");
            totalProfit += item.profit;
            totalWeight += item.weight;
        }
        writeItems(totalProfit,totalWeight,finalItemList.size(),content);
    }

    /**
     * Driver function
     * @param args: accepts a filename as argument from cmd line
     */
    public static void main(String[] args){
        try {
            if(args.length == 0 ) throw new Exception("Provide Filepath");
            if(args.length > 1)  throw new Exception("Provided multiple Filepath");
            List<unit> items = getItems(args[0]);
            List<unit> finalItemList = knapsackbruteforce(items,capacity);
            printToFile(finalItemList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
