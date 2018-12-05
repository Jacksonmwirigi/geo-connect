package com.jackoyee.geopics;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jackoyee.geopics.accounts.LoginResponse;
import com.jackoyee.geopics.network.RetrofitClient;

import retrofit2.Call;


public class BaseActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        TextView navview=(TextView)findViewById(R.id.nav_header_textView) ;

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String email=preferences.getString("user_email","defaultValue");

//         navview.setText(email);
//         navview.setVisibility(View.VISIBLE);

        preferences =getApplication().getSharedPreferences("myData",MODE_PRIVATE);
        String email_new=preferences.getString("user_email","");
        String session_key=preferences.getString("session_key","");

//       if (!session_key.isEmpty()){
//           navview.setText(email_new);
//
//       }
//       else{
//           Toast.makeText(this, "Not logged In", Toast.LENGTH_SHORT).show();
//           }

//

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                final String appPackageName = getPackageName();

                switch (item.getItemId()) {

                    case R.id.board:
                        Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(loginIntent);
//                        finish();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.instals:
                        Intent dash = new Intent(getApplicationContext(), InstallationActivity.class);
                        startActivity(dash);
//                        finish();
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.customer_feedback:
                        Intent cutomerIntent = new Intent(getApplicationContext(), Feedback.class);
                        startActivity(cutomerIntent);
//                        finish();
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.log_out:

                        startActivity(new Intent(getApplicationContext(),LogOut.class));
//                        finish();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.ticket:
                        Intent ticketIntent = new Intent(getApplicationContext(), ViewTickets.class);
                        startActivity(ticketIntent);
//                        finish();
                        drawerLayout.closeDrawers();
                        break;

                }
                return false;
            }
        });

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}