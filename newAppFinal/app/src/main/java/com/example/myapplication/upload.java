package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class upload extends AppCompatActivity {

    ImageView uploadImg;
    EditText imgName;

    final int IMAGE_REQUEST=70;
    Uri imageLocationPath;

    StorageReference objectStorageReference;
    FirebaseFirestore objectFirebaseFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        try {
            uploadImg = findViewById(R.id.u_image);
            imgName = findViewById(R.id.img_name);

            objectStorageReference = FirebaseStorage.getInstance().getReference("imageFolder");
            objectFirebaseFireStore = FirebaseFirestore.getInstance();
        } catch (Exception e){

            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    public void selectImage(View view){


            Intent objectIntent = new Intent();
            objectIntent.setType("image/*");

            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent,IMAGE_REQUEST);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageLocationPath=data.getData();
            Bitmap objectBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageLocationPath);

            uploadImg.setImageBitmap(objectBitmap);
        }
    }
        catch (Exception e)
        {
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public  void uploadImage(View view){
        try {
            if (imgName.getText().toString().isEmpty() && imageLocationPath != null) {
                String nameOfImage = imgName.getText().toString() + "." + getExtention(imageLocationPath);
                final StorageReference imageRef = objectStorageReference.child(nameOfImage);

                UploadTask objectUploadTask = imageRef.putFile(imageLocationPath);
                objectUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        return imageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful()){
                            Map<String,String> objectMap = new HashMap<>();
                            objectMap.put("url",task.getResult().toString());

                            objectFirebaseFireStore.collection("links").document(imgName.getText().toString())
                                    .set(objectMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(upload.this,"Inmage is Uploaded",Toast.LENGTH_LONG).show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(upload.this,"fail",Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                        else if(!task.isSuccessful()){
                            Toast.makeText(upload.this,task.getException().toString(),Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }
            else
                {
                Toast.makeText(this,"Please put name for image",Toast.LENGTH_LONG).show();;

            }


        }catch (Exception e){

            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private String getExtention(Uri uri) {

        try {
            ContentResolver objectContentResolver = getContentResolver();
            MimeTypeMap objectMimeTypeMap = MimeTypeMap.getSingleton();

            return objectMimeTypeMap.getExtensionFromMimeType(objectContentResolver.getType(uri));

        }catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    public void moveTo(View view){

        startActivity(new Intent(this,submit.class));

    }


}