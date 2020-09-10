package de.leuphana.webmo.foodplan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.List;

import de.leuphana.webmo.foodplan2.structure.Food;
import de.leuphana.webmo.foodplan2.structure.FoodList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button navButtonfoods =  findViewById(R.id.foodsButton);
        Button navButtonlogin =  findViewById(R.id.loginButton);
        Button navButtonsettings =  findViewById(R.id.settingsButton);

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
                Intent i = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(i);
            }
        });
        navButtonsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(i);
            }
        });

        // fill monday grid with food 1-3
        final GridView gridMondayFoods = findViewById(R.id.gridMonday);
        List<String> foodNameList = FoodList.getFoodList().getFoodNameList();
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