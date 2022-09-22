package com.fwd.invoice.model;

import com.fwd.invoice.view.MainFrame;

import java.util.ArrayList;
import java.util.Date;


public class InvoiceM {
    private int invoice_Number;
    private Date date;
    private String Client;
    private ArrayList<LineM> lines;

    public InvoiceM(int invoice_Number, Date date, String Client) {
        this.invoice_Number = invoice_Number;
        this.date = date;
        this.Client = Client;
    }

    public int getinvoice_Number() {
        return invoice_Number;
    }

    public void setinvoice_Number(int invoice_Number) {
        this.invoice_Number = invoice_Number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClient() {
        return Client;
    }

    public void setClient(String Client) {
        this.Client = Client;
    }

    public ArrayList<LineM> getLines() {
        if(lines == null){
        lines=new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList<LineM> lines) {
        this.lines = lines;
    }

    public double getCost(){
        double Cost = 0.0;
        for(LineM line:getLines()){
        Cost += line.getTotalLine();
        }
        return Cost;
    }
    public String toCSV(){
        return invoice_Number+","+ MainFrame.dFormat.format(date)+","+Client;
    }
}


