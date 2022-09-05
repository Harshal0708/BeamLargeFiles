package com.example.android.beamlargefiles.models;

public class Contact {
    public int _id;
    public int subhasad_code_no;
    public String name1;
    public String address1;
    public int  amount;
    public String comment;

    public int getSubhasad_code_no() {
        return subhasad_code_no;
    }

    public void setSubhasad_code_no(int subhasad_code_no) {
        this.subhasad_code_no = subhasad_code_no;
    }

    public int totalCollectrdAmount = 0;
    public String lastpdateDate;

    public Contact(){   }
    public Contact(int id, int amount, String comment){
        this._id = id;
        this.amount = amount;
        this.comment = comment;
    }

    public Contact(int _id,int subhasad_code_no, String name1, String address1, int amount, String comment, int totalCollectrdAmount, String lastpdateDate) {
        this._id = _id;
        this.subhasad_code_no = subhasad_code_no;
        this.name1 = name1;
        this.address1 = address1;
        this.amount = amount;
        this.comment = comment;
        this.totalCollectrdAmount = totalCollectrdAmount;
        this.lastpdateDate = lastpdateDate;
    }

    public int getTotalCollectrdAmount() {
        return totalCollectrdAmount;
    }

    public void setTotalCollectrdAmount(int totalCollectrdAmount) {
        this.totalCollectrdAmount = totalCollectrdAmount;
    }

    public String getLastpdateDate() {
        return lastpdateDate;
    }

    public void setLastpdateDate(String lastpdateDate) {
        this.lastpdateDate = lastpdateDate;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this.name1;
    }

    public void setName(String name){
        this.name1 = name;
    }

    public String getAddress() {
        return address1;
    }

    public void setAddress(String address) {
        this.address1 = address;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "_id=" + _id +
                ", name1='" + name1 + '\'' +
                ", address1='" + address1 + '\'' +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                '}';
    }
}