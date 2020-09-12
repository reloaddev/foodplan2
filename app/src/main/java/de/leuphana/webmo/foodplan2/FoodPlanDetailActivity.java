package de.leuphana.webmo.foodplan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import de.leuphana.webmo.foodplan2.structure.Food;
import de.leuphana.webmo.foodplan2.structure.FoodList;
import de.leuphana.webmo.foodplan2.structure.Type;

public class FoodPlanDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_plan_detail);

        Bundle bundle = getIntent().getExtras();
        int foodId = -1;
        if(bundle != null) {
            foodId = bundle.getInt("foodId");
        }

        createNavigation();
        createFoodDetailView(foodId);

    }

    private void createNavigation() {
        Button navButtonplanFoods = findViewById(R.id.nav_foodplanButton);
        Button navButtonLogin = findViewById(R.id.nav_loginButton);
        Button navButtonFoods = findViewById(R.id.nav_foodsButton);

        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        if (sp.getBoolean("logged", false)) {
            navButtonLogin.setText(R.string.logout);
        } else {
            navButtonLogin.setText(R.string.menu_login);
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
    }

    private void createFoodDetailView(int foodId) {
        List<Food> foodList = FoodList.getFoodList().getFoodArrayList();
        Food food = new Food(-1, "Undefined", 0.00f, Type.NOTASSIGNED);
        for (Food foodIterator : foodList) {
            if (foodIterator.getId() == foodId) {
                food = foodIterator;
            }
        }
        final TextView foodIdView = findViewById(R.id.foodIdVal);
        foodIdView.setText((String.valueOf(food.getId())));

        final EditText foodNameView = findViewById(R.id.foodNameVal);
        foodNameView.setText(food.getName());

        final EditText foodPriceView = findViewById(R.id.foodPriceVal);
        foodPriceView.setText(String.valueOf(food.getPrice()));

        final EditText foodTypeView = findViewById(R.id.foodTypeVal);
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
                int deleteId = Integer.parseInt(foodIdView.getText().toString());
                ArrayList<Food> foodArrayList = FoodList.getFoodList().getFoodArrayList();
                ListIterator<Food> iterator = foodArrayList.listIterator();

                while (iterator.hasNext()) {
                    if (iterator.next().getId() == deleteId) {
                        iterator.remove();
                    }
                }

                FoodList.getFoodList().setFoodArrayList(foodArrayList);

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
}
