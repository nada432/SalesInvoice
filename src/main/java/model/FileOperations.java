package model;


import java.util.ArrayList;



import view.SalesInvoiceFrame;


public class FileOperations {
    private SalesInvoiceFrame frame;

     ArrayList<SalesInvoice> invoicesArray = frame.getInvoices();
    static String headers = "";
    static String lines = "";

    public void readFilesData() {
        for (SalesInvoice invoice : invoicesArray) {
            headers += invoice.toString();
            headers += "\n";
            System.out.println("{Invoice Num:" + invoice.getNum()+ "\n");
            writeFilesHeaderData(invoice);
            for (SalesInvoiceLine line : invoice.getLines()) {
                lines += line .toString();
                lines += "\n";
                writeFilesItemData(line);

            }
            System.out.println("}" + "\n" + "------------------------------------");
        }
    }

    public static void writeFilesItemData(SalesInvoiceLine x) {
        System.out.println(x);
    }

    public static void writeFilesHeaderData(SalesInvoice x) {
        System.out.println(x);

    }

    
   
}
