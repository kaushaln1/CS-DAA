import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class floyd {
    public static final String problem_string = "problem";


    /**
     * Function to find the shortedPaths across vertices ,
     * this updates the adjacency matrix as well as creates pathMatrix
     * @param graph : adjacency matrix with initial distances b/w vertices
     * @param size : adjacency matrix size
     * @return : pMatrix with path details
     */
    static List<List<Integer>> shortestPath(List<List<Integer>> graph, int size){

        List<List<Integer>> pMatrix = createEmptyMat(size);

            for (int k =0;k<size;k++)
            {
                for (int i=0; i<size;i++){
                    for(int j=0;j<size;j++){
                        int val = graph.get(i).get(k) + graph.get(k).get(j);
                        if( graph.get(i).get(j) > val){
                            graph.get(i).set(j,val);
                            pMatrix.get(i).set(j,k+1);
                            //System.out.println("Setting P "+ k+1 );
                        }
                    }
                }
            }
        //printMatrix(pMatrix, size);
    return pMatrix;
    }

    /**
     * Create Empty matrix assign it with Zeros
     * @param size : size of matrix
     * @return : return Matrix with all zeros
     */
    public static List<List<Integer>> createEmptyMat(int size){
        List<List<Integer>> arrayList = new ArrayList<>(size);
        for (int l = 0; l < size; l++) {
            arrayList.add(new ArrayList<>(Collections.nCopies(size, 0)));
        }
        return  arrayList;
    }

    /**
     * Print the Matrix
     * @param mat : ArrayList of list
     * @param size : size of ArrayList
     */
    public static void printMatrix(List<List<Integer>> mat, int size ){
        for( int i =0 ; i<size; i++){
            for( int j=0;j<size;j++)
            {
                System.out.print(mat.get(i).get(j) + "\t");
            }
            System.out.println();
        }
    }

    /**
     * read File and create a ArrayList of List
     * @param br : readStream
     * @param size : size of matrix , used to find how many lines to read
     * @return : Matrix (Arraylist of list) with given adjacency Matrix from file.
     * @throws IOException
     */
    private static List<List<Integer>> readMatrix(BufferedReader br, int size) throws IOException {
        String line;
        int lineCount = 0;
        List<List<Integer>> graph = new ArrayList<>();
        while ((line = br.readLine()) != null && lineCount < size) {
            String[] rowValues = line.split("\\s+");

            List<Integer> row = new ArrayList<>();
            for (String value : rowValues) {
                row.add(Integer.parseInt(value));
            }
            graph.add(row);
            lineCount++;
        }
    return  graph;
    }

    /**
     * To Print All shortest Paths from a vertex to others along with shortest distances
     * @param pMat : path Matrix containing all shortest paths
     * @param n : size of pMatrix
     * @param graph : Adjacency Matrix containing all optimalDistances
     */
    private static void printShortestPaths(List<List<Integer>> pMat, int n, List<List<Integer>> graph) {
        for (int i=0;i<n;i++){
            int i_int =  i+1;
            System.out.println("V"+i_int+"-"+ "Vj:shortest path and length");
            for (int j=0;j<n;j++){

                    int j_int = j+1;
                    System.out.print("V"+ i_int + " ");
                    printOptimal(pMat, i, j);
                    System.out.print("V"+ j_int + " ");
                    System.out.println(": " +graph.get(i).get(j) );
            }
            System.out.println();
        }
    }

    /**
     * Recursive Function to traverse between intermediate vertices in Path Matrix
     * @param pMat : path Matrix
     * @param i :  start vertex
     * @param j : end vertex
     */
    static void printOptimal(List<List<Integer>> pMat, int i, int j){

        if (pMat.get(i).get(j) != 0){
            printOptimal(pMat,i, pMat.get(i).get(j)-1);
            System.out.print("V"+ pMat.get(i).get(j) + " ");
            printOptimal(pMat,pMat.get(i).get(j)-1,j);
        }
    }

    /**
     * Driver function to read the file and handle exceptions
     * @param args : file path for problems
     */
    public static void main(String[] args) {

        try{
            /**
             *  Read file to get graph data
             */
            if(args.length == 0 ) throw  new Exception("Pass the input filepath for floyd");
            if (args[0] == null || args[0].trim().isEmpty()) {
                throw new Exception("Pass the input filepath for floyd");
            } else {

                BufferedReader bf = new BufferedReader(new FileReader(args[0]));
                BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));
                /**
                 * Redirect PrintStream to buffered write
                 * post this every print Statement will write to file
                 */
                PrintStream originalOut = System.out;
                System.setOut(new PrintStream(new OutputStream() {
                    @Override
                    public void write(int b) throws IOException {
                        bw.write(b);
                    }
                }));

                String line ;
                while ((line= bf.readLine())!= null ){
                    String pLine = line;
                    if(line.toLowerCase().startsWith(problem_string)){
                        String [] parts = line.split(":");
                        //System.out.println(parts[0] + parts[1]);
                        if(parts.length == 2 ){
                            String nPart = parts[1].trim();
                            int nValue = Integer.parseInt(nPart.substring("n =".length()).trim());
                            List<List<Integer>> graphMatrix = readMatrix(bf, nValue);
                            List<List<Integer>> pMatrix = shortestPath(graphMatrix, nValue);
                            System.out.println(pLine+ "\nPmatrix:");
                            printMatrix(pMatrix,nValue);
                            printShortestPaths(pMatrix,nValue,graphMatrix);
                        }
                        else {
                            throw new Exception("Problem is not passed correctly");
                        }

                    }
                }
                /**
                 * Close file Streams
                 */
                bf.close();
                bw.close();
                /**
                 * Restore PrintStream to Console
                 */
                System.setOut(originalOut);
            }

    } catch (Exception e) {
            System.out.println("Error : "+ e.getMessage());
        }
    }

}