package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.api_interfaces.JsonPlaceHolderApi;
import com.example.myapplication.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroPOST extends AppCompatActivity {

    private TextView textViewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro_p_o_s_t);

        textViewResult = findViewById(R.id.text_view_result2);

        createPost();
    }

    void createPost(){

        Post post = new Post(15,"ReviewApp","ReviewApp Body Data");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Post> call = jsonPlaceHolderApi.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful()){
                    textViewResult.setText("code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "id: " + post.getId() + "\n";
                content += "User ID: " + post.getUserId() + "\n";
                content += "Title: " + post.getTitle() + "\n";
                content += "Description: " + post.getDescription() + "\n";

                textViewResult.append(content);


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());


            }
        });


    }
    
}