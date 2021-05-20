package com.example.myapplication300;

public class ContactsInfo {

    private  String id;
    private  String name;
    private  String cname;
    private  String shoptype;
    private  String address;
    private  String caddress;
    private  String password;

    public ContactsInfo() {
    }

    public ContactsInfo(String id,String name,String cname, String shoptype, String address,String caddress,String password){
        this.address = address;
        this.caddress = caddress;
        this.cname = cname;
        this.id = id;
        this.name = name;
        this.shoptype = shoptype;
        this.password = password;
    }

    public String getName(){
        return this.name=name;
    }
    public String getshoptype(){
        return this.shoptype=shoptype;
    }
    public String getaddress(){
        return this.address=address;
    }
    public String getId(){
        return this.id=id;
    }
    public String getpassword(){
        return this.password=password;
    }
    public String getcaddress(){
        return this.caddress=caddress;
    }
    public String getcName(){
        return this.cname=cname;
    }
}