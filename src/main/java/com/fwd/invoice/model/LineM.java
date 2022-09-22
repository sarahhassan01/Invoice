package com.fwd.invoice.model;

public class LineM {

    private String Product;
    private double Value;
    private int Quantity;
    private InvoiceM invoice;
    
    public LineM(String Product, double Value, int Quantity, InvoiceM invoice) {
        this.Product = Product;
        this.Value = Value;
        this.Quantity = Quantity;
        this.invoice = invoice;
    }
 

    public String getProduct() {
        return Product;
    }

    public void setProduct(String Product) {
        this.Product = Product;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double Value) {
        this.Value = Value;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public InvoiceM getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceM invoice) {
        this.invoice = invoice;
    }

    public double getTotalLine() {
        return getQuantity() * getValue();
    }
    public String toCSV(){
        return invoice.getinvoice_Number()+","+Product+","+Value+","+Quantity;
    }
}
