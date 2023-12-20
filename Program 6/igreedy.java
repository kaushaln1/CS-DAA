import java.io.*;
import java.util.*;

/**
 * Class for item unit
 * has profit ,weight, name as attributes
 */
class unit {
    int profit;
    int weight;
    String name;

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
 * Class for greedy knapsack functions
 */
public class igreedy {
    static int capacity ;
    static int totalProfit = 0;

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
            throw new Exception("File Not Found");
        }
        return items;
    }

    /**
     * Print results to file
     * @param finalItemList : list of selected Items
     */
    static void printToFile(List<unit> finalItemList) {
        StringBuilder content = new StringBuilder();
        int totalProfit = 0;
        int totalWeight = 0;

        for (unit item : finalItemList) {
            content.append(item.name).append(" ").append(item.profit).append(" ").append(item.weight).append("\n");
            totalProfit += item.profit;
            totalWeight += item.weight;
        }
        writeItems(totalProfit, totalWeight, finalItemList.size(), content);
    }
    /**
     *Write results to file
     * @param totalProfit : total profit from knapsack problem
     *@param totalWeight : total Weight of knapsack
     *@param size : number of selected items
     * @param content : String with data of selected items (weight, profit , name)
     */
    public static void writeItems(int totalProfit, int totalWeight, int size, StringBuilder content){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output3.txt"))) {
            writer.write(size + " ");
            writer.write( totalProfit + " ");
            writer.write( totalWeight + "\n");
            writer.write(content.toString());
            System.out.println("output in 'output3.txt'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Find max profit item from list
     * @param items : List of items
     * @return
     */
    public static int maxProfitElement(List<unit> items, int capacity) {
        int maxProfit = Integer.MIN_VALUE;

        for (unit item : items) {
            if (item.weight <= capacity){
                maxProfit = Math.max(maxProfit, item.getProfit());
            }
        }
        return maxProfit;
    }

    /**
     * Optimized greedy knappsack
     * @param items : List of available items
     * @param capacity :  size of knappsack
     * @return : list of selected items
     */
    public static List<unit> optimizedGreedyKnapsack(List<unit> items, int capacity) {

        int maxBenefit = maxProfitElement(items,capacity);                           // maxbenefit
        List<unit> greedyResult = greedyKnapsack(items, capacity);      // greedy4
        int app = Math.max(totalProfit, maxBenefit);
        if (app == maxBenefit) {
//            System.out.println("selecting Max element ");
            return maxProfitElementArray(items,maxBenefit);
        } else {
//            System.out.println("selecting Greedy Result ");
            return greedyResult;
        }
    }


    static List<unit> maxProfitElementArray(List<unit> items, int maxBenefit){
        Optional<unit> maxBenefitItem = items.stream()
                .filter(item -> item.profit == maxBenefit)
                .max(Comparator.comparingInt(item -> item.weight));

        List<unit> maxElementArray = new ArrayList<>();

        if (maxBenefitItem.isPresent() && maxBenefitItem.get().weight <= capacity) {
            maxElementArray.add(maxBenefitItem.get());
        }
        return maxElementArray;
    }
    public static List<unit> greedyKnapsack(List<unit> items, int capacity) {

        Collections.sort(items, (Comparator) (item1, item2) -> {

            int p1 = ((unit) item1).getProfit();
            int p2 = ((unit) item2).getProfit();
            int w1 = ((unit) item1).getWeight();
            int w2 = ((unit) item2).getWeight();
            double ratio1 = (double) p1/w1;
            double ratio2 = (double) p2/w2;

            return Double.compare(ratio2,ratio1) ;
        });
        List<unit> selectedItems = new ArrayList<>();
        for (unit item : items) {
            if (item.weight <= capacity) {
                    totalProfit += item.profit;
                    capacity -= item.weight;
                    selectedItems.add(item);
            }
        }

        return selectedItems;
    }

    /**
     * Driver Function
     * @param args: accepts a filename as argument from cmd line
     * @throws Exception
     */
    public static void main(String[] args)  {
        try{
            if(args.length == 0 ) throw new Exception("Provide Filepath");
            if(args.length > 1)  throw new Exception("Provided multiple Filepath");

            List<unit> items = getItems(args[0]);

            List<unit> finalItemList = optimizedGreedyKnapsack(items,capacity);

            printToFile(finalItemList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
