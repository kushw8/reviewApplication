package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.Userdata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class submit extends AppCompatActivity {

    EditText nameOfImage;
    ImageView downloaded;
    TextView result_tv;
    Button showReviews;

    FirebaseFirestore objectFirebaseFireStore;
    DocumentReference objectDocumentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        result_tv=findViewById(R.id.s_textview);
        showReviews=findViewById(R.id.s_btn2);


            nameOfImage = findViewById(R.id.s_getName);
            downloaded = findViewById(R.id.s_img);

            objectFirebaseFireStore=FirebaseFirestore.getInstance();

            showReviews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getData();
                }
            });

    }

    public void downloadImage(View view){
        if(!nameOfImage.getText().toString().isEmpty()){

            objectDocumentReference = objectFirebaseFireStore.collection("links")
                    .document(nameOfImage.getText().toString());

            objectDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    String linkOfImage = documentSnapshot.getString("url");
                    Glide.with(submit.this)
                            .load(linkOfImage)
                            .into(downloaded);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(submit.this,"Unsuccess",Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            Toast.makeText(this,"Please Enter an image Name",Toast.LENGTH_LONG).show();
        }

    }

    public void getData(){
        objectFirebaseFireStore.collection("userdata")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            String resultStr="";

                            for(DocumentSnapshot document : task.getResult()){
                                Userdata userdata =document.toObject(Userdata.class);
                                resultStr+=
                                        "Reviews :"+userdata.getU_description();
                            }
                            result_tv.setText(resultStr);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(submit.this,"Error",Toast.LENGTH_LONG).show();
                    }
                });
    }

}