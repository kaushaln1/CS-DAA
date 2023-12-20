import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FileOperations {

    static int capacity;
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
     * Read Item-list with final selected Items
     * calc weight and profit and call to writeToFile
     * @param finalItemList
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
     * Get String builder content and print it to entries file
     * @param content
     */
    public static void writeEntries(StringBuilder content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("entries3.txt"))) {
            writer.write(content.toString());
            System.out.println("Entries written in 'entries3.txt'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
