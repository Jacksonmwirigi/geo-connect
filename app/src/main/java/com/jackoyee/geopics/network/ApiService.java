package com.jackoyee.geopics.network;

import com.jackoyee.geopics.accounts.LoginResponse;
import com.jackoyee.geopics.accounts.TicketsList;
import com.jackoyee.geopics.accounts.TicketsResponse;
import com.jackoyee.geopics.accounts.UploadsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

//    String URL = "http://172.29.200.28/dp/rest/";

//    @GET("accounts/{accountId}")
//    Call<Data> getAccountInfo(@Path("accountId") String accountId);
//
//    Call<Data> getAccountInfo();
    // Call<Data> getAccountInfo();

    @FormUrlEncoded
    @POST("safOauth")
    Call<LoginResponse>userLogin(
            @Field("username") String username,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("supTicketUpdate")
          Call<UploadsResponse> uploadData(
            @Field("ticket") String ticket,
            @Field("current_session_key") String current_session_key,
            @Field("client_loc_lat") String client_loc_lat
 );

    @GET("viewMyTickets/2567a5ec9705eb7ac2c984033e06189d/{current_session_key}")
    Call<TicketsResponse> viewMyTickets( @Path("current_session_key") String current_session_key,
                                        @Query("status") String status,
                                        @Query("request_type") String request_type,
                                        @Query("company") String company);

    //  Call<UploadsResponse> uploadData(String status, String client_loc_long, String client_loc_lat);

    // Call<LoginResponse> uploadData(String status, String client_loc_long, String client_loc_lat);
}

//    @FormUrlEncoded
//    @POST("supTicketUpdate")
//    Call<UploadsResponse>uploadData(
//            @Field("ticket") String ticket,
//            @Field("current_session_key")String current_session_key,
//            @Field("client_loc_lat") String client_loc_lat,
//            @Field("client_loc_long") String client_loc_long,
//            @Field("status") String status,
//            @Field("comment") String comment
//    );

//    @Field("DISPATCH_ID") String DISPATCH_ID,
//    @Field("TICKET") String TICKET,
//    @Field("REQUEST_TYPE") String REQUEST_TYPE,
//    @Field("ASSIGNEE") String ASSIGNEE,
//    @Field("ASSIGNMENT_DATE") String ASSIGNMENT_DATE,
//    @Field("ASSIGNER") String ASSIGNER,
//    @Field("LAST_UPDATE") String LAST_UPDATE,
//    @Field("STATUS") String STATUS,
//    @Field("COMMENTS") String COMMENTS,
//    @Field("SCHEDULED_TIME_SLOT") String SCHEDULED_TIME_SLOT,
//    @Field("CLIENT_NAME") String CLIENT_NAME,
//    @Field("CLIENT_RESIDENCE") String CLIENT_RESIDENCE,
//    @Field("CLIENT_PHONE") String CLIENT_PHONE,
//    @Field("SCHEDULED_DATE") String SCHEDULED_DATE