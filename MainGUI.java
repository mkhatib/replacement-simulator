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
    ReplacementSimulator simulateModel = null;
	
	private int delay=1000; // miliseconds
	private Dimension windowSize = new Dimension(500, 500);
	// Labels
	private JLabel distLabel = new JLabel("Distribution");
	private JLabel policyLabel = new JLabel("Policy");
	private JLabel memorySizeLabel = new JLabel("Memory Size");
	private JLabel pageSizeLabel = new JLabel("Page Size");
	private JLabel kbyteLabel = new JLabel("KB");
	private JLabel numOfReferencesLabel = new JLabel("# References");
	private JLabel numOfVirtualPagesLabel = new JLabel("# Virtual Pages");
	private JLabel emptyLabel = new JLabel("                           ");
	private JLabel numOfFaultsLabel = new JLabel("# Page Faults");
	// Fields
	private JComboBox distField 		= new JComboBox(new String[]{"Uniform","Normal Gaussian"});
	private JComboBox policyField 		= new JComboBox(new String[]{"FIFO","LRU"});
	private JComboBox memorySizeField 	= new JComboBox(new String[]{"16","32","64","256"});
	private JComboBox pageSizeField 	= new JComboBox(new String[]{"1"});
	private JComboBox numOfReferencesField 		= new JComboBox(new String[]{"100","1000"});
	private JComboBox numOfVirtualPagesField	= new JComboBox(new String[]{"10","100","1000"});
	private JTextField numOfFaultsField = new JTextField("0");
	
	
	private JButton simulateBtn = new JButton("Simulate");
	private JButton fasterBtn = new JButton("Faster");
	private JButton slowerBtn = new JButton("Slower");
	PagesPanel pagesPanel = new PagesPanel(16);
	// {{{ MainGUI constructor
    /**
     * 
     */
    public MainGUI() {
        //setLayout (new FlowLayout(FlowLayout.LEFT, 10, 20));
		

		//JFrame mainFrame = new JFrame();
		JPanel secondPanel = new JPanel();
		secondPanel.setLayout(new GridLayout(8, 2, 5, 5));
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
		//secondPanel.add(emptyLabel);
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
		//secondPanel.add(emptyLabel);
		numOfFaultsField.setEditable(false);
		secondPanel.add(numOfFaultsLabel);
		secondPanel.add(numOfFaultsField);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(fasterBtn);
		buttonsPanel.add(slowerBtn);
		secondPanel.add(simulateBtn);
		secondPanel.add(buttonsPanel);
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
		mainPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Replacemet Simulator"),new EmptyBorder(10,20,10,20)));
		
		//mainPanel.add(new JLabel("Replacement Simulator"),BorderLayout.NORTH);
		add(mainPanel,BorderLayout.WEST);
		add(pagesPanel, BorderLayout.SOUTH);
		//pagesPanel.setNextReplacePage(0);
		//add(pagesPanel,BorderLayout.NORTH);
		//setSize(600,200);
		//setVisible(true);
		fasterBtn.addActionListener(this);
		slowerBtn.addActionListener(this);
		simulateBtn.addActionListener(this);
		setSize(windowSize); // Set the frame size
	    setLocationRelativeTo(null); // New since JDK 1.4
	   	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//add(new JLabel("Replacement Simulator"),BorderLayout.NORTH);
 	//add(secondPanel,BorderLayout.CENTER);
		
    }
	// }}}
	
	 public static void main(String[] args) {
			    MainGUI mainFrame = new MainGUI(); // Create a frame				
			     mainFrame.setVisible(true);  
			
   }
   
   public void actionPerformed(ActionEvent e) {
		
		String action = e.getActionCommand();
		if(action.equals("Simulate")){
			numOfFaultsField.setText("0");
			int memorySize = Integer.parseInt((String)memorySizeField.getSelectedItem());
			int pageSize = Integer.parseInt((String)pageSizeField.getSelectedItem());
			int numOfPages = (int)(memorySize/pageSize);
			Dimension newWindowSize; 
			
			remove(pagesPanel);
			pagesPanel = new PagesPanel(numOfPages);
			if(numOfPages > 64)
			{
				newWindowSize = new Dimension((int)(windowSize.getWidth() + 8*40), (int)windowSize.getHeight()+ (int)(10*numOfPages/8));
				add(pagesPanel, BorderLayout.CENTER);
			}
				
			else 
			{
				newWindowSize = new Dimension((int)(windowSize.getWidth()), (int)windowSize.getHeight()+ (int)(10*numOfPages/8));
				add(pagesPanel, BorderLayout.SOUTH);
			}
				
			setSize(newWindowSize);
			
			validate();
			repaint();



			simulateModel = new ReplacementSimulator(pagesPanel);
			

			simulateModel.setMemorySize(Integer.parseInt((String)memorySizeField.getSelectedItem()));
			simulateModel.setPageSize(Integer.parseInt((String)pageSizeField.getSelectedItem()));
			simulateModel.setPolicy(policyField.getSelectedIndex());
			simulateModel.setNumOfReferences(Integer.parseInt((String)numOfReferencesField.getSelectedItem()));
			simulateModel.setnumOfVirtualPages(Integer.parseInt((String)numOfVirtualPagesField.getSelectedItem()));
			simulateModel.setDistribution(distField.getSelectedIndex()); 
			simulateModel.setDelay(delay);
			
			// To tell the GUI when there's a fault so it can increase the Feild
			simulateModel.addActionListener(this);
			
			simulateModel.start();
		}
		else if(action.equals("Faster")){
			delay -= 100;
			if(delay < 0) delay = 0;
			if(simulateModel != null) simulateModel.setDelay(delay);
		}
		else if(action.equals("Slower")){
			delay += 100;
			if(delay < 0) delay = 0;
			if(simulateModel != null) simulateModel.setDelay(delay);
		}
		else if(action.equals("New Fault")){
			int tmp = Integer.parseInt(numOfFaultsField.getText());
			tmp++;
			numOfFaultsField.setText(""+tmp);
		}
		else if(action.equals("Simulation Finished")){
			JOptionPane.showMessageDialog(this, "Simulation is Finished!\n # Of Faults: "+e.getID(),"Simulation Finished!",JOptionPane.INFORMATION_MESSAGE);
		}
		
		//int[] refString1 = simulateModel.generateReferenceString(simulateModel.getNumOfReferences(),simulateModel.getNumOfVirtualPages(),simulateModel.getDistribution());
		//int pageFaults = simulateModel.calculatePageFaults(refString1, simulateModel.getPageSize(), simulateModel.getMemorySize(), simulateModel.getPolicy()); // FIFO, 16KB
				
			
				
				
   }
   
   
   
}

