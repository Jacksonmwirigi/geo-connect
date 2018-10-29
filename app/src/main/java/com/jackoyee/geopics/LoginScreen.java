package com.jackoyee.geopics;

import android.content.Intent;
import android.graphics.drawable.DrawableWrapper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "LoginScreen";

   private Button btn ;
   private TextView link;
   private   EditText pass,email;

   private DatabaseReference databaseReference;
   private FirebaseAuth mfirebaseAuth;


    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;


//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mfirebaseAuth.getCurrentUser();
//        updateUI(currentUser);
//     }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_window);


//        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar=getSupportActionBar();
//        assert actionBar != null;
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//
//        drawer=(DrawerLayout)findViewById(R.id.drawer_layout);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        link=(TextView)findViewById(R.id.reLink);
        email=(EditText)findViewById(R.id.emailAcc);
        pass=(EditText)findViewById(R.id.EPass);

        btn=(Button)findViewById(R.id.LognBtn);

        mfirebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");


    }

    public void onclickSignIn(View view) {

      switch (view.getId()){
          case R.id.LognBtn:

              String email_Str=email.getText().toString().trim();
              String pass_Str=pass.getText().toString().trim();

              if (!TextUtils.isEmpty(email_Str)&&!TextUtils.isEmpty(pass_Str)){

                  mfirebaseAuth.signInWithEmailAndPassword(email_Str,pass_Str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {

                          if (task.isSuccessful()){

                              checkIfUserExists();
                          }
                          else {
                              Toast.makeText(LoginScreen.this,"Authentication Failed ",Toast.LENGTH_SHORT).show();
                           }
                       }

                  })

//                          .addOnFailureListener(new OnFailureListener() {
//                      @Override
//                      public void onFailure(@NonNull Exception e) {
//
//                          Toast.makeText(LoginScreen.this,"Authentication Failed ",Toast.LENGTH_SHORT).show();
//                      }
//                  })
                  ;

              }
              else {
                  Toast.makeText(LoginScreen.this,"Provide your credentials",Toast.LENGTH_SHORT).show();
                  }

              break;

        case R.id.reLink:

           startActivity(new Intent(LoginScreen.this,Registration.class));
                break;


        }
    }

    private void checkIfUserExists() {

        final String user_id=mfirebaseAuth.getCurrentUser().getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id)){
                    startActivity(new Intent(LoginScreen.this,Dashboard.class));
                }
                else {
                    Toast.makeText(LoginScreen.this,"Sorry! User Doesn't Exist or Account not yet Activated",Toast.LENGTH_SHORT).show();
                     }
               }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
          });

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.isDrawerOpen(GravityCompat.START);
        }
        else {
            super.onBackPressed();
             }
     }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.signmeup) {
            startActivity(new Intent(LoginScreen.this,Registration.class));
        }
        else if (id == R.id.upload) {
            startActivity(new Intent(LoginScreen.this,MainActivity.class));

        } else if (id == R.id.checkConnection) {

        } else if (id == R.id.nav_share) {

        }
        else if (id == R.id.nav_send) {

        }else if (id == R.id.board) {
            startActivity(new Intent(LoginScreen.this,Dashboard.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



