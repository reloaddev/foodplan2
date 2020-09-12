package de.leuphana.webmo.foodplan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText Name = findViewById(R.id.editNameText);
        final EditText Password = findViewById(R.id.editPasswordText);
        Button Login = findViewById(R.id.login_button);

        //Login Logik
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });


        Button navButtonplanfoods =  findViewById(R.id.nav_foodplanButton);
        Button navButtonfoods =  findViewById(R.id.nav_foodsButton);

        //Navbar
        navButtonplanfoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        navButtonfoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(i);
            }
        });

    }

    private void validate (String userName, String userPassword){
        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);

        switch(userName){
            case "jonas":
                if(userPassword.equals("jonas")){
                    Toast.makeText(getApplicationContext(), R.string.login_successfull ,Toast.LENGTH_LONG).show();
                    sp.edit().putBoolean("logged",true).apply();
                    changeFragment();
                }else{
                    Toast.makeText(getApplicationContext(), R.string.login_unsuccessfull, Toast.LENGTH_LONG).show();
                    sp.edit().putBoolean("logged",false).apply();
                }
                break;
            case "henrik":
                if(userPassword.equals("henrik")){
                    Toast.makeText(getApplicationContext(), R.string.login_successfull ,Toast.LENGTH_LONG).show();
                    sp.edit().putBoolean("logged",true).apply();
                    changeFragment();
                }else{
                    Toast.makeText(getApplicationContext(), R.string.login_unsuccessfull, Toast.LENGTH_LONG).show();
                    sp.edit().putBoolean("logged",false).apply();
                }
                break;
            case "max":
                if(userPassword.equals("max")){
                    Toast.makeText(getApplicationContext(), R.string.login_successfull ,Toast.LENGTH_LONG).show();
                    sp.edit().putBoolean("logged",true).apply();
                    changeFragment();
                }else{
                    Toast.makeText(getApplicationContext(), R.string.login_unsuccessfull, Toast.LENGTH_LONG).show();
                    sp.edit().putBoolean("logged",false).apply();
                }
                break;
            case "slotos":
                if(userPassword.equals("slotos")){
                    Toast.makeText(getApplicationContext(), R.string.login_successfull ,Toast.LENGTH_LONG).show();
                    sp.edit().putBoolean("logged",true).apply();
                    changeFragment();
                }else{
                    Toast.makeText(getApplicationContext(), R.string.login_unsuccessfull, Toast.LENGTH_LONG).show();
                    sp.edit().putBoolean("logged",false).apply();
                }
                break;
            default:
                Toast.makeText(getApplicationContext(), R.string.login_unsuccessfull, Toast.LENGTH_LONG).show();
                sp.edit().putBoolean("logged",false).apply();
        }

    }
    private void changeFragment(){
        Intent k = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(k);
    }
}