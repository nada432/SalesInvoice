
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
public class SalesInvoiceDialog extends JDialog {
    private JTextField CustomerNameField;
    private JTextField InvoiceDateField;
    private JLabel CustomerNameLbl;
    private JLabel InvoiceDateLbl;
    private JButton okButton;
    private JButton CancelButton;

    public SalesInvoiceDialog(SalesInvoiceFrame frame) {
        CustomerNameLbl = new JLabel("Customer Name:");
        CustomerNameField = new JTextField(20);
        InvoiceDateLbl = new JLabel("Invoice Date:");
        InvoiceDateField = new JTextField(20);
        okButton = new JButton("OK");
        CancelButton = new JButton("Cancel");
        
        okButton.setActionCommand("createInvoiceOK");
        CancelButton.setActionCommand("createInvoiceCancel");
        
        okButton.addActionListener(frame.getController());
        CancelButton.addActionListener(frame.getController());
        setLayout(new GridLayout(3, 2));
        
        add(InvoiceDateLbl);
        add(InvoiceDateField);
        add(CustomerNameLbl);
        add(CustomerNameField);
        add(okButton);
        add(CancelButton);
        
        pack();
        
    }

    public JTextField getCustNameField() {
        return CustomerNameField;
    }

    public JTextField getInvDateField() {
        return InvoiceDateField;
    }
    
}

