
package view;
 
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author DELL
 */
public class SalesLineDialog extends JDialog{
    private JTextField NameField;
    private JTextField CountField;
    private JTextField PriceField;
    private JLabel NameLbl;
    private JLabel CountLabal;
    private JLabel PriceLabal;
    private JButton okBtn;
    private JButton cancelBtn;
    
    public SalesLineDialog(SalesInvoiceFrame frame) {
        NameField = new JTextField(20);
        NameLbl = new JLabel("Item Name");
        
        CountField = new JTextField(20);
        CountLabal = new JLabel("Item Count");
        
        PriceField = new JTextField(20);
        PriceLabal = new JLabel("Item Price");
        
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.setActionCommand("createLineOK");
        cancelBtn.setActionCommand("createLineCancel");
        
        okBtn.addActionListener(frame.getController());
        cancelBtn.addActionListener(frame.getController());
        setLayout(new GridLayout(4, 2));
        
        add(NameLbl);
        add(NameField);
        add(CountLabal);
        add(CountField);
        add(PriceLabal);
        add(PriceField);
        add(okBtn);
        add(cancelBtn);
        
        pack();
    }

    public JTextField getItemNameField() {
        return NameField;
    }

    public JTextField getItemCountField() {
        return CountField;
    }

    public JTextField getItemPriceField() {
        return PriceField;
    }
}

