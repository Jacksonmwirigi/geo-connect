package com.jackoyee.geopics.network;

import com.jackoyee.geopics.accounts.TicketsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface  GetMyTickets {
    @GET("viewTicket/")
    Call<TicketsList>getTickets(@Query("ticket") String ticket,
                                @Query("current_session_key") String current_session_key
    );
}
