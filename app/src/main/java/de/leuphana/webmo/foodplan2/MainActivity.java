package de.leuphana.webmo.foodplan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.GridView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.List;

import de.leuphana.webmo.foodplan2.structure.Food;
import de.leuphana.webmo.foodplan2.structure.FoodList;

import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FoodList foodPlanList = FoodList.getFoodList();

        try {
            InternalStorage.writeObject(this, "foodPlanList", foodPlanList);
        } catch (IOException e) {
            e.printStackTrace();
        }




        createNavigation();
        fillFoodPlan();
    }

    private void createNavigation(){

        Button navButtonfoods =  findViewById(R.id.foodsButton);
        Button navButtonlogin =  findViewById(R.id.loginButton);
        Button navButtonsettings =  findViewById(R.id.settingsButton);

        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);

        if ( sp.getBoolean("logged",false)){
            navButtonlogin.setText(R.string.logout);
        }else{
            navButtonlogin.setText(R.string.menu_login);
        }

        navButtonfoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(i);
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
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });
    }

    private void fillFoodPlan(){
        FoodList foodList = null;
        try {
            foodList = (FoodList) InternalStorage.readObject(this, "foodPlanList");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "fillFoodPlan: " + foodList);
            //foodList = FoodList.getFoodList();
        }
        // fill monday grid with food 1-3
        final GridView gridMondayFoods = findViewById(R.id.gridMonday);
        List<String> foodNameList = foodList.getFoodNameList();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(0, 3));
        gridMondayFoods.setAdapter(adapter);

        // fill tuesday grid with next three foods 4-6
        final GridView gridTuesdayFoods = findViewById(R.id.gridTuesday);
        adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(3, 6));
        gridTuesdayFoods.setAdapter(adapter);

        // fill wednesday grid with next three foods 7-9
        final GridView gridWednesdayFoods = findViewById(R.id.gridWednesday);
        adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(6, 9));
        gridWednesdayFoods.setAdapter(adapter);

        // fill wednesday grid with next three foods 10-12
        final GridView gridThursdayFoods = findViewById(R.id.gridThursday);
        adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(9, 12));
        gridThursdayFoods.setAdapter(adapter);

        // fill wednesday grid with next three foods 13-15
        final GridView gridFridayFoods = findViewById(R.id.gridFriday);
        adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(12, 15));
        gridFridayFoods.setAdapter(adapter);
    }
}