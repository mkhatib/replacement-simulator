import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;
import javax.swing.border.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public class MainGUI extends JFrame implements ActionListener {
    ReplacementSimulator simulateModel;
	
	// Labels
	private JLabel distLabel = new JLabel("Distribution");
	private JLabel policyLabel = new JLabel("Policy");
	private JLabel memorySizeLabel = new JLabel("Memory Size");
	private JLabel pageSizeLabel = new JLabel("Page Size");
	private JLabel kbyteLabel = new JLabel("KB");
	private JLabel numOfReferencesLabel = new JLabel("# References");
	private JLabel numOfVirtualPagesLabel = new JLabel("# Virtual Pages");
	private JLabel emptyLabel = new JLabel("                           ");
	// Fields
	private JComboBox distField 		= new JComboBox(new String[]{"Uniform","Normal Gaussian"});
	private JComboBox policyField 		= new JComboBox(new String[]{"FIFO","LRU"});
	private JComboBox memorySizeField 	= new JComboBox(new String[]{"16","32","64","256"});
	private JComboBox pageSizeField 	= new JComboBox(new String[]{"1"});
	private JComboBox numOfReferencesField 		= new JComboBox(new String[]{"100","1000"});
	private JComboBox numOfVirtualPagesField	= new JComboBox(new String[]{"10","100","1000"});
	
	private JButton simulateBtn = new JButton("Simulate");
	PagesPanel pagesPanel = new PagesPanel(16);
	// {{{ MainGUI constructor
    /**
     * 
     */
    public MainGUI() {
        //setLayout (new FlowLayout(FlowLayout.LEFT, 10, 20));
		

		//JFrame mainFrame = new JFrame();
		JPanel secondPanel = new JPanel();
		secondPanel.setLayout(new GridLayout(7, 2, 5, 5));
		//JPanel memoryPanel = new JPanel(new FlowLayout());
		//memoryPanel.add(memorySizeLabel);
		//memoryPanel.add(memorySizeField); 
		//add(memorySizeLabel);
		//add(memorySizeField);
		secondPanel.add(memorySizeLabel);
		secondPanel.add(memorySizeField); 
		//secondPanel.add(emptyLabel);
		
		//JPanel pagePanel = new JPanel(new FlowLayout());
		//pagePanel.add(pageSizeLabel);
		//pagePanel.add(pageSizeField); 
		//add(pageSizeLabel);
		//add(pageSizeField);
		secondPanel.add(pageSizeLabel);
		secondPanel.add(pageSizeField);
		//secondPanel.add(kbyteLabel);
		//JPanel policyPanel = new JPanel(new FlowLayout());
		//policyPanel.add(policyLabel);
		//policyPanel.add(policyField); 
		//add(policyLabel);
		//add(policyField);
		secondPanel.add(policyLabel);
		secondPanel.add(policyField);
		//secondPanel.add(emptyLabel);
		//JPanel referencesPanel = new JPanel(new FlowLayout());
		//referencesPanel.add(numOfReferencesLabel);
		//referencesPanel.add(numOfReferencesField); 
		//add(numOfReferencesLabel);
		//add(numOfReferencesField);
		secondPanel.add(numOfReferencesLabel);
		secondPanel.add(numOfReferencesField);
		secondPanel.add(emptyLabel);
		//JPanel virtualPagesPanel = new JPanel(new FlowLayout());
		//virtualPagesPanel.add(numOfVirtualPagesLabel);
		//virtualPagesPanel.add(numOfVirtualPagesField);
		//add(numOfVirtualPagesLabel);
		//add(numOfVirtualPagesField);
		secondPanel.add(numOfVirtualPagesLabel);
		secondPanel.add(numOfVirtualPagesField);
		//secondPanel.add(emptyLabel);
		//JPanel distributionPanel = new JPanel(new FlowLayout());
		//distributionPanel.add(distLabel);
		//distributionPanel.add(distField); 
		//add(distLabel);
		//add(distField);
		secondPanel.add(distLabel);
		secondPanel.add(distField);
		//secondPanel.add(emptyLabel);
		secondPanel.add(emptyLabel);
		secondPanel.add(simulateBtn);
		/* add(memoryPanel);
		add(pagePanel);
		add(policyPanel);
		add(referencesPanel);
		add(virtualPagesPanel);
		add(distributionPanel);  */
		JPanel mainPanel = new JPanel(new BorderLayout());
		/* mainPanel.add(memoryPanel);
		mainPanel.add(pagePanel);
		mainPanel.add(policyPanel);
		mainPanel.add(referencesPanel);
		mainPanel.add(virtualPagesPanel);
		mainPanel.add(distributionPanel); */ 
		
		mainPanel.add(secondPanel,BorderLayout.CENTER);
		mainPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Replacemet Simulator"),new EmptyBorder(60,20,60,20)));
		
		//mainPanel.add(new JLabel("Replacement Simulator"),BorderLayout.NORTH);
		add(mainPanel,BorderLayout.CENTER);
		//pagesPanel.setNextReplacePage(0);
		//add(pagesPanel,BorderLayout.NORTH);
		//setSize(600,200);
		//setVisible(true);
		
		simulateBtn.addActionListener(this);
		//add(new JLabel("Replacement Simulator"),BorderLayout.NORTH);
 	//add(secondPanel,BorderLayout.CENTER);
		
    }
	// }}}
	
	 public static void main(String[] args) {
			    MainGUI mainFrame = new MainGUI(); // Create a frame
			  
			  mainFrame.setSize(500, 500); // Set the frame size
			    mainFrame.setLocationRelativeTo(null); // New since JDK 1.4
			    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				
			     mainFrame.setVisible(true);  
			
   }
   
   public void actionPerformed(ActionEvent e) {
		//String action = e.getActionCommand();
		simulateModel = new ReplacementSimulator();
	
		simulateModel.setMemorySize(Integer.parseInt((String)memorySizeField.getSelectedItem()));
		simulateModel.setPageSize(Integer.parseInt((String)pageSizeField.getSelectedItem()));
		simulateModel.setPolicy(policyField.getSelectedIndex());
		simulateModel.setNumOfReferences(Integer.parseInt((String)numOfReferencesField.getSelectedItem()));
		simulateModel.setnumOfVirtualPages(Integer.parseInt((String)numOfVirtualPagesField.getSelectedItem()));
		simulateModel.setDistribution(distField.getSelectedIndex()); 
		
		 
		int[] refString1 = simulateModel.generateReferenceString(simulateModel.getNumOfReferences(),simulateModel.getNumOfVirtualPages(),simulateModel.getDistribution());
		int pageFaults = simulateModel.calculatePageFaults(refString1, simulateModel.getPageSize(), simulateModel.getMemorySize(), simulateModel.getPolicy()); // FIFO, 16KB
				
			
				
				
   }
   
   
   
}

