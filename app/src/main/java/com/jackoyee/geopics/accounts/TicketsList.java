package com.jackoyee.geopics.accounts;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TicketsList {
//    @SerializedName("data")
//    private Tickets data;
    @SerializedName("data")
    private ArrayList<Tickets>myticktes;
    @SerializedName("request_timestamp")
    private String request_timestamp;
    @SerializedName("response")
    private boolean response;
    @SerializedName("request_id")
    private  String request_id;
    @SerializedName("requester_uid")
    private  String requester_uid;
    @SerializedName("status")
    private  int status;

//    public TicketsResponse() {
//    }

    public TicketsList( String request_timestamp, boolean response, String request_id, String requester_uid, int status, ArrayList<Tickets> myticktes) {
       // this.data = data;
        this.request_timestamp = request_timestamp;
        this.response = response;
        this.request_id = request_id;
        this.requester_uid = requester_uid;
        this.status = status;
        this.myticktes = myticktes;
    }


//    public TicketsList(Tickets data, String request_timestamp, boolean response, String request_id, String requester_uid, int status) {
//        super();
//        this.data = data;
//        this.request_timestamp = request_timestamp;
//        this.response = response;
//        this.request_id = request_id;
//        this.requester_uid = requester_uid;
//        this.status = status;
//    }

//    public Tickets getData() {
//        return data;
//    }

    public String getRequest_timestamp() {
        return request_timestamp;
    }

    public boolean isResponse() {
        return response;
    }

    public String getRequest_id() {
        return request_id;
    }

    public String getRequester_uid() {
        return requester_uid;
    }

    public int getStatus() {
        return status;
    }
//    @SerializedName("tickets")
//    private ArrayList<Tickets>myticktes;

//    public TicketsList(Tickets data, String request_timestamp, boolean response, String request_id, String requester_uid, int status) {
//        super(data, request_timestamp, response, request_id, requester_uid, status);
//    }

    public  ArrayList<Tickets>getMyticktes(){


        return  myticktes;
    }
    public  void setMyticktesArrayList(ArrayList<Tickets>ticketsArrayList){
        this.myticktes=ticketsArrayList;
    }

}
