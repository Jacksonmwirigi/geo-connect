package com.jackoyee.geopics;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.TintContextWrapper;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jackoyee.geopics.accounts.Tickets;
import com.jackoyee.geopics.accounts.TicketsList;
import com.jackoyee.geopics.accounts.TicketsResponse;
import com.jackoyee.geopics.adapters.TicketListAdapter;
import com.jackoyee.geopics.network.ApiService;
import com.jackoyee.geopics.network.GetMyTickets;
import com.jackoyee.geopics.network.RetroTicketsInstance;
import com.jackoyee.geopics.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Url;


public class ViewTickets extends BaseActivity {

    private RecyclerView mRecyclerView;
   // private RecyclerView.Adapter mAdapter;
    private TicketListAdapter adapter;
    private List<Tickets>myticktes;


    TextView asignertxt,asigneetxt,sttustxt,dispatch_idtxt,req_typetxt,date_asigntxt,clinttxt,phonetxt,residencetxt,comentstxt,
               sched_datetxt,tickotxt,schd_timetxt,lst_updatetxt;

    SharedPreferences preferences;
    public final   String status="assigned";
    public final  String company ="Safaricom";
    public  final String request_type="installation";
    public   String current_session_key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.view_tickets);
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.view_tickets,frameLayout);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(1).setChecked(true);

       preferences=getApplicationContext().getSharedPreferences("myData",MODE_PRIVATE);
       current_session_key = preferences.getString("session_key","");

      Call<TicketsResponse> call =RetrofitClient
              .getNetworkInstance()
              .getApiService()
              .viewMyTickets(current_session_key,status,request_type,company);

