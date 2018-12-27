import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main implements DS1Interface  {


    /* Implement these methods */

    @Override
    public int[] insertionSort(int[] input) {
    	 int n = input.length;			// initializing takes a constant factor 
         for (int j = 1; j < n; j++) {  // traverse j to end takes n times
             int key = input[j];		// key shifts to j, takes n-1 time (nested in for loop)
             int i = j-1;				// i is on the left side of key, to check takes n-1 time (nested in for loop)
             while ( (i > -1) && ( input [i] > key ) ) { //while i is in the array and bigger than key, switch i with i+1. 
                 input [i+1] = input [i];				// depends on worse or best case scenario, best case 1-1, worse case n-1
                 i--;					// decrease i-- back before key
             }
             input[i+1] = key;			// puts key in front of i (n-1) nested in for loop
    }
		return input;					
    }
 
    //------------------------------------------------------------------------------------------- 

    @Override
    public int[] mergeSort(int[] input) {
    	int r = input.length;
    	// if size is 1 element, than return input |takes constant time
        if (r <= 1) {	
            return input;			
        }
       
        //Split the array in half in two parts
        int q = r / 2;	
        int leftSize = q;			
        int rightSize = r - q;
        int[] left = new int[leftSize];
        int[] right = new int[rightSize];
        
        //Do this as long as long as i is smaller than mid, left becomes input i
        for (int i = 0; i < q; i++) {	
            left[i] = input[i];
        }
        
      //Do this as long as long as mid is smaller than arraylength, right becomes input i
        for (int i = q; i < r; i++) {		
            right[i - q] = input[i];
        }
       
        //Sort each half recursively
        mergeSort(left);					
        mergeSort(right);					
       
        // merge subarrays together
        merge(left, right, input);			
        
        return input;
    }
 
   
    public static void merge(int[] left, int[] right, int[] arr) {
        int leftSize = left.length;
        int rightSize = right.length;
        int i = 0, j = 0, k = 0; 		//Index Position in first, second and third array - starting with first element
        
        while (i < leftSize && j < rightSize) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
                k++;
            } else {
                arr[k] = right[j];
                k++;
                j++;
            }
        }
        while (i < leftSize) {
            arr[k] = left[i];
            k++;
            i++;
        }
        while (j < leftSize) {
            arr[k] = right[j];
            k++;
            j++;
        }
    }
 //-------------------------------------------------------------------------------------------   
    
    @Override
    public int[] heapSort(int[] input) {
    	buildheap(input);
    	int size = input.length;
    	for (int i = size - 1; i >= 1; i--) {	// Traverses every node from the highest element to lowest element| n times
			int temp = input[0];						// n-1
			input[0] = input[i]; 						// a[0] turns into the largest value |n-1
			input[i] = temp;		
			heapify(input, i, 0); 						// restore the heap property by 
		}
   
    		return input;
    }
    private void buildheap(int[] input) {
		for (int i = input.length / 2 - 1; i >= 0; i--) //for length n
			heapify(input, input.length, i); //n-1
	}
    static void heapify(int input[], int size, int i) {
		int largest, child; // declare the largest, and the child left(child right is child +1)
		child = 2 * i + 1;	// determines the position of the child according to i
		largest = i;		// largest becomes i
		if (child < size)		//if left child is smaller than array length
			if (input[child] > input[largest]) //if the left child element is bigger than largest element
				largest = child;				// largest becomes left child
		if (child + 1 < size)						//if right child is smaller than array length
			if (input[child + 1] > input[largest])	// if the right child element is bigger than largest element
				largest = child + 1;				// largest becomes right child
		if (largest != i) {							// if largest is not i
			int temp = input[i];
			input[i] = input[largest]; // exchange input[i] with the largest
			input[largest] = temp;
			heapify(input, size, largest); // Make sure it maintains the heap property, by calling the function heapify
		}
	}	



	/* BEGIN UTIL FUNCTION. DO NOT TOUCH */

    int[] readInput(String file) throws FileNotFoundException {

        InputStream inputStream;
        if (file == null) {
            inputStream = System.in;
        } else {
            inputStream = new FileInputStream(file);
        }
        Scanner in = new Scanner(inputStream);

        List<Integer> list = new ArrayList<Integer>();
        while (in.hasNext()) {
            int e = in.nextInt();
            list.add(e);
        }
        return list.stream().mapToInt(i -> i).toArray();
    }

    void start(String algorithm, String file) throws FileNotFoundException {
        int[] toBeSorted = readInput(file);
        int[] sortedResult = null;
        switch (algorithm) {
            case "insertion":
                sortedResult = insertionSort(toBeSorted);
                break;
            case "merge":
                sortedResult = mergeSort(toBeSorted);
                break;
            case "heap":
                sortedResult = heapSort(toBeSorted);
                break;
            default:
                System.out.printf("Invalid algorithm provided: %s\n", algorithm);
                printHelp(null);
                System.exit(1);
        }

        printArray(sortedResult);
    }

    static void printArray(int[] array) {
        for (int e: array) {
            System.out.printf("%d ", e);
        }
    }

    static void printHelp(String[] argv) {
        System.out.printf("Usage: java -jar DS1.jar <algorithm> [<input_file>]\n");
        System.out.printf("\t<algorithm>: insertion, merge, heap\n");
        System.out.printf("E.g.: java -jar DS1.jar insertion example.txt\n");
    }

    public static void main(String argv[]) {
        if (argv.length == 0) {
            printHelp(argv);
        }
        try {
            (new Main()).start(argv[0], argv.length < 2 ? null : argv[1]);
        } catch (FileNotFoundException e) {
            System.out.printf("File not found: %s", e.getMessage());
        }

    }

}
