package com.example.omokproject2.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.omokproject2.R;
import com.example.omokproject2.api.ApiManager;
import com.example.omokproject2.api.ResponseForm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ApiManager apiManager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        apiManager = new ApiManager();

        Button helloButton = findViewById(R.id.mainHelloButton);
        Button loginButton = findViewById(R.id.mainLoginButton);
        Button practiceButton = findViewById(R.id.mainPracticeButton);
        Button onlineGameButton = findViewById(R.id.mainOnlineGameButton);

        helloButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseForm> call = apiManager.getApiService().hello();
                call.enqueue(new Callback<ResponseForm>() {
                    @Override
                    public void onResponse(Call<ResponseForm> call, Response<ResponseForm> response){
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        else{
                            Log.e("Hello", "Request Info: " + call.request().toString());
                            Log.e("Hello", "fail code: "+response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseForm> call, Throwable t){
                        Log.e("Hello", "Request Info: " + call.request().toString());
                        Log.e("Hello", "Network Error: " + t.getMessage());
                    }
                });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });

        practiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PracticeActivity.class);
                startActivity(intent);
            }
        });

        onlineGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // request makeMatch()
                // when match start OnlineOmokActivity
            }
        });
    }
}