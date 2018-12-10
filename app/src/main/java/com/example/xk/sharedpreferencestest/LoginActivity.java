package com.example.xk.sharedpreferencestest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private EditText accountEdit;

    private EditText passwordEdit;

    private Button login;

    private CheckBox rememberAccount;

    private CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        rememberAccount = (CheckBox) findViewById(R.id.remember_account);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        login = (Button) findViewById(R.id.login);
        boolean isRememberPass = pref.getBoolean("remember_password", false);
        boolean isRememberAccount = pref.getBoolean("remember_account", false);
        //记住密码
        if(isRememberPass){//将账号密码从存储里，读取设置到文本框中
            String password = pref.getString("password", "");
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }
        //记住账号
        if(isRememberAccount){
            String account = pref.getString("account", "");
            accountEdit.setText(account);
            rememberAccount.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if(account.equals("admin") && password.equals("123456")){
                    editor = pref.edit();
                    if(rememberAccount.isChecked()){//检查账号复选框是否被选中
                        editor.putBoolean("remember_account", true);
                        editor.putString("account", account);
                        if(rememberPass.isChecked()){//检查密码复选框是否被选中
                            editor.putBoolean("remember_password", true);
                            editor.putString("password", password);
                        }else {
                            editor.putBoolean("remember_password", false);
                            editor.putString("password", "");
                        }
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
