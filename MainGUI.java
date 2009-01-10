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
	private JButton stopBtn = new JButton("Stop");
	private JButton fasterBtn = new JButton("Faster");
	private JButton slowerBtn = new JButton("Slower");
	PagesPanel pagesPanel = new PagesPanel(16);
	// {{{ MainGUI constructor
    /**
     * 
     */
    public MainGUI() {
		JPanel secondPanel = new JPanel();
		secondPanel.setLayout(new GridLayout(8, 2, 5, 5));

		secondPanel.add(memorySizeLabel);
		secondPanel.add(memorySizeField); 

		secondPanel.add(pageSizeLabel);
		secondPanel.add(pageSizeField);

		secondPanel.add(policyLabel);
		secondPanel.add(policyField);

		secondPanel.add(numOfReferencesLabel);
		secondPanel.add(numOfReferencesField);

		secondPanel.add(numOfVirtualPagesLabel);
		secondPanel.add(numOfVirtualPagesField);

		secondPanel.add(distLabel);
		secondPanel.add(distField);

		numOfFaultsField.setEditable(false);
		secondPanel.add(numOfFaultsLabel);
		secondPanel.add(numOfFaultsField);
		
		JPanel buttonsPanel1 = new JPanel();
		buttonsPanel1.add(fasterBtn);
		buttonsPanel1.add(slowerBtn);
		JPanel buttonsPanel2 = new JPanel();
		buttonsPanel2.add(simulateBtn);
		buttonsPanel2.add(stopBtn);

		secondPanel.add(buttonsPanel2);
		secondPanel.add(buttonsPanel1);

		JPanel mainPanel = new JPanel(new BorderLayout());

		mainPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Replacemet Simulator"),new EmptyBorder(10,20,10,20)));
		mainPanel.add(secondPanel,BorderLayout.CENTER);
		
		add(mainPanel,BorderLayout.WEST);
		//add(pagesPanel, BorderLayout.SOUTH);

		fasterBtn.addActionListener(this);
		slowerBtn.addActionListener(this);
		simulateBtn.addActionListener(this);
		stopBtn.addActionListener(this);
		setSize(windowSize); // Set the frame size
	    setLocationRelativeTo(null); // New since JDK 1.4
	   	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			if(simulateModel != null){
				simulateModel.stopProcessing();
			}
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
		else if(action.equals("Stop")){
			if(simulateModel != null)
			{
				simulateModel.stopProcessing();
				JOptionPane.showMessageDialog(this, "Simulation Was Interrupted By the User! ","Simulation Faild!",JOptionPane.ERROR_MESSAGE);
			} 
			
		}
   }
}

