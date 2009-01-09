import java.awt.*;
import javax.swing.*;


/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
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
	 * paintComponent
	 *
	 * @param  
	 * @return 
	* /
	public void paintComponent(Graphics g ) {
		/*super.paintComponent(g);
				for(int i=0; i<numberOfPages; i++){
					g.drawRect((i*50 + 10), 10, 40,40 );
				}* /
	}
	*/
	
	
	
	/**
	 * setNumberOfPages
	 *
	 * @param pages 
	 * @return 
	 */
	public void setNumberOfPages(int pages) {
		numberOfPages = pages;
	}


	
	
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		PagesPanel pp = new PagesPanel(16);
		pp.setNumberOfPages(16);
		frame.add(pp);
		frame.setSize(800,100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		pp.setNextReplacePage(5);
	}
}
