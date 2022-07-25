package controller;

import model.SalesInvoice;
import model.SalesInvoicesTableModel;
import model.SalesInvoiceLine;
import model.SalesLinesTableModel;
import view.SalesInvoiceDialog;
import view.SalesInvoiceFrame;
import view.SalesLineDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Controller implements ActionListener, ListSelectionListener {

    private SalesInvoiceFrame frame;
    private SalesInvoiceDialog SalesinvoiceDialog;
    private SalesLineDialog SaleslineDialog;

    public Controller(SalesInvoiceFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
    
        switch (actionCommand) {
            case "Load File":
                loadFile();
                break;
            case "Save File":
                saveFile();
                break;
            case "Create New Invoice":
                createNewInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Create New Item":
                createNewItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "createInvoiceCancel":
                createInvoiceCancel();
                break;
            case "createInvoiceOK":
                createInvoiceOK();
                break;
            case "createLineOK":
                createLineOK();
                break;
            case "createLineCancel":
                createLineCancel();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = frame.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1) {
            
            SalesInvoice currentInvoice = frame.getInvoices().get(selectedIndex);
            frame.getInvoiceNumLabel().setText("" + currentInvoice.getNum());
            frame.getInvoiceDateLabel().setText(currentInvoice.getDate());
            frame.getCustomerNameLabel().setText(currentInvoice.getCustomer());
            frame.getInvoiceTotalLabel().setText("" + currentInvoice.getInvoiceTotal());
            SalesLinesTableModel linesTableModel = new SalesLinesTableModel(currentInvoice.getLines());
            frame.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
        }
    }

    private void loadFile() {
        JFileChooser filechoose = new JFileChooser();
        try {
            int result = filechoose.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = filechoose.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
              
               
                ArrayList<SalesInvoice> invoicesArray = new ArrayList<>();
                for (String headerLine : headerLines) {
                    try {
                        String[] headerParts = headerLine.split(",");
                        int invoiceNum = Integer.parseInt(headerParts[0]);
                        String invoiceDate = headerParts[1];
                        String customerName = headerParts[2];

                        SalesInvoice invoice = new SalesInvoice(invoiceNum, invoiceDate, customerName);
                        invoicesArray.add(invoice);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
               
                result = filechoose.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = filechoose.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    
                    for (String lineLine : lineLines) {
                        try {
                            String lineParts[] = lineLine.split(",");
                            int invoiceNum = Integer.parseInt(lineParts[0]);
                            String itemName = lineParts[1];
                            double itemPrice = Double.parseDouble(lineParts[2]);
                            int count = Integer.parseInt(lineParts[3]);
                            SalesInvoice inv = null;
                            for (SalesInvoice invoice : invoicesArray) {
                                if (invoice.getNum() == invoiceNum) {
                                    inv = invoice;
                                    break;
                                }
                            }

                            SalesInvoiceLine line = new SalesInvoiceLine(itemName, itemPrice, count, inv);
                            inv.getLines().add(line);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                   
                }
                frame.setInvoices(invoicesArray);
                SalesInvoicesTableModel invoicesTableModel = new SalesInvoicesTableModel(invoicesArray);
                frame.setInvoicesTableModel(invoicesTableModel);
                frame.getInvoiceTable().setModel(invoicesTableModel);
                frame.getInvoicesTableModel().fireTableDataChanged();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFile() {
        ArrayList<SalesInvoice> invoices = frame.getInvoices();
        String headers = "";
        String lines = "";
        for (SalesInvoice invoice : invoices) {
            String invoicefoldrer = invoice.getAsCSV();
            headers += invoicefoldrer;
            headers += "\n";

            for (SalesInvoiceLine line : invoice.getLines()) {
                String linefolder = line.getAsCSV();
                lines += linefolder;
                lines += "\n";
            }
        }
       
        try {
            JFileChooser filechoose = new JFileChooser();
            int result = filechoose.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = filechoose.getSelectedFile();
                FileWriter headerfilewriter = new FileWriter(headerFile);
                headerfilewriter.write(headers);
                headerfilewriter.flush();
                headerfilewriter.close();
                result = filechoose.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = filechoose.getSelectedFile();
                    FileWriter linefilewriter = new FileWriter(lineFile);
                    linefilewriter.write(lines);
                    linefilewriter.flush();
                    linefilewriter.close();
                }
            }
        } catch (Exception ex) {

        }
    }

    private void createNewInvoice() {
        SalesinvoiceDialog = new SalesInvoiceDialog(frame);
        SalesinvoiceDialog.setVisible(true);
    }

    private void deleteInvoice() {
        int selectedRow = frame.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {
            frame.getInvoices().remove(selectedRow);
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createNewItem() {
        SaleslineDialog = new SalesLineDialog(frame);
        SaleslineDialog.setVisible(true);
    }

    private void deleteItem() {
        int selectedRow = frame.getLineTable().getSelectedRow();

        if (selectedRow != -1) {
            SalesLinesTableModel linesTableModel = (SalesLinesTableModel) frame.getLineTable().getModel();
            linesTableModel.getLines().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createInvoiceCancel() {
        SalesinvoiceDialog.setVisible(false);
        SalesinvoiceDialog.dispose();
        SalesinvoiceDialog = null;
    }

    private void createInvoiceOK() {
        String date = SalesinvoiceDialog.getInvDateField().getText();
        String customer = SalesinvoiceDialog.getCustNameField().getText();
        int num = frame.getNextInvoiceNum();
        try {
            String[] dateParts = date.split("-");  // "22-05-2013" -> {"22", "05", "2013"}  xy-qw-20ij
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    SalesInvoice invoice = new SalesInvoice(num, date, customer);
                    frame.getInvoices().add(invoice);
                    frame.getInvoicesTableModel().fireTableDataChanged();
                    SalesinvoiceDialog.setVisible(false);
                    SalesinvoiceDialog.dispose();
                    SalesinvoiceDialog = null;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void createLineOK() {
        String item = SaleslineDialog.getItemNameField().getText();
        String countStr = SaleslineDialog.getItemCountField().getText();
        String priceStr = SaleslineDialog.getItemPriceField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = frame.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            SalesInvoice invoice = frame.getInvoices().get(selectedInvoice);
            SalesInvoiceLine line = new SalesInvoiceLine(item, price, count, invoice);
            invoice.getLines().add(line);
            SalesLinesTableModel linesTableModel = (SalesLinesTableModel) frame.getLineTable().getModel();
            //linesTableModel.getLines().add(line);
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
        SaleslineDialog.setVisible(false);
        SaleslineDialog.dispose();
        SaleslineDialog = null;
    }

    private void createLineCancel() {
        SaleslineDialog.setVisible(false);
        SaleslineDialog.dispose();
        SaleslineDialog = null;
    }

}
