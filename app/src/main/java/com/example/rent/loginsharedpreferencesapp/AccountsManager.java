package com.example.rent.loginsharedpreferencesapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by RENT on 2017-02-15.
 */

public class AccountsManager {

    public static final String PREFERENCES_ALL_ACCOUNTS = "all accounts";
    public static final String PREFERENCES_USER_DATA = "user data";
    public static final String USER_DATA_SEPARATOR= "%20";

//    public static final String PREFERENCE_FIRST_NAME = "key first name";
//    public static final String PREFERENCE_LAST_NAME="key last name";
//    public static final String PREFERENCE_COUNTRY="key country";
//    public static final String PREFERENCE_LANGUAGE="key language";
//    public static final String PREFERENCE_LOGIN="key login";
//    public static final String PREFERENCE_PASSWORD="key password";
//    public static final String PREFERENCE_GENDER="key gender";
//    public static final String PREFERENCE_DATE_OF_BIRTH="key dateOfBirth";


    SharedPreferences allAccountsPreferences;
    SharedPreferences accountsDataPreferences;
    Context context;

    public AccountsManager(Context context) {

        this.context = context;
        allAccountsPreferences = context.getSharedPreferences(PREFERENCES_ALL_ACCOUNTS,Context.MODE_PRIVATE);
        accountsDataPreferences = context.getSharedPreferences(PREFERENCES_USER_DATA,Context.MODE_PRIVATE);

    }

    public boolean isPasswordCorrect(String password,String login){

       return getMD5EncryptedString(password).equals(allAccountsPreferences.getString(login,"")) ;

    }

    private boolean loginExists(String login){
        return (!allAccountsPreferences.getString(login,"").isEmpty());
    }

    public void updateAccount(Account account)  {

        try {
            saveAccount(account,false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveAccount(Account account,boolean isNew) throws Exception {

        if(loginExists(account.getLogin())&&isNew) throw new Exception("Login already exists!");
        String userDataInString;
        StringBuilder builder = new StringBuilder();
        builder.append(account.getFirstName()+USER_DATA_SEPARATOR);
        builder.append(account.getLastName()+USER_DATA_SEPARATOR);
        builder.append(account.getCountry()+USER_DATA_SEPARATOR);
        builder.append(account.getLanguage()+USER_DATA_SEPARATOR);
        builder.append(getMD5EncryptedString(account.getLogin())+USER_DATA_SEPARATOR);
        builder.append(account.getPassword()+USER_DATA_SEPARATOR);
        builder.append(account.getDateOfBirth()+USER_DATA_SEPARATOR);
        builder.append(account.getGender());
        userDataInString = builder.toString();
        SharedPreferences.Editor editor = accountsDataPreferences.edit();

        editor.putString(getMD5EncryptedString(account.getLogin()),userDataInString);

        addAccountToAllAccounts(account);

        editor.apply();
    }

    private void addAccountToAllAccounts(Account account) {

        SharedPreferences.Editor editor = allAccountsPreferences.edit();

        editor.putString(account.getLogin(),getMD5EncryptedString(account.getPassword()));
        editor.apply();
    }

    public Account getAccount(String login) throws Exception {

        String userDataString = accountsDataPreferences.getString(getMD5EncryptedString(login),"");

        String [] userDataArray = userDataString.split(USER_DATA_SEPARATOR);

        String firstName = userDataArray[0];
        String lastName = userDataArray[1];
        String country = userDataArray[2];
        String language = userDataArray[3];
        String password = userDataArray[5];
        String dateOfBirth = userDataArray[6];
        String gender = userDataArray[7];

        return new Account(firstName,lastName,country,language,login,password,gender,dateOfBirth);
    }

    public static String getMD5EncryptedString(String encTarget){
        MessageDigest mdEnc = null;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception while encrypting to md5");
            e.printStackTrace();
        } // Encryption algorithm
        mdEnc.update(encTarget.getBytes(), 0, encTarget.length());
        String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
        while ( md5.length() < 32 ) {
            md5 = "0"+md5;
        }
        return md5;
    }
}
