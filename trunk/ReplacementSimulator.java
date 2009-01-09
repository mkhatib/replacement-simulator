import java.util.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class ReplacementSimulator {
    
	public static final int UNIFORM_DIST = 0, GAUSSIAN_DIST = 1;
	


	// {{{ ReplacementSimulator constructor
    /**
     * 
     */
    public ReplacementSimulator() {
        int[] refString1 = generateReferenceString(1000,1000,GAUSSIAN_DIST);
		int sum=0;
		for(int i=0; i<refString1.length ;i++){
			System.out.println(refString1[i]);
			sum += refString1[i];
		}
		System.out.println("AVERAGE: " + sum/refString1.length);
		
			
			
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

	
	public static void main(String[] args) {
		ReplacementSimulator rs = new ReplacementSimulator();
		
	}
}