//        GetMyTickets service= RetroTicketsInstance.getRetrofitInstance().create(GetMyTickets.class);
//
//        Call<TicketsResponse>call=service.getTickets(ticket,current_session_key);

        Log.d("Url",call.request().url().toString() );

        call.enqueue(new Callback<TicketsResponse>() {
            @Override
            public void onResponse(Call<TicketsResponse> call, Response<TicketsResponse> response) {
              TicketsResponse ticketsResponse1=response.body();

                if (ticketsResponse1.isResponse()) {
                    generateMyTickets(ticketsResponse1.getData());
//                    generateMyTickets(ticketsResponse1.getData());
//                    Log.d("My data here", response.body().toString());
                    //                  mRecyclerView = findViewById(R.id.ticket_recyclerView);
                    //                  adapter = new TicketListAdapter(ticketsResponse1.getData());
                    //                  Log.d("my adapter", String.valueOf(mRecyclerView));
                    //                  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewTickets.this);
                    //                  mRecyclerView.setLayoutManager(layoutManager);
                    //                  mRecyclerView.setAdapter(adapter);


                }
                else {
                    Toast.makeText(ViewTickets.this, "Error occurred or Session Expired!", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<TicketsResponse> call, Throwable t) {

            }
        });


//        Call<TicketsList>call =RetrofitClient
//                .getNetworkInstance()
//                .getApiService()
//                .viewMyTasks(ticket,current_session_key);


//        call.enqueue(new Callback<TicketsList>() {
//            @Override
//            public void onResponse(Call<TicketsList> call, Response<TicketsList> response) {
//
//               // TicketsResponse ticketsResponse=response.body();
//                Log.d("Response be like", String.valueOf(response.body()));
//                generateMyTickets(response.body().getMyticktes());
//
////                mRecyclerView = findViewById(R.id.ticket_recyclerView);
////                adapter = new TicketListAdapter(myticktes);
////
////                Log.d("my adapter", String.valueOf(mRecyclerView));
////                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewTickets.this);
////                mRecyclerView.setLayoutManager(layoutManager);
////                mRecyclerView.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onFailure(Call<TicketsList> call, Throwable t) {
//
//            }
//        });



//      Log.d("URL Call",service.request().url().toString());
//     service.enqueue(new Callback<TicketsResponse>() {
//         @Override
//         public void onResponse(Call<TicketsList> call, Response<TicketsList> response) {
//           //  TicketsResponse ticketsResponse=  response.body();
//
////             TicketsResponse ticketsResponse=response.body();
////             int status=ticketsResponse.getStatus();
////
////
////           //  System.err.println(response.body().getData().toString());
////                             if (status==200) {
////                                // System.out.print(response.body().getData().getASSIGNEE());
////
////                                 Log.d("status",ticketsResponse.getData().getCLIENT_NAME());
////                                 mRecyclerView = findViewById(R.id.ticket_recyclerView);
////                                 adapter = new TicketListAdapter(mytickets,ViewTickets.this);
////                                 RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewTickets.this);
////                                 mRecyclerView.setLayoutManager(layoutManager);
////                                 mRecyclerView.setAdapter(adapter);
////                             }
//             generateNoticeList(response.body().getNoticeArrayList());
//
//
//
//         }
//
//         @Override
//         public void onFailure(Call<TicketsResponse> call, Throwable t) {
//
//         }
//     });

    }



    private   void  generateMyTickets(List<Tickets> tickets){

        mRecyclerView = findViewById(R.id.ticket_recyclerView);
        adapter = new TicketListAdapter(tickets);
        Log.d("my adapter", String.valueOf(mRecyclerView));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

    }


//    public  void viewMyTasks(){
//
//        mRecyclerView = findViewById(R.id.ticket_recyclerView);
//        adapter = new TicketListAdapter(mytickets);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setAdapter(adapter);
//    }


//    public  void viewMyTasks(){
//
//        Call<TicketsResponse> call= RetrofitClient.getNetworkInstance()
//                .getApiService()
//                .viewMyTasks(ticket,current_session_key);
//
//        call.enqueue(new Callback<TicketsResponse>() {
//            @Override
//            public void onResponse(Call<TicketsResponse> call, Response<TicketsResponse> response) {
//                Log.d("onResponse", response.message());
//                TicketsResponse ticketsResponse =  response.body();
//
//                if (!ticketsResponse.isResponse()) {
//                    String dipatch_id = ticketsResponse.getData().getDISPATCH_ID();
//                    dispatch_idtxt.setText(dipatch_id);
//                    String tickonumber = ticketsResponse.getData().getTICKET();
//                    tickotxt.setText(tickonumber);
//                    String requestType = ticketsResponse.getData().getREQUEST_TYPE();
//                    req_typetxt.setText(requestType);
//                    String assignee = ticketsResponse.getData().getASSIGNEE();
//                    asigneetxt.setText(assignee);
//                    String asignmentDate = ticketsResponse.getData().getASSIGNMENT_DATE();
//                    date_asigntxt.setText(asignmentDate);
//                    String assigner = ticketsResponse.getData().getASSIGNER();
//                    asignertxt.setText(assigner);
//                    String lastupdate = ticketsResponse.getData().getLAST_UPDATE();
//                    lst_updatetxt.setText(lastupdate);
//                    String status = ticketsResponse.getData().getSTATUS();
//                    sttustxt.setText(status);
//                    String comment = ticketsResponse.getData().getCOMMENTS();
//                    comentstxt.setText(comment);
//                    String scheuleTime = ticketsResponse.getData().getSCHEDULED_TIME_SLOT();
//                    schd_timetxt.setText(scheuleTime);
//                    String client_name = ticketsResponse.getData().getCLIENT_NAME();
//                    clinttxt.setText(client_name);
//                    String residence_clt = ticketsResponse.getData().getCLIENT_RESIDENCE();
//                    residencetxt.setText(residence_clt);
//                    String client_phone = ticketsResponse.getData().getCLIENT_PHONE();
//                    phonetxt.setText(client_phone);
//                    String scheduledate = ticketsResponse.getData().getSCHEDULED_DATE();
//                    sched_datetxt.setText(scheduledate);
//
//                }
//
//
//
////                mLayoutManager=new LinearLayoutManager(ViewTickets.this);
////                mRecyclerView.setLayoutManager(mLayoutManager);
////                mRecyclerView=(RecyclerView)findViewById(R.id.ticket_recyclerView);
////               // mRecyclerView.setHasFixedSize(true);
////                mAdapter = new TicketListAdapter(ticketList,this);
////                mRecyclerView.setAdapter(mAdapter);
//
//            }
//
//            @Override
//            public void onFailure(Call<TicketsResponse> call, Throwable t) {
//
//            }
//        });

   // }

 }
