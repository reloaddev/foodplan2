package de.leuphana.webmo.foodplan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.leuphana.webmo.foodplan2.structure.Food;

import static android.R.layout.simple_list_item_1;

public class FoodSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);

        try {
            List<Food> foodList = (List<Food>) InternalStorage.readObject(getApplicationContext(), "foodList");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        createNavigation();
        try {
            fillFoodList();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createNavigation() {
        Button navButtonplanfoods =  findViewById(R.id.nav_foodplanButton);
        Button navButtonlogin =  findViewById(R.id.nav_loginButton);
        Button navButtonFoods = findViewById(R.id.nav_foodsButton);
        Button backButton = findViewById(R.id.backButton);

        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
        if ( sp.getBoolean("logged",false)){
            navButtonlogin.setText(R.string.logout);
        }else{
            navButtonlogin.setText(R.string.menu_login);
        }

        navButtonplanfoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
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
        navButtonFoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent k = new Intent(getApplicationContext(), FoodListActivity.class);
                    startActivity(k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent k = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fillFoodList() throws IOException, ClassNotFoundException {
        final List<Food> foodList = (List<Food>) InternalStorage.readObject(this, "foodList");
        List<String> foodNameList = new ArrayList<String>();
        for (Food food : foodList) {
            foodNameList.add(food.getName());
        }

        final ListView listView = (ListView) findViewById(R.id.foodList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getApplicationContext(), simple_list_item_1, foodNameList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String foodName = (String) listView.getItemAtPosition(position);
                List<Food> foodPlanList = null;
                try {
                    foodPlanList = (List<Food>) InternalStorage.readObject(getApplicationContext(), "foodPlanList");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                float i = 0;
                boolean found = false;
                for(Food food: foodPlanList) {
                    if(foodName.equals(food.getName())){
                        found = true;
                       break;
                    }
                    i++;
                }
                if(found==false){
                    i = 0;
                }

                i = i/15;

                // Show when the food will be available the next time
                // Therefore: find position in foodplan and fetch the appropriate week
                if(i>0 && i<=1) {
                    Toast.makeText(getApplicationContext(), R.string.txt_currentWeek, Toast.LENGTH_LONG).show();
                } else if (i>1 && i<=2) {
                    Toast.makeText(getApplicationContext(), R.string.txt_nextWeek, Toast.LENGTH_LONG).show();
                } else if (i>2 && i<=3) {
                    Toast.makeText(getApplicationContext(), R.string.txt_secondWeek, Toast.LENGTH_LONG).show();
                } else if (i>3 && i<=4) {
                    Toast.makeText(getApplicationContext(), R.string.thirdWeek, Toast.LENGTH_LONG).show();
                } else if (i>4 && i<=5) {
                    Toast.makeText(getApplicationContext(), R.string.txt_fourthWeek, Toast.LENGTH_LONG).show();
                } else if (i>5 && i<=6) {
                    Toast.makeText(getApplicationContext(), R.string.txt_fithWeek, Toast.LENGTH_LONG).show();
                } else if (i>6 && i<=7) {
                    Toast.makeText(getApplicationContext(), R.string.txt_sixthWeek, Toast.LENGTH_LONG).show();
                } else if (i>7 && i<=8) {
                    Toast.makeText(getApplicationContext(), R.string.txt_seventhWeek, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.txt_noWeek, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}