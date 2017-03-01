package com.example.rent.loginsharedpreferencesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_firstName_textView)
    TextView firstNameTextView;
    @BindView(R.id.activity_main_lastName_textView)
    TextView lastNameTextView;
    @BindView(R.id.activity_main_login_textView)
    TextView loginTextView;
    @BindView(R.id.activity_main_country_textView)
    TextView countryTextView;
    @BindView(R.id.activity_main_language_textView)
    TextView languageTextView;
    @BindView(R.id.activity_main_gender_textView)
    TextView genderTextView;
    @BindView(R.id.activity_main_dateOfBirth_textView)
    TextView dateOfBirthTextView;
    @BindView(R.id.activity_main_editPreferences_button)
    Button editPreferencesButton;

    Account account;
    AccountsManager accountsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadAccount();

        ButterKnife.bind(this);

        loadAccountDataIntoViews();
    }

    private void loadAccount(){
        String login = getIntent().getStringExtra(LoginActivity.LOGIN);
        accountsManager = new AccountsManager(this);
        try {
            account = accountsManager.getAccount(login);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAccountDataIntoViews(){

        loginTextView.setText(account.getLogin());
        firstNameTextView.setText(account.getFirstName());
        lastNameTextView.setText(account.getLastName());
        countryTextView.setText(account.getCountry());
        genderTextView.setText(account.getGender());
        countryTextView.setText(account.getCountry());
        dateOfBirthTextView.setText(account.getDateOfBirth());
        languageTextView.setText(account.getLanguage());
    }

    @OnClick(R.id.activity_main_editPreferences_button)
    public void onEditPreferencesButtonClicked(){

        Intent i = new Intent(this,EditPreferencesActivity.class);
        i.putExtra(LoginActivity.LOGIN,account.getLogin());
        startActivity(i);
    }



}
