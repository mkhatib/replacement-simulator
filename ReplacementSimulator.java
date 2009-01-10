import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib & Noura Salhi & Walid Abu Salah
 * @version $Rev$
 */
public final class ReplacementSimulator extends Thread {
    // Panel to show Visually How The Replacement Is Done
	PagesPanel pagesPanel;
	// Constants, Distribution types, and Policies
	public static final int UNIFORM_DIST = 0, GAUSSIAN_DIST = 1;
	public static final int FIFO = 0, LRU = 1;
	
	// Simulator Parameters
	// Speed of Simulation, Default 1 Second Delay
	private int delay=1000; //miliseconds
	private int pageSize = 1; // 1KB
	private int memorySize;
	private int policy;
	private int distribution;
	private int numOfVirtualPages;
	private int numOfReferences;
	
	private boolean stopped = false;
	
	ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	
	// {{{ ReplacementSimulator constructor
    /**
     * 
     */
    public ReplacementSimulator(PagesPanel pp) {
		pagesPanel = pp;
		pagesPanel.setNextReplacePage(0);
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
			e.printStackTrace();
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
			// Calculation Of The Standard Deviation
			sum = 0;
			for(int i=1; i <= numOfVirtualPages; i++)
				sum += Math.pow((i - mean),2);
			double standardDeviation = (int) Math.sqrt(sum/(numOfVirtualPages-1));
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
		ArrayList<Integer> queue = new ArrayList<Integer>();
		int numOfFaults=0;
		if(policy == FIFO){
			int nextPageIndex=0;
			for(int i=0; i<refStrings.length && !stopped; i++){
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
						pagesPanel.setPage(refStrings[i]);
						pagesPanel.setNextReplacePage(queue.size()-1);
						pagesPanel.setNextReplace(queue.size());	
					}
					else if(!(queue.size() < numOfPages))
					{
						pagesPanel.setNextReplaceReference(queue.get(0));
						pagesPanel.setNextReplace(i);						
					} 
					else if(queue.size() < numOfPages){
						pagesPanel.setNextReplacePage(queue.size());
					}
					try {
						Thread.sleep(delay);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else {
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
		if(!stopped)
			notifyListeners(new ActionEvent(this,numOfFaults, "Simulation Finished"));
		return numOfFaults;
	}

	/**
	 * stop
	 *
	 * @param  
	 * @return 
	 */
	public void stopProcessing() {
		stopped = true;
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

	// Setters and Getters
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
}
