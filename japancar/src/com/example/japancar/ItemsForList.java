package com.example.japancar;

public class ItemsForList {
	 private String company = "";
     private String model = "";
     private String photo = "";
     private String id = "";
     private String year ="";
     private String price = "";
     private String town = "";
     private String name = "";
    
    
    ItemsForList(){
  	
    }
     public void setName(String name){
    	 this.name = name;
     }
     public String getName(){
         return name;	 
     }
     public void setTown(String town){
    	 this.town = town;
     }
     public String getTown(){
         return town;	
     }
	 public void setCompany(String company) {
	     this.company = company;
	 }

	 public String getCompany() {
	     return company;
	 }
	 public void setPrice(String price){
		 this.price = price;
	 }
	 public String getPrice(){
		 return price;
	 }
	 public void setYear(String year){
		 this.year = year;
	 }
	 public String getYear(){
		 return year;
	 }
	 public void setPhoto(String photo){
		 this.photo = photo;
	 }
	 
	 public String getPhoto(){
		 return photo;
	 }
	 
	 public void setId(String id){
		 this.id = id;
	 }
	 
	 public String getId(){
		 return id;
	 }
	 
	 public void setModel(String model){
		 this.model = model;
	 }
	 
	 public String getModel(){
		 return model;
	 }
}
