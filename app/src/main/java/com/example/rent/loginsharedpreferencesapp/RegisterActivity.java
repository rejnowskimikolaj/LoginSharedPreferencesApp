package com.example.rent.loginsharedpreferencesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rent.loginsharedpreferencesapp.utils.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.first_name_edit)
    EditText firstNameEditText;
    @BindView(R.id.last_name_edit)
    EditText lastNameEditText;
    @BindView(R.id.login_edit)
    EditText loginEditText;
    @BindView(R.id.password_edit)
    EditText passwordEditText;
    @BindView(R.id.gender_spinner)
    Spinner genderSpinner;
    @BindView(R.id.language_spinner)
    Spinner languageSpinner;
    @BindView(R.id.counry_spinner)
    Spinner countrySpinner;
    @BindView(R.id.save_button)
    Button saveButton;
    @BindView(R.id.datePicker)
    DatePicker datePicker;

    public static final String[] languageArr = {"polish", "polglish", "english"};
    public static final String[] countryArr = {"Poland", "England", "Polgland"};
    public static final String[] genderArr = {"male", "female", "programmer"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setSpinners();

    }

    private void setSpinners() {
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countryArr);
        countrySpinner.setAdapter(countryAdapter);
        ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languageArr);
        languageSpinner.setAdapter(languageAdapter);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderArr);
        genderSpinner.setAdapter(genderAdapter);
    }

    @OnClick(R.id.save_button)
    void onSaveButtonClicked() {

        Account account = getAccountFromFields();
        if(account==null) Toast.makeText(this,"Make sure u fill names, login and password!!",Toast.LENGTH_SHORT).show();
        else{
            saveAccount(account);
        }
    }

    private void saveAccount(Account account){
        try {
            AccountsManager manager = new AccountsManager(this);
            manager.saveAccount(account,true);
            onAccountCreated();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"Login already exists!!!!",Toast.LENGTH_SHORT).show();
        }
    }

    private void onAccountCreated(){
        Toast.makeText(this,"Account created. Your soul is mine.",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }

    private boolean areRequiredFieldsFilled() {

        return (!(firstNameEditText.getText().toString().isEmpty() &&
                lastNameEditText.getText().toString().isEmpty() &&
                loginEditText.getText().toString().isEmpty() &&
                passwordEditText.getText().toString().isEmpty()
        ));
    }


    private Account getAccountFromFields() {

        Account account = null;

        if (areRequiredFieldsFilled() == true) {
            account = new Account();
            account.setFirstName(firstNameEditText.getText().toString());
            account.setLastName(lastNameEditText.getText().toString());
            account.setLogin(loginEditText.getText().toString());
            account.setCountry(countrySpinner.getSelectedItem().toString());
            account.setLanguage(languageSpinner.getSelectedItem().toString());
            account.setGender(genderSpinner.getSelectedItem().toString());
            account.setLogin(loginEditText.getText().toString());
            account.setPassword(passwordEditText.getText().toString());
            account.setDateOfBirth(DateUtil.getStringDateFromDatePicker(datePicker));

        }
        return account;
    }
}
