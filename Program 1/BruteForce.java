import java.io.BufferedReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.abs;


/*
PriceItem Class to store the Price and Name of the card.
 */
class PriceItem{
    int value;
    String name;

    PriceItem(String name, int value ){
        this.name= name;
        this.value= value;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
class BruteForce {

    public static int  profit = 0;
    public static List<PriceItem> finalSubset = new ArrayList<>();

    public static ArrayList<PriceItem> removedItems = new ArrayList<>();
    public static HashMap<String, Integer> marketPrice = new HashMap<>();


    public static void main(String[] args) throws IOException {

        try{
            int weight;

            //Read and Store the Market Price File
            if (args[0] == null || args[0].trim().isEmpty()) {
                System.out.println("Pass the market price filepath");
                return;
            } else {
                BufferedReader bf = new BufferedReader(new FileReader(args[0]));
                int n = Integer.parseInt(bf.readLine());
                String strLine;
                while ((strLine = bf.readLine()) != null)   {
                    marketPrice.put(strLine.split(" ")[0], Integer.parseInt(strLine.split(" ")[1]));
                }
                bf.close();
            }
            // Read and store the Sample Price File :  Gertude's file
            if (args[1] == null || args[1].trim().isEmpty()) {
                System.out.println("Pass the Sample Price filepath");
                return;
            } else {
                BufferedReader bf = new BufferedReader(new FileReader(args[1]));
                FileWriter output = new FileWriter("output.txt");

                String line;
                while ((line = bf.readLine()) != null) {
                    long startTime = System.nanoTime();
                    int arraySize = Integer.parseInt(line.split(" ")[0]);
                    weight = Integer.parseInt(line.split(" ")[1]);
                    //Read the Sample price into Array
                    ArrayList<PriceItem>  samplePrice = new ArrayList<>();
                    for (int i = 0; i < arraySize; i++) {
                        line = bf.readLine();
                        PriceItem temp = new PriceItem(line.split(" ")[0], Integer.parseInt(line.split(" ")[1]));
                        samplePrice.add(temp);
                    }
                    if(getIntersection(marketPrice,samplePrice)){
                        System.out.print("Case Rejected : Market Price not found for Cards in Gertude's List ,ie cards : " );
                        for (PriceItem item: samplePrice) {
                            System.out.print(item.getName()+ ",");
                        }
                    }
                    else {
                        profit = 0;
                        //get the max profit from subsets.
                        getSubsets(samplePrice, weight);
                        long endTime   = System.nanoTime();
                        long totalTime = endTime - startTime;

                        //write to file -output
                        output.write(samplePrice.size()+" "+profit+" "+ finalSubset.size()+" "+(double) totalTime / 1_000_000_000+ "\n");
                        for (PriceItem item: finalSubset) {
                            output.write(item.getName()+"\n");
                        }
                    }
                }
                bf.close();
                output.close();

            }}
        catch(Exception exception)
        {
            System.out.println("Pass the market price and Gertrude's price list as argument");
        }

    }

    public static boolean getIntersection(Map marketPrice, ArrayList<PriceItem> sampleList)
    {
        Set<String> keysInSampleList = new HashSet<String>();
        for (PriceItem item: sampleList){
            keysInSampleList.add(item.getName());
        }
        Set<String> keysInMarketList = marketPrice.keySet();
        keysInSampleList.retainAll(keysInMarketList);
        if (abs(sampleList.size()-keysInSampleList.size()) == 0  ) return false;
        return  true;
    }
    public static void getSubsets(ArrayList<PriceItem> array, int weight) {
        int size = array.size();
        int limit = (1 << size) - 1;
        for (int i = 1; i <= limit; i++) {
            int index = size - 1;
            int start = i;
            List<PriceItem> subsetTobeChecked = new ArrayList<>();
            while (start > 0) {
                if ((start & 1) == 1) {
                    subsetTobeChecked.add(array.get(index));
                }
                index--;
                start >>= 1;
            }

            int getBuyingPrice = sumOfValuesInSubsetArray(subsetTobeChecked);
            int currentProfit = sumofProfitInSubsetArray(subsetTobeChecked);

            if (currentProfit > profit &&  getBuyingPrice<= weight){
                profit= currentProfit;
                finalSubset= new ArrayList<> (subsetTobeChecked);
            }
        }

    }

    private static int sumofProfitInSubsetArray(List<PriceItem> items) {
        int sum = 0;
        for (PriceItem item : items) {
            int marketValue= marketPrice.get(item.getName());
            int perCardProfit = marketValue - item.getValue();
            sum += perCardProfit;
        }
        return sum;
    }
    private  static int sumOfValuesInSubsetArray (List<PriceItem> items){
        int sum = 0;
        for (PriceItem item : items) {
            sum += item.getValue();
        }
        return sum;
    }
}
