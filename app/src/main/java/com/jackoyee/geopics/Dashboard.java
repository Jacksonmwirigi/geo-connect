package com.jackoyee.geopics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity  {

    private CardView devics,feedback,dispatch_histo;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        devics=(CardView)findViewById(R.id.devices);
        feedback=(CardView)findViewById(R.id.feedbak);

        
    }

    public void clickEvents(View view){
        switch (view.getId()){
            case R.id.feedbak:

                break;

            case R.id.devices:
                startActivity(new Intent(Dashboard.this,MainActivity.class));
                break;
        }
    }

}
