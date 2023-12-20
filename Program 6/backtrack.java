import java.text.DecimalFormat;
import java.util.*;

/**
 *Class for item unit
 *  has profit ,weight, name as attributes
 *  also implements Comparable to override CompareTo funciton for sorting based on ratio of profit and weight
 */
class unit implements Comparable<unit>{

    int weight;
    int profit;

    String name;

    //Constructor
    public unit(String name, int profit, int weight) {
        this.weight = weight;
        this.name = name;
        this.profit = profit;
    }

    //getters
    public int getWeight() {
        return weight;
    }

    public int getProfit() {
        return profit;
    }

    //override compareTo
    @Override
    public int compareTo(unit other) {
        double thisRatio = (double) this.getProfit()/this.getWeight();
        double otherRatio = (double) other.getProfit()/other.getWeight();

        if (thisRatio > otherRatio) {
            return -1;
        } else if (thisRatio < otherRatio) {
            return 1;
        } else {
            return 0;
        }
    }

    //to String to print the unit names of Items
    @Override
    public String toString() {
        return "unit{" +
                "name='" + name + '\'' +
                '}';
    }
}

/**
 * Class with Functions for backtracking logic
 *
 */
public class backtrack {

    //Class variables
    static int maxProfit = 0;
    static int num = 0;
    static int capacity ;
    static int counter= 0;
    static List<unit> included = new ArrayList<unit>();
    static List<unit> best = new ArrayList<unit>();

    static StringBuilder entries = new StringBuilder();


    /**
     * Function to initiate the backtracking process
     * @param items : List of Items
     * @return : max profit that can be gained
     */
    static int backTracking(List<unit> items){
        maxProfit = 0;
        num = 0;
        knapsackBackTrack(items, 0, 0, 0);
        return maxProfit;
    }


    /**
     * Fractional KnapSack implementation using greedy4 algo
     * This will return bound
     * @param items : list of items
     * @param index : index of item at consideration
     * @param weightV : item weight
     * @param profitV : item profit
     * @param capacity : capacity of knapsack
     * @return : bound using fractional knapsack (greedy)
     */
    static double kwf2(List<unit> items, int index, int weightV, int profitV ,int capacity) {

        double bound = profitV;
        int n = items.size()+1;

        while( (weightV < capacity) && (index <= n-1 )) {
            int remainingWeight = weightV + items.get(index-1).getWeight();
            if( remainingWeight<= capacity) {
                weightV += items.get(index-1).getWeight();
                bound += items.get(index-1).getProfit();
            } else {
                double fraction = (double)(capacity - weightV) / items.get(index-1).getWeight();
                weightV = capacity;
                bound += items.get(index-1).getProfit() * fraction;
           }
            index++;
        }
        return bound;
    }

    /**
     * Recurssive function to traverse the state space tree.
     * @param items : list of items
     * @param i: index (item under consideration )
     * @param profit : profit till now
     * @param weight : wright till now
     */
    static void knapsackBackTrack(List<unit> items, int i, int profit, int weight) {
        if(weight <= capacity && profit > maxProfit) {
            maxProfit = profit;
            num = i;
            best = new ArrayList<>(included);
        }
        if(promising(items, weight, profit, i)) {
            included.add(items.get(i));
            knapsackBackTrack(items, i+1, profit + items.get(i).getProfit(), weight + items.get(i).getWeight());
            included.remove(items.get(i));
            knapsackBackTrack(items, i+1, profit, weight);
        }
    }

    /**
     * Determine if the node is promising or not and update the entries String-builder with bound ,profit ,weight in every iteration
     * @param items : List of items
     * @param weight : weight of item under consideration
     * @param profit :  profit of item under consideration
     * @param i : index
     * @return : yes or no
     */
    static boolean promising(List<unit> items, int weight, int profit, int i ) {
        if(weight >= capacity) {
            entries.append(++counter).append("\t").append(profit).append("\t").append(weight).append("\t0\n");
            return false;
        }

        double bound = kwf2(items,i+1, weight, profit,capacity);

        DecimalFormat df = new DecimalFormat("#0.000000");
        String boundString = df.format( bound);
        entries.append(++counter).append("\t").append(profit).append("\t").append(weight).append("\t").append(boundString).append("\n");
        return (bound > maxProfit);
    }

    /**
     * Driver fucntion to call FileOperations and
     * BackTracking algorithm
     * @param args
     */
    public static void main(String[] args) {
        try {
            if (args.length == 0) throw new Exception("Provide Filepath");
            if (args.length > 1) throw new Exception("Provided multiple Filepath");
            List<unit> items = FileOperations.getItems(args[0]);            // items from file
            Collections.sort(items);                               //sorting items by ratio (pi/wi)
            capacity = FileOperations.capacity;           //capacity of knapsack from file

            backTracking(items);                   // backtracking Algo call
            FileOperations.writeEntries(entries);  //entries file
            FileOperations.printToFile(best);      //output file
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

}