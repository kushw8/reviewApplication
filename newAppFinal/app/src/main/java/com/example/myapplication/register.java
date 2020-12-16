package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class register extends AppCompatActivity {

    EditText mFullname,memail,mpassword;
    Button mRegisterBtn;
    FirebaseAuth fAuth;
    TextView mLoginBtn;

    private APIService mAPIService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullname = findViewById(R.id.register_fullName);
        memail = findViewById(R.id.register_email);
        mpassword = findViewById(R.id.register_password);
        mRegisterBtn = findViewById(R.id.register_btn);
        mLoginBtn = findViewById(R.id.register_textView2);

        fAuth = FirebaseAuth.getInstance();

        mAPIService = ApiUtils.getAPIService();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),login.class));
            finish();

        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= memail.getText().toString().trim();
                String password= mpassword.getText().toString().trim();
                String Fullname= mFullname.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    memail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Password is required");
                    return;


                }
                if(password.length() < 6){
                    mpassword.setError("Password must be >=6 Characters");
                    return;
                }

                try{
                    sendPost(Fullname, email,password);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),login.class));
                        }else {
                            Toast.makeText(register.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });
    }

    public void sendPost(String Fullname, String email, String password) throws IOException {

        Call<JsonObject> call = mAPIService.getStringScalar(Fullname, email, password);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Toast.makeText(register.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(register.this, "Error" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
