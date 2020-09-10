package de.leuphana.webmo.foodplan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        //NavButtons
        final Button navButtonplanfoods =  findViewById(R.id.foodplanButton);
        final Button navButtonfoods =  findViewById(R.id.foodsButton);
        final Button navButtonlogin =  findViewById(R.id.loginButton);
        final Button navButtonsettings =  findViewById(R.id.settingsButton);

        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
        if ( sp.getBoolean("logged",false)){
            navButtonlogin.setText(R.string.logout);
        }else{
            navButtonlogin.setText(R.string.menu_login);
        }
        navButtonplanfoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent k = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(k);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        navButtonfoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent k = new Intent(getApplicationContext(), FoodListActivity.class);
                    startActivity(k);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        navButtonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
                if ( sp.getBoolean("logged",false)){
                    sp.edit().putBoolean("logged",false).apply();
                    Toast.makeText(getApplicationContext(), R.string.logout_successfull ,Toast.LENGTH_LONG).show();
                }else{
                    Intent k = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(k);
                }
            }
        });
        navButtonsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent k = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(k);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}