package com.jackoyee.geopics;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private static final String TAG = "RegistrationScreen";
    Button btn ;
    TextView link;
    EditText pass,email;
    DatabaseReference dbReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);



        email=(EditText)findViewById(R.id.etEmail);
        pass=(EditText)findViewById(R.id.etPassWord);

        btn=(Button)findViewById(R.id.regBtn);

        firebaseAuth= FirebaseAuth.getInstance();
        dbReference= FirebaseDatabase.getInstance().getReference().child("Users");




    }
    public void onclickSignIn(View view) {


                final String email_Str=email.getText().toString().trim();
                String pass_Str=pass.getText().toString().trim();


        if (pass_Str.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
                if (!TextUtils.isEmpty(email_Str)&& !TextUtils.isEmpty(pass_Str)){

                    firebaseAuth.createUserWithEmailAndPassword(email_Str,pass_Str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){

//                                String user_id=firebaseAuth.getCurrentUser().getUid();
//                                DatabaseReference current_user=dbReference.child(user_id);
//                                current_user.child("Name").setValue(email_Str);


                                Toast.makeText(Registration.this,"Account created Successfully ",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registration.this,LoginScreen.class));

                            }
                            else {
                                Toast.makeText(Registration.this,"SORRY!! Registration Failed ",Toast.LENGTH_LONG).show();

                              }

                            String user_id=firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference current_user=dbReference.child(user_id);
                            current_user.child("Name").setValue(email_Str);

                            Log.i(TAG,"user Wetu"+user_id);
                        }
                    });

                }
                else {
                    Toast.makeText(Registration.this,"Fields cannot be empty ",Toast.LENGTH_SHORT).show();
                    }



//            startActivity(new Intent(LoginScreen.this,MainActivity.class));



        }
    }

