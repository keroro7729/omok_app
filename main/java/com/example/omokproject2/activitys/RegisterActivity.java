package com.example.omokproject2.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omokproject2.R;
import com.example.omokproject2.api.ApiManager;
import com.example.omokproject2.api.RegisterForm;
import com.example.omokproject2.api.ResponseForm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ApiManager apiManager;
    private boolean availableId = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        apiManager = new ApiManager();

        TextView userIdTextView = findViewById(R.id.register_userId);
        TextView passwordTextView = findViewById(R.id.register_password);
        TextView pwdCheckTextView = findViewById(R.id.register_pwdCheck);
        TextView nicknameTextView = findViewById(R.id.register_nickname);
        Button summitButton = findViewById(R.id.register_summitButton);
        Button idCheckButton = findViewById(R.id.register_idCheckButton);

        summitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterForm registerForm = new RegisterForm(
                        userIdTextView.getText().toString(),
                        passwordTextView.getText().toString(),
                        nicknameTextView.getText().toString()
                );
                if(!registerForm.getPassword().equals(pwdCheckTextView.getText().toString())){
                    Toast.makeText(getApplicationContext(), "check your password!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!registerForm.check()){
                    Toast.makeText(getApplicationContext(), "id: 5이상의 영어와 언더바(_) 조합\n"+
                            "password: 8이상의 영어와 특수문자(!@#$%^&*_-+=~`) 조합", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!availableId){
                    Toast.makeText(getApplicationContext(), "check id first!", Toast.LENGTH_LONG).show();
                    return;
                }

                Call<ResponseForm> call = apiManager.getApiService().registerUser(registerForm);
                call.enqueue(new Callback<ResponseForm>() {
                    @Override
                    public void onResponse(Call<ResponseForm> call, Response<ResponseForm> response){
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{
                            Log.e("Register", response.code()+": "+response.body().getMessage());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseForm> call, Throwable t){
                        String requestInfo = call.request().toString();
                        String errorMessage = t.getMessage();
                        Log.e("Register", "Request Info: " + requestInfo);
                        Log.e("Register", "Network Error: " + errorMessage);
                    }
                });
            }
        });

        idCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = userIdTextView.getText().toString();
                if(!new RegisterForm(userId, null, null).checkId()){
                    Toast.makeText(getApplicationContext(), "id: 5이상의 영어와 언더바(_) 조합", Toast.LENGTH_LONG).show();
                    return;
                }

                Call<ResponseForm> call = apiManager.getApiService().checkId(userId);
                call.enqueue(new Callback<ResponseForm>() {
                    @Override
                    public void onResponse(Call<ResponseForm> call, Response<ResponseForm> response){
                        if(response.isSuccessful()){
                            if(response.body().getResult().equals("Success")){
                                availableId = true;
                            }
                            else{
                                availableId = false;
                            }
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        else{
                            Log.e("IdCheck", "code: "+response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseForm> call, Throwable t){
                        String requestInfo = call.request().toString();
                        String errorMessage = t.getMessage();
                        Log.e("IdCheck", "Request Info: " + requestInfo);
                        Log.e("IdCheck", "Network Error: " + errorMessage);
                    }
                });
            }
        });
    }
}