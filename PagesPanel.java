import java.awt.*;
import javax.swing.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib & Noura Salhi
 * @version $Rev$
 */
public final class PagesPanel extends JPanel{
    private int numberOfPages = 16;
	private JLabel[] pageLabels;
	private int[] pages;
	private int nextReplace=0;
	// {{{ PagesPanel constructor
    /**
     * 
     */
    public PagesPanel(int n) {
		numberOfPages = n;
        pageLabels = new JLabel[numberOfPages];
		pages = new int[numberOfPages];
		setLayout(new GridLayout(0,8));
		for(int i=0; i<pages.length; i++){
			pageLabels[i] = new JLabel(" ", JLabel.CENTER);
			//pages[i] = i+1;
			pageLabels[i].setOpaque(true);
			pageLabels[i].setBackground(Color.YELLOW);
			pageLabels[i].setBorder(BorderFactory.createLineBorder(Color.BLUE)/*BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10,10,10,10),BorderFactory.createLineBorder(Color.BLUE))*/);
			add(pageLabels[i]);
		}
    }
	// }}}
	
	/**
	 * setNextReplacePage
	 *
	 * @param  
	 * @return 
	 */
	public void setNextReplacePage(int index) {
		for(int i=0; i<pageLabels.length; i++)
			pageLabels[i].setBackground(Color.YELLOW);
		pageLabels[index].setBackground(Color.RED);
		nextReplace = index;
		repaint();
	}

	/**
	 * setNextReplaceReference
	 *
	 * @param  
	 * @return 
	 */
	public void setNextReplaceReference(int ref ) {
		int index = indexOf(ref);
		if(index == -1) return;
		setNextReplacePage(index);
	}

	/**
	 * highlightPage
	 *
	 * @param  
	 * @return 
	 */
	public void highlightPage(int i ) {
		pageLabels[i].setBackground(Color.WHITE);
	}

	public void highlightReference(int ref ) {
		int index = indexOf(ref);
		if(index == -1) return;
		pageLabels[index].setBackground(Color.WHITE);
	}
	
	/**
	 * replacePages
	 *
	 * @param  
	 * @return 
	 */
	public void replacePages(int ref1, int ref2 ) {
		int index = indexOf(ref1);
		if(index == -1) return;
		pageLabels[index].setText(""+ref2);
		pageLabels[index].setBackground(Color.RED);
		pages[index] = ref2;
	}

	/**
	 * setNextReplace
	 *
	 * @param i 
	 * @return 
	 */
	public void setNextReplace(int i) {
		nextReplace = i;
	}

	public void setNextReplaceRef(int ref) {
		int index = indexOf(ref);
		if(index == -1) return;
		nextReplace = index;
	}
	
	/**
	 * indexOf
	 *
	 * @param  
	 * @return 
	 */
	public int indexOf(int item ) {
		for(int i=0; i<pages.length; i++){
			if(pages[i] == item)
				return i;
		}
		return -1;
	}

	/**
	 * setPage
	 *
	 * @param i 
	 * @return 
	 */
	public void setPage(int i) {
		pages[nextReplace] = i;
		pageLabels[nextReplace].setText(""+i);
	}
	
	/**
	 * setNumberOfPages
	 *
	 * @param pages 
	 * @return 
	 */
	public void setNumberOfPages(int pages) {
		numberOfPages = pages;
	}
}
