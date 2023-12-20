import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Class having functions for matrix multiplication using
 * 1. Strassens method
 * 2. Basic method
 */
public class nerkar_k_pa2_strassen {

    /**
     * Create a Matrix as array list
     * @param size : size of square matrix
     * @param max_limit : max_value for a element
     * @return ArrayList of arrays (having matrix values)
     */
    public static List<List<Integer>> createList(int size, int max_limit) {
        List<List<Integer>> A_temp = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Integer> temp = new ArrayList<Integer>();
            for (int j = 0; j < size; j++) {
                Random r = new Random();
                int randomInt = r.nextInt(max_limit) + 1;
                temp.add(randomInt);
            }
            A_temp.add(temp);
        }
        return A_temp;
    }

    /**
     * Classic Method to multiply 2 matrix
     * @param A Matrix A
     * @param B Matrix B
     * @param size size of square matrix
     * @return a square matrix with A*B
     */
    public static List<List<Integer>> Matrix_multiply(List<List<Integer>> A, List<List<Integer>> B, int size) {
        int i, j, k;
        List<List<Integer>> result = new ArrayList<>(size);
        for (int l = 0; l < size; l++) {
            result.add(new ArrayList<>(Collections.nCopies(size, 0)));
        }
        for (i = 0; i < size; i++) {
            for (j = 0; j < size; j++) {
                for (k = 0; k < size; k++) {
                    int temp_val = A.get(i).get(k) * B.get(k).get(j);
                    result.get(i).set(j, result.get(i).get(j) + temp_val);
                }
            }
        }
        return result;
    }

    /**
     * Using strassens method to find the Matrix multiplication
     * @param a Matrix a
     * @param b  Matrix b
     * @return  multiplication of 2 Matrix a*b
     */
    public static List<List<Integer>> strassens_mul (List<List<Integer>>a , List<List<Integer>> b){
        //Init result array
        int size = a.size();

        List<List<Integer>> result = create_Empty_Mat(size);

        if (size == 1){
            result.get(0).set(0, a.get(0).get(0) * b.get(0).get(0));
        }
        else {
            //create partitions of A, B, result
            Object[] partitions_a = getpartition(size, a);
            List<List<Integer>> a11 = (List<List<Integer>>) partitions_a[0];
            List<List<Integer>> a12 = (List<List<Integer>>) partitions_a[1];
            List<List<Integer>> a21 = (List<List<Integer>>) partitions_a[2];
            List<List<Integer>> a22 = (List<List<Integer>>) partitions_a[3];


            //for b
            Object[] partitions_b = getpartition(size, b);
            List<List<Integer>> b11 = (List<List<Integer>>) partitions_b[0];
            List<List<Integer>> b12 = (List<List<Integer>>) partitions_b[1];
            List<List<Integer>> b21 = (List<List<Integer>>) partitions_b[2];
            List<List<Integer>> b22 = (List<List<Integer>>) partitions_b[3];

            List<List<Integer>> res1 = add(a11,a22);
            List<List<Integer>> res2 = add(b11,b22);
            List<List<Integer>> p1 = strassens_mul(res1, res2);
            res1 =  add(a21,a22);
            List<List<Integer>> p2 = strassens_mul(res1, b11);
            res1 = sub(b12,b22);
            List<List<Integer>> p3 = strassens_mul(a11, res1);
            res1 = sub(b21,b11);
            List<List<Integer>> p4 = strassens_mul(a22, res1);
            res1 = add(a11,a12);
            List<List<Integer>> p5 = strassens_mul(res1, b22);
            res1 = sub(a21,a11);
            res2 = add(b11,b12);
            List<List<Integer>> p6 = strassens_mul(res1,res2);
            res1 = sub(a12,a22);
            res2 = add(b21,b22);
            List<List<Integer>> p7 = strassens_mul(res1, res2);

            List<List<Integer>> c11 = sub(add(add(p1, p4), p7), p5);
            List<List<Integer>> c12 = add(p3, p5);
            List<List<Integer>> c21 = add(p2, p4);
            List<List<Integer>> c22 = add(add(sub(p1, p2),p3), p6);

            result = join(c11,c12,c21,c22,size);

        }

        return  result;

    }

    /**
     * Create a Empty Arraylist
     * @param size = size of sqaure matrix
     * @return a empty arraylist of given size
     */
    public static List<List<Integer>> create_Empty_Mat (int size){
        List<List<Integer>> arrayList = new ArrayList<>(size);
        for (int l = 0; l < size; l++) {
            arrayList.add(new ArrayList<>(Collections.nCopies(size, 0)));
        }
        return  arrayList;
    }

    /**
     * Add matrix
     * @param a Matrix a
     * @param b Matrix b
     * @return Array list with sum of 2 given Matrix
       */
    public static List<List<Integer>> add(List<List<Integer>> a, List<List<Integer>> b){
        int size = a.size();
        List<List<Integer>> c = create_Empty_Mat(size);
        for (int i =0; i<size;i++){
            for (int j=0;j<size;j++){
                int val1 = a.get(i).get(j);
                int val2 = b.get(i).get(j);
                c.get(i).set(j,val1+val2);
            }
        }
        return c;
    }

    /**
     * Subtract Matrix
     * @param a Matrix a
     * @param b Matrix b
     * @return  Array list with difference of 2 given Matrix
     */
    public static List<List<Integer>> sub(List<List<Integer>> a, List<List<Integer>> b){
        int size = a.size();
        List<List<Integer>> c = create_Empty_Mat(size);
        for (int i =0; i<size;i++){
            for (int j=0;j<size;j++){
                int val1 = a.get(i).get(j);
                int val2 = b.get(i).get(j);
                c.get(i).set(j,val1-val2);
            }
        }
        return c;
    }

    /**
     *
     * @param a  Matrix a : size n/4
     * @param b  Matrix b : size n/4
     * @param c  Matrix c : size n/4
     * @param d  Matrix d : size n/4
     * @param size = size of final array
     * @return joined array with size n*n
     */
    public static List<List<Integer>> join(List<List<Integer>> a, List<List<Integer>> b,List<List<Integer>> c, List<List<Integer>> d , int size){

        List<List<Integer>> joined_array = create_Empty_Mat(size);
        int n = a.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                joined_array.get(i).set(j, a.get(i).get(j));
                joined_array.get(i).set(j + n, b.get(i).get(j));
                joined_array.get(i + n).set(j, c.get(i).get(j));
                joined_array.get(i + n).set(j + n, d.get(i).get(j));
            }
        }
        return joined_array;
    }

    /**
     * Print a matrix
     * @param mat : Matrix A
     * @param size n
     */
    public static void print_matrix(List<List<Integer>> mat, int size ){
        for( int i =0 ; i<size; i++){
            System.out.println(mat.get(i));
        }
    }

    /**
     * get partition as object list
     * @param n : size of matrix
     * @param array : Matrix arrayList
     * @return : ObjectList with 4 arrays
     */
    public static Object[] getpartition(int n ,List<List<Integer>> array ){
        int parSize = n/2;

        List<List<Integer>> a11= create_Empty_Mat(parSize);
        List<List<Integer>> a12= create_Empty_Mat(parSize);
        List<List<Integer>> a21= create_Empty_Mat(parSize);
        List<List<Integer>> a22= create_Empty_Mat(parSize);

        for (int i = 0; i < parSize; i++) {
            for (int j = 0; j < parSize; j++) {
                int v1 = array.get(i).get(j);
                int v2 = array.get(i).get(j+parSize);
                int v3 = array.get(i+parSize).get(j);
                int v4 = array.get(i+parSize).get(j+parSize);
                a11.get(i).set(j,v1);
                a12.get(i).set(j,v2);
                a21.get(i).set(j,v3);
                a22.get(i).set(j,v4);
            }
        }

        return  new Object[]{a11, a12,a21,a22};

    }

    /**
     * Main method
     * @param args args[0]= size of matrix as power of 2.
     */
    public static void main(String[] args) {

        if (args.length >0){
            int n = Integer.parseInt(args[0]);
            //to check power of 2
            if ( n!=0 && ( n & (n-1))==0)
            {
                int maxIntValue = Integer.MAX_VALUE;
                int max_limit = (int) Math.floor(Math.sqrt(maxIntValue / n));
                //int max_limit = 5;  Test code

                List<List<Integer>> A = createList(n, max_limit);
                List<List<Integer>> B = createList(n, max_limit);
                System.out.println("A =");
                print_matrix(A, n);
                System.out.println("B =");
                print_matrix(B, n);
                System.out.println("The standard matrix multiplication A*B =");
                List<List<Integer>> std_result = Matrix_multiply(A, B, n);
                print_matrix(std_result, n);

                System.out.println("The Strassen’s multiplication A*B =");
                List<List<Integer>> strassen_result = strassens_mul(A, B);
                print_matrix(strassen_result, n);
            }
            else{
                System.out.println("Provided input for matrix size is not a power of 2");
            }
        }
        else {
            System.out.println("Provide input for matrix size");
        }


    }
}
