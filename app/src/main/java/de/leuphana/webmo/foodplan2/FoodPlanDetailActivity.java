package de.leuphana.webmo.foodplan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import de.leuphana.webmo.foodplan2.structure.Food;
import de.leuphana.webmo.foodplan2.structure.FoodList;
import de.leuphana.webmo.foodplan2.structure.Type;

public class FoodPlanDetailActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_plan_detail);

        Bundle bundle = getIntent().getExtras();
        int foodId = -1;
        int deletePosition = -1;
        String deleteDay = "undefined";
        if(bundle != null) {
            foodId = bundle.getInt("foodId");
            deleteDay = bundle.getString("day");
            deletePosition = bundle.getInt("position");
        }

        createNavigation(foodId);
        try {
            createFoodDetailView(foodId, deleteDay, deletePosition);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createNavigation(final int foodId) {
        Button navButtonplanFoods = findViewById(R.id.nav_foodplanButton);
        Button navButtonLogin = findViewById(R.id.nav_loginButton);
        Button navButtonFoods = findViewById(R.id.nav_foodsButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        ImageButton navButtonSearch = findViewById((R.id.nav_searchButton));

        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        if (sp.getBoolean("logged", false)) {
            navButtonLogin.setText(R.string.logout);
            deleteButton.setVisibility(View.INVISIBLE);
        } else {
            navButtonLogin.setText(R.string.menu_login);
            deleteButton.setVisibility(View.VISIBLE);
        }

        navButtonplanFoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        navButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                if (sp.getBoolean("logged", false)) {
                    sp.edit().putBoolean("logged", false).apply();
                    Toast.makeText(getApplicationContext(), R.string.logout_successfull, Toast.LENGTH_LONG).show();
                } else {
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
        navButtonSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FoodSearchActivity.class);
                startActivity(i);
            }
        });
    }

    private void createFoodDetailView(int foodId, final String deleteDay, final int deletePosition) throws IOException, ClassNotFoundException {
        final List<Food> foodList = (List<Food>) InternalStorage.readObject(getApplicationContext(), "foodList");
        Food food = new Food(-1, "Undefined", 0.00f, Type.NOTASSIGNED);
        for (Food foodIterator : foodList) {
            if (foodIterator.getId() == foodId) {
                food = foodIterator;
            }
        }
        final TextView foodIdView = findViewById(R.id.foodIdVal);
        foodIdView.setText((String.valueOf(food.getId())));

        final TextView foodNameView = findViewById(R.id.foodNameVal);
        foodNameView.setText(food.getName());

        final TextView foodPriceView = findViewById(R.id.foodPriceVal);
        foodPriceView.setText(String.valueOf(food.getPrice()));

        final TextView foodTypeView = findViewById(R.id.foodTypeVal);
        foodTypeView.setText(food.getType().toString());

        //DetailButtons
        final Button deleteButton = findViewById(R.id.deleteButton);
        final Button backButton = findViewById(R.id.backButton);
        final Button navButtonLogin = findViewById(R.id.nav_loginButton);

        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        if (sp.getBoolean("logged", false)) {
            deleteButton.setVisibility(View.VISIBLE);
            navButtonLogin.setText(R.string.logout);
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
            navButtonLogin.setText(R.string.menu_login);
        }

        navButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                if (sp.getBoolean("logged", false)) {
                    sp.edit().putBoolean("logged", false).apply();
                    Toast.makeText(getApplicationContext(), R.string.logout_successfull, Toast.LENGTH_LONG).show();
                } else {
                    Intent k = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(k);
                }
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent deleteFoodIntent = new Intent(getApplicationContext(), MainActivity.class);
                    Bundle deleteFoodBundle = new Bundle();
                    deleteFoodBundle.putString("deleteDay", deleteDay);
                    deleteFoodBundle.putInt("deletePosition", deletePosition);
                    deleteFoodIntent.putExtras(deleteFoodBundle);
                    startActivity(deleteFoodIntent);
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
}
