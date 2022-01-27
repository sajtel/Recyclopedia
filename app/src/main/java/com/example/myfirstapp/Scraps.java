package com.example.myfirstapp;


// This class will help to parameterize our input fields in a concrete way in order to place in the arraylist, then to display the Scraps.
//Creating Scrap variable type to fill arraylist
public class Scraps{

    // Initializing fields : scrap ID (row), name, date posted, student id
    private int id;
    private String name;
    private String dateFormatted;
    private String identification;
    private String scrapDescription;
    private String timestamp;

    public Scraps(){}
    public Scraps(int id, String name, String dateFormatted, String identification, String scrapDescription, String timestamp) {
        this.id = id;
        this.name = name;
        this.dateFormatted = dateFormatted;
        this.identification = identification;
        this.scrapDescription = scrapDescription;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    } //Returns row ID

    public void setId(int id) {
        this.id = id;
    } //Returns ID on edit

    public String getName() {
        return name;
    } //Returns scrap name

    public void setName(String name) {
        this.name = name;
    } //Returns scrap name on edit

    public String getDateFormatted() {
        return dateFormatted;
    } //Returns date of posting

    public void setDateFormatted(String dateFormatted) {
        this.dateFormatted = dateFormatted; //Returns data of reformatted scrap
    }

    public String getIdentification() {
        return identification;
    } //Returns SFU Student or faculty ID - No access do SFU's API

    public void setIdentification(String identification) {
        this.identification = identification; //Returns iput ID in the postScrap Activity
    }

    //Returns inputed scrap description
    public String getScrapDescription() {
        return scrapDescription;
    }

    public void setScrapDescription(String scrapDescription) {
        this.scrapDescription = scrapDescription;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
