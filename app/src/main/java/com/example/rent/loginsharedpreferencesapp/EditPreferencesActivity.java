package com.example.rent.loginsharedpreferencesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.rent.loginsharedpreferencesapp.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditPreferencesActivity extends AppCompatActivity {

    @BindView(R.id.activity_edit_preferences_first_name_edit)
    EditText firstNameEditText;
    @BindView(R.id.activity_edit_preferences_last_name_edit)
    EditText lastNameEditText;
    @BindView(R.id.activity_edit_preferences_password_edit)
    EditText passwordEditText;
    @BindView(R.id.activity_edit_preferences_gender_spinner)
    Spinner genderSpinner;
    @BindView(R.id.activity_edit_preferences_language_spinner)
    Spinner languageSpinner;
    @BindView(R.id.activity_edit_preferences_counry_spinner)
    Spinner countrySpinner;
    @BindView(R.id.activity_edit_preferences_datePicker)
    DatePicker datePicker;
    @BindView(R.id.activity_edit_preferences_save_button)
    Button saveButton;

    AccountsManager accountsManager;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_preferences);

        ButterKnife.bind(this);
        loadAccount();
        fillViewsWithAccountData();

    }

    @OnClick(R.id.activity_edit_preferences_save_button)
    public void onSaveButtonClicked(){
        accountsManager.updateAccount(getAccountFromFields());
        Intent i = new Intent(this,MainActivity.class);
        i.putExtra(LoginActivity.LOGIN,account.getLogin());
        startActivity(i);

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

    private void fillViewsWithAccountData(){
        setSpinners();
        setDatePicker();
        setTextViews();
    }

    private void setSpinners() {
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, RegisterActivity.countryArr);
        countrySpinner.setAdapter(countryAdapter);
        countrySpinner.setSelection(getIndexOfArrayItem(account.getCountry(),RegisterActivity.countryArr));
        ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, RegisterActivity.languageArr);
        languageSpinner.setAdapter(languageAdapter);
        languageSpinner.setSelection(getIndexOfArrayItem(account.getLanguage(),RegisterActivity.languageArr));
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, RegisterActivity.genderArr);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setSelection(getIndexOfArrayItem(account.getGender(),RegisterActivity.genderArr));

    }

    private void setTextViews(){
        firstNameEditText.setText(account.getFirstName());
        lastNameEditText.setText(account.getLastName());
    }

    private void setDatePicker(){

        Date dateOfBirth = DateUtil.getDateFromString(account.getDateOfBirth());
        Calendar c = Calendar.getInstance();
        c.setTime(dateOfBirth);
        datePicker.updateDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
    }

    private int getIndexOfArrayItem(String item,String[] array){

        int result = -1;

        for(int i = 0; i<array.length;i++){
            if(item.equals(array[i])) {
                result=i;
                break;
            }
        }
        return result;
    }

    private Account getAccountFromFields() {

        Account newAccount = null;

        if (areRequiredFieldsFilled() == true) {
            newAccount = new Account();
            newAccount.setFirstName(firstNameEditText.getText().toString());
            newAccount.setLastName(lastNameEditText.getText().toString());
            newAccount.setCountry(countrySpinner.getSelectedItem().toString());
            newAccount.setLanguage(languageSpinner.getSelectedItem().toString());
            newAccount.setGender(genderSpinner.getSelectedItem().toString());
            newAccount.setLogin(account.getLogin());
            newAccount.setPassword(passwordEditText.getText().toString());
            newAccount.setDateOfBirth(DateUtil.getStringDateFromDatePicker(datePicker));

        }
        return newAccount;
    }

    private boolean areRequiredFieldsFilled() {

        return (!(firstNameEditText.getText().toString().isEmpty() &&
                lastNameEditText.getText().toString().isEmpty() &&
                passwordEditText.getText().toString().isEmpty()
        ));
    }
}
