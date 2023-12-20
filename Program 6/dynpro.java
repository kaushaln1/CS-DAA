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

    /**
     * constructor with following parameters
     */
    unit(String name, int profit, int weight) {
        this.name = name;
        this.profit = profit;
        this.weight = weight;
    }

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
 * Class with functions like getItems , knapsackdpSolution, writeItems
 */
public class dynpro {
    static int capacity;
    static Set<String> calculatedEntries = new HashSet<>();

    /**
     * function to retieve the unitItem list from file
     * @param fileName : file with items data
     * @return : List of objects (unit)
     * @throws Exception : throws exception
     */
    public static List<unit> getItems(String fileName) throws Exception {
        List<unit> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int itemCount = 0;
            String line = br.readLine();
            StringTokenizer st = new StringTokenizer(line);
            itemCount = Integer.parseInt(st.nextToken());
            capacity = Integer.parseInt(st.nextToken());
            int lineNumber = 0;
            String lineData;
            while ((lineData = br.readLine()) != null && lineNumber < itemCount) {
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
    public static void writeItems(int totalProfit, int totalWeight, int size, StringBuilder content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output2.txt"))) {
            writer.write(size + " ");
            writer.write(totalProfit + " ");
            writer.write(totalWeight + "\n");
            writer.write(content.toString());
            System.out.println("KnapsackResult in 'output2.txt'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper fucntion to wrote content in file
     * @param content : String Content
     */
    public static void writeEntries(StringBuilder content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("entries2.txt"))) {
            writer.write(content.toString());
            System.out.println("Entries written in 'entries2.txt'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Find Entries which are required to be filled in DP table
     * recursive call to find the required Entries
     * @param n : number of items
     * @param capacity : max Weight capacity of knapsack
     * @param items : List of Items
     * @return :n
     */
    static int findEntries(int n, int capacity, List<unit> items){
        if (n == 0 || capacity < 0) {
            return 0;
        }
        if (items.get(n - 1).weight <= capacity) {
            int a1 = findEntries(n - 1, capacity, items);
            int a2 = findEntries(n - 1, capacity - items.get(n - 1).weight, items);
        } else {
            findEntries(n - 1, capacity, items);
        }
        calculatedEntries.add( n +","+ capacity);
        return n;
    }

    /**
     * Get DP table in iteration and update only relevant entries
     * @param n : number of items
     * @param capacity : size of knapsack
     * @param items :  List of items
     * @param calculatedEntries :  Set with relavent Entries
     * @return : optimized Dp table
     */
    static List<List<Integer>> getDpTable(int n, int capacity, List<unit> items , Set<String> calculatedEntries){

        //Initialize the DP table
        List<List<Integer>> dpTable = initializeTable(n,capacity);

        //Iterate over table
        for (int i =0 ;i<=n ;i++){
            for (int j = 0; j <=capacity; j++) {
                String s= i+","+j;
                if (i ==0 || j==0 )  dpTable.get(i).set(j,0);
                if (calculatedEntries.contains(s)){
                    if(items.get(i-1).getWeight() <= j)
                    {
                        int a1 = dpTable.get(i-1).get(j);
                        int a2 = dpTable.get(i-1).get(j- items.get(i-1).getWeight() ) + items.get(i-1).profit;
                        dpTable.get(i).set(j,Math.max(a1,a2));
                    }
                    else{
                        dpTable.get(i).set(j, dpTable.get(i-1).get(j));
                    }
                }
            }
        }
        return dpTable;
    }


    /**
     * get selected items from the memoList (DP table optimized) and store it to set 'selectedItems'
     *
     * @param totalProfit : total profit or memoList[n,capacity]
     * @param capacity    : size of knapsack
     * @param dpTable     : DP table optimized
     * @param items       : List of all available items
     * @return : Set of selected Items
     */
    static Set<unit> getSelectedItems(int totalProfit, int capacity, List<List<Integer>> dpTable, List<unit> items) {
        Set<unit> selectedItems = new HashSet<>();
        int profit = totalProfit;
        int weightAtItr = capacity;

        for (int i = items.size(); i > 0 && profit > 0; i--) {
            if (profit != dpTable.get(i - 1).get(weightAtItr)) {
                selectedItems.add(items.get(i - 1));
                profit -= items.get(i - 1).profit;
                weightAtItr -= items.get(i - 1).weight;
            }
        }
        return selectedItems;
    }

    /**
     * Append a String with required entries from memoList(DP table)
     * @param items : List of items
     * @param capacity : size of knapsack
     * @param dpTable : DP table optimized
     */
    static void printEntries(List<unit> items, int capacity, List<List<Integer>> dpTable, Set<String> calculatedEntries) {
        StringBuilder entries = new StringBuilder();
        for (int i = 1; i <= items.size(); i++) {
            entries.append("row").append(i).append("\t");
            for (int j = 0; j <= capacity; j++) {
                String  s = i+","+j;
                if ((dpTable.get(i).get(j) != -1) && calculatedEntries.contains(s))  {
                    entries.append(dpTable.get(i).get(j)).append(" \t");
                }
            }
            entries.append("\n");
        }
        writeEntries(entries);
    }

    /**
     * Print results to file
     * @param totalProfit : profit from knapsack
     * @param selectedItems : selectedItems from item list
     */
    static void printToFile(int totalProfit, Set<unit> selectedItems) {
        int totalWeight = 0;
        StringBuilder content = new StringBuilder();
        for (unit item : selectedItems) {
            content.append(item.name).append(" ").append(item.profit).append(" ").append(item.weight).append("\n");
            totalWeight += item.weight;
        }
        writeItems(totalProfit, totalWeight, selectedItems.size(), content);
    }

    /**
     * initalize the dp table with -1 , memoList to -1
     * @param n : numeber of items
     * @param capacity : size of knapsack
     */
    static List<List<Integer>> initializeTable(int n, int capacity) {

        List<List<Integer>> dpTable= new ArrayList<>();
        //fill with -1
        for (int i = 0; i < n + 1; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < capacity + 1; j++) {
                row.add(-1);
            }
            dpTable.add(row);
        }
        return dpTable;
    }

    /**
     * Driver Function, accepts argument from cmd
     * @param args : filename from cmdline
     */
    public static void main(String[] args) {
        try{
            if (args.length ==0 ) throw new Exception("Provide Filepath");
            if (args.length >2) throw new Exception("Provided multiple Filepath ");

            int totalProfit = 0;
            //get items from file
            List<unit> items = getItems(args[0]);
            int n = items.size();

            findEntries(n,capacity,items);
            List<List<Integer>> table = getDpTable(n, capacity, items, calculatedEntries);

            //total profit & selected items
            totalProfit = table.get(n).get(capacity);
            Set<unit> selectedItems = getSelectedItems(totalProfit, capacity, table, items);


            //print to file selected items and weight and profit
            printEntries(items, capacity, table,calculatedEntries);
            printToFile(totalProfit, selectedItems);
        }
         catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
