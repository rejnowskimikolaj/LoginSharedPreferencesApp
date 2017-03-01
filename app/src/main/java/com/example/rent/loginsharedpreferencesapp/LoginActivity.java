package com.example.rent.loginsharedpreferencesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.activity_login_register_button)
    Button registerButton;

    @BindView(R.id.activity_login_loginButton)
    Button loginButton;

    @BindView(R.id.activity_login_login_EditText)
    EditText loginEditText;

    @BindView(R.id.activity_login_password_EditText)
    EditText passwordEditText;

    public static final String LOGIN = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.activity_login_register_button)
    void onRegisterButtonClicked(){
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.activity_login_loginButton)
    void onLoginButtonClicked(){

        if(fieldsAreFilled()){
            String login = loginEditText.getText().toString();
            if(loginExists()){
                AccountsManager manager = new AccountsManager(this);
                if(manager.isPasswordCorrect(passwordEditText.getText().toString(),login)){
                    logIn(login);
                }
                else Toast.makeText(this,"Are you tryin to hack "+login+"?",Toast.LENGTH_SHORT).show();

            }
            else Toast.makeText(this,"login doesn't exist.",Toast.LENGTH_SHORT).show();
        }

    }

    private void logIn(String login) {

        Toast.makeText(this,"Long time no see!",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,MainActivity.class);
        i.putExtra(LOGIN,login);
        startActivity(i);

    }

    private boolean loginExists() {

        String login = loginEditText.getText().toString();

        SharedPreferences sp = getSharedPreferences(AccountsManager.PREFERENCES_ALL_ACCOUNTS,MODE_PRIVATE);
        if(sp.contains(login)) return true;
        else return false;

    }


    private boolean fieldsAreFilled(){
        return (!(loginEditText.getText().toString().isEmpty()&&loginEditText.getText().toString().isEmpty()));
    }
}
