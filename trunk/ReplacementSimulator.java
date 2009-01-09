import java.util.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class ReplacementSimulator {
    
	public static final int UNIFORM_DIST = 0, GAUSSIAN_DIST = 1;
	public static final int FIFO = 0, LRU = 1;
	
	private int pageSize = 1; // 1KB

	// {{{ ReplacementSimulator constructor
    /**
     * 
     */
    public ReplacementSimulator() {
        int[] refString1 = generateReferenceString(1000,1000,UNIFORM_DIST);
		/*int sum=0;
				for(int i=0; i<refString1.length ;i++){
					System.out.println(refString1[i]);
					sum += refString1[i];
				}
				System.out.println("AVERAGE: " + sum/refString1.length);*/
		
		// RefString, pageSize, Memory Size, Policy
		int pageFaults = calculatePageFaults(refString1, 1, 256, LRU); // FIFO, 16KB
		System.out.println("Num Of Faults: " + pageFaults);
		
		
    }
	// }}}
	
	
	/**
	 * generateReferenceString
	 *
	 * @param  numOfReferences, numOfVirtualPages, Dist
	 * @return int[]
	 */
	public int[] generateReferenceString(int numOfReferences, int numOfVirtualPages, int dist){
		Random random = new Random();
		int[] referenceString = new int[numOfReferences];
		// Possible References Numbers are 1 - numOfVirtualPages
		if(dist == UNIFORM_DIST) {
			for(int i=0; i<numOfReferences; i++)
				referenceString[i] = 1 + random.nextInt(numOfVirtualPages);
		}
		else if(dist == GAUSSIAN_DIST){
			// Calculation Of the Mean
			int sum = 0;
			for (int i=1; i <= numOfVirtualPages; i++) 
				sum += i;
			double mean = sum/numOfVirtualPages;
			System.out.println("MEAN: " + mean);
			
			// Calculation Of The Standard Deviation
			sum = 0;
			for(int i=1; i <= numOfVirtualPages; i++)
				sum += Math.pow((i - mean),2);
			double standardDeviation = (int) Math.sqrt(sum/(numOfVirtualPages-1));
			System.out.println("DEVIATION: " + standardDeviation);
			
			//for(int i=0; i<numOfReferences; i++)
			//	referenceString[i] = (int)((1/standardDeviation*Math.sqrt(2*Math.PI)) * Math.exp(-0.5 * Math.pow((numOfVirtualPages-mean)/standardDeviation, 2))); 
			random = new Random();
			for(int i=0;i<numOfReferences; i++)
				referenceString[i] = (int) Math.abs((random.nextGaussian() * standardDeviation) + mean);
		}
		return referenceString;
	}

	/**
	 * calculatePageFaults
	 *
	 * @param  
	 * @return 
	 */
	public int calculatePageFaults(int[] refStrings, int pageSize, int memorySize, int policy ) {
		int numOfPages = (int) (memorySize/pageSize);
		System.out.println("NUM OF PAGES: " + numOfPages);
		
		ArrayList<Integer> queue = new ArrayList<Integer>();
		int numOfFaults=0;
		if(policy == FIFO){
			int nextPageIndex=0;
			for(int i=0; i<refStrings.length; i++){
				System.out.println("QUEUE: " + queue);
				System.out.println("ITEM: " + refStrings[i]);
				if(queue.contains(refStrings[i])) continue;
				if(queue.size() < numOfPages) queue.add(refStrings[i]);
				else queue.set(nextPageIndex, refStrings[i]);
				numOfFaults++;
				nextPageIndex++; nextPageIndex %= numOfPages;
			}
		}
		else if(policy == LRU){
			// Implemented Using Stack Implementation Methodology
			boolean flag = true;
			for(int i=0; i<refStrings.length; i++){
				flag = true;
				System.out.println("QUEUE: " + queue);
				System.out.println("ITEM: " + refStrings[i]);
				int index=-1;
				if((index = queue.indexOf(refStrings[i])) != -1) 
				{
					queue.remove(index);
					flag = false;
				}	
				if(queue.size() < numOfPages) queue.add(refStrings[i]);
				else {
					queue.remove(0);
					queue.add(refStrings[i]);
				}
				if(flag) numOfFaults++;
			}
		}
		return numOfFaults;
	}

	
	
	
	public static void main(String[] args) {
		ReplacementSimulator rs = new ReplacementSimulator();
		
	}
}
