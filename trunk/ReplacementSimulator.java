import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class ReplacementSimulator extends Thread {
    PagesPanel pagesPanel;// = new PagesPanel(16);
	
	public static final int UNIFORM_DIST = 0, GAUSSIAN_DIST = 1;
	public static final int FIFO = 0, LRU = 1;
	
	
	private int delay=1000; //miliseconds
	private int pageSize = 1; // 1KB
	private int memorySize;
	private int policy;
	private int distribution;
	private int numOfVirtualPages;
	private int numOfReferences;
	
	
	ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	
	// {{{ ReplacementSimulator constructor
    /**
     * 
     */
    public ReplacementSimulator(PagesPanel pp) {
		//super();
		pagesPanel = pp;
		/* MainGUI mainFrame = new MainGUI(); // Create a frame
			  
			  mainFrame.setSize(500, 500); // Set the frame size
			    mainFrame.setLocationRelativeTo(null); // New since JDK 1.4
			    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				
			     mainFrame.setVisible(true);  */
			
		pagesPanel.setNextReplacePage(0);
		//setSize(600,200);
		//add(pagesPanel);
		//setVisible(true);  
       // int[] refString1 = generateReferenceString(1000,100,UNIFORM_DIST);
		//int pageFaults = calculatePageFaults(refString1, 1, 16, FIFO); // FIFO, 16KB
		//System.out.println("Num Of Faults: " + pageFaults);
		//int[] refString1 = generateReferenceString(getNumOfReferences(),getNumOfVirtualPages(),getDistribution());
		//int pageFaults = calculatePageFaults(refString1, getPageSize(), getMemorySize(), getPolicy()); // FIFO, 16KB
		
		
    }
	// }}}
	
	/**
	 * run
	 *
	 * @param  
	 * @return 
	 */
	public void run() {
		try {
			int[] refString1 = generateReferenceString(numOfReferences,numOfVirtualPages,distribution);
			int pageFaults = calculatePageFaults(refString1, pageSize, memorySize, policy); // FIFO, 16KB
		} catch (Exception e) {
			// expression
		} finally {
			// expression
		}
	}

	
	
	
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
				if(queue.contains(refStrings[i])) 
				{	
					// Highlight the Page
					pagesPanel.highlightPage(queue.indexOf(refStrings[i]));
					try {
						Thread.sleep(delay);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					continue;
				}
				if(queue.size() < numOfPages){
					queue.add(refStrings[i]);
					pagesPanel.setPage(refStrings[i]);
					try {
						Thread.sleep(delay);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} 
				else 
				{
					queue.set(nextPageIndex, refStrings[i]);
					pagesPanel.setPage(refStrings[i]);
					try {
						Thread.sleep(delay);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				numOfFaults++;
				notifyListeners(new ActionEvent(this,numOfFaults, "New Fault"));
				nextPageIndex++; nextPageIndex %= numOfPages;
				pagesPanel.setNextReplacePage(nextPageIndex);
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
					pagesPanel.highlightReference(refStrings[i]);
					try {
						Thread.sleep(delay);
					} catch (Exception e) {
						e.printStackTrace();
					}
					queue.remove(index);
					flag = false;
				}	
				if(queue.size() < numOfPages) 
				{
					queue.add(refStrings[i]);
					if(flag) {
						System.out.println("NEXT: " + (queue.size()-1));
						pagesPanel.setPage(refStrings[i]);
						//pagesPanel.setNextReplacePage(queue.size()-1/*refStrings[i]*/);
						
						
						pagesPanel.setNextReplacePage(queue.size()-1);
						pagesPanel.setNextReplace(queue.size());	
					}
					else if(!(queue.size() < numOfPages))
					{
						pagesPanel.setNextReplaceReference(queue.get(0));
						pagesPanel.setNextReplace(i);						
						
					} 
					else if(queue.size() < numOfPages){
						System.out.println("NEXT: " + queue.size());
						
						pagesPanel.setNextReplacePage(queue.size());
					}
					try {
						Thread.sleep(delay);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else {
					//pagesPanel.setNextReplaceReference(queue.get(0));
					pagesPanel.replacePages(queue.get(0),refStrings[i]);
					queue.remove(0);
					queue.add(refStrings[i]);
					pagesPanel.setNextReplaceRef(queue.get(0));						
					
					try {
						Thread.sleep(delay);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				if(flag) {
					numOfFaults++;
					notifyListeners(new ActionEvent(this,numOfFaults, "New Fault"));
				}
			}
		}
		notifyListeners(new ActionEvent(this,numOfFaults, "Simulation Finished"));
		return numOfFaults;
	}



	/**
	 * addActionListener
	 *
	 * @param  
	 * @return 
	 */
	public void addActionListener(ActionListener l ) {
		if(listeners.contains(l)) return;
		listeners.add(l);
	}
	
	/**
	 * notifyListeners
	 *
	 * @param  
	 * @return 
	 */
	public void notifyListeners(ActionEvent e) {
		if(listeners.size() == 0) return;
		ArrayList<ActionListener> copy;
		synchronized(this){
			copy = (ArrayList<ActionListener>)listeners.clone();
		}
		for(int i=0; i< copy.size(); i++)
			copy.get(i).actionPerformed(e);
	}

	

	
	public void setMemorySize(int mSize)
	{
		
		memorySize = mSize;
	}
	public void setPageSize(int pSize)
	{
		pageSize = pSize;
	}
	public void setPolicy(int policy)
	{
		this.policy = policy; //FIF0 = 0
	}
	public void setNumOfReferences(int NumOfRef)
	{
		numOfReferences = NumOfRef;
	}
	public void setnumOfVirtualPages(int numOfVirtualPages)
	{
		this.numOfVirtualPages = numOfVirtualPages;
	}
	public void setDistribution(int dist)
	{
		distribution = dist;
	}	
	
	public int getMemorySize()
	{
		
		return memorySize;
	}
	public int getPageSize()
	{
		return pageSize;
	}
	public int getPolicy()
	{
		return policy; //FIF0 = 0
	}
	public int getNumOfReferences()
	{
		return numOfReferences;
	}
	public int getNumOfVirtualPages()
	{
		return numOfVirtualPages;
	}
	public int getDistribution()
	{
		return distribution;
	}	
	
	public void setDelay(int delay){
		this.delay = delay;
	}
	
	public static void main(String[] args) {
		//ReplacementSimulator rs = new ReplacementSimulator();
		
	}
}
