package com.example.omokproject2.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.omokproject2.R;
import com.example.omokproject2.api.ApiManager;
import com.example.omokproject2.api.LoginForm;
import com.example.omokproject2.api.ResponseForm;
import com.example.omokproject2.api.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ApiManager apiManager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        apiManager = new ApiManager();

        TextView userIdTextView = findViewById(R.id.login_userId);
        TextView passwordTextView = findViewById(R.id.login_password);
        Button summitButton = findViewById(R.id.login_summit);
        Button registerButton = findViewById(R.id.login_register);

        summitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = userIdTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                LoginForm loginForm = new LoginForm(userId, password);
                if(!loginForm.check()){
                    Toast.makeText(getApplicationContext(), "wrong login form", Toast.LENGTH_LONG).show();
                    return;
                }

                apiManager.getApiService().login(loginForm);
                Call<ResponseForm> call = apiManager.getApiService().login(loginForm);
                call.enqueue(new Callback<ResponseForm>() {
                    @Override
                    public void onResponse(Call<ResponseForm> call, Response<ResponseForm> response){
                        if(response.isSuccessful()){
                            if(response.body().getResult().equals("Success")){
                                TokenManager.getInstance().setToken(response.body().getData());
                                finish();
                            }
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        else{
                            Log.e("Login", "login failed code: " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseForm> call, Throwable t){
                        String requestInfo = call.request().toString();
                        String errorMessage = t.getMessage();
                        Log.e("login", "Request Info: " + requestInfo);
                        Log.e("login", "Network Error: " + errorMessage);
                    }
                });
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
