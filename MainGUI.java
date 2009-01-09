import java.awt.*;
import java.swing.*;
import java.awt.event.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class MainGUI extends JFrame implements ActionListener  {
    
	// Labels
	private JLabel distLabel = new JLabel("Distribution");
	private JLabel policyLabel = new JLabel("Policy");
	private JLabel memorySizeLabel = new JLabel("Memory Size");
	private JLabel pageSizeLabel = new JLabel("Page Size");
	private JLabel kbyteLabel = new JLabel("KB");
	private JLabel numOfReferencesLabel = new JLabel("# References");
	private JLabel numOfVirtualPagesLabel = new JLabel("# Virtual Pages");
	
	// Fields
	private JComboBox distField 		= new JComboBox();
	private JComboBox policyField 		= new JComboBox("Policy");
	private JComboBox memorySizeField 	= new JComboBox("Memory Size");
	private JComboBox pageSizeField 	= new JComboBox("Page Size");
	private JComboBox numOfReferencesField 		= new JComboBox("# References");
	private JComboBox numOfVirtualPagesField	= new JComboBox("# Virtual Pages");
	
	// {{{ MainGUI constructor
    /**
     * 
     */
    public MainGUI() {
        
    }
	// }}}
}
