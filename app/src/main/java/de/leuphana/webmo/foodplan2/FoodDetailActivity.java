package de.leuphana.webmo.foodplan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

public class FoodDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        Bundle bundle = getIntent().getExtras();
        int foodId = -1;
        if(bundle != null) {
            foodId = bundle.getInt("foodId");
        }

<<<<<<< Updated upstream
        List<Food> foodList = FoodList.getFoodList().getFoodArrayList();
        Food food = new Food(-1, "Undefined", 0.00f,Type.NOTASSIGNED);
        for(Food foodIterator: foodList) {
            if(foodIterator.getId()==foodId){
=======
        createNavigation();
        try {
            createFoodDetailView(foodId);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createNavigation() {
        //NavButtons
        final Button navButtonPlanFoods = findViewById(R.id.nav_foodplanButton);
        final Button navButtonFoods = findViewById(R.id.nav_foodsButton);
        navButtonPlanFoods.setOnClickListener(new View.OnClickListener() {
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

    public void createFoodDetailView(int foodId) throws IOException, ClassNotFoundException {
        final List<Food> foodList = (List<Food>) InternalStorage.readObject(getApplicationContext(), "foodList");
        Food food = new Food(-1, "Undefined", 0.00f, Type.NOTASSIGNED);
        for (Food foodIterator : foodList) {
            if (foodIterator.getId() == foodId) {
>>>>>>> Stashed changes
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

        final Button saveButton = findViewById(R.id.saveButton);
        final Button deleteButton = findViewById(R.id.deleteButton);
        final Button backButton = findViewById(R.id.backButton);

        final TextView result = findViewById((R.id.tvResult));
        //NavButtons
        final Button navButtonplanfoods =  findViewById(R.id.foodplanButton);
        final Button navButtonfoods =  findViewById(R.id.foodsButton);
        final Button navButtonlogin =  findViewById(R.id.loginButton);


        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
        if ( sp.getBoolean("logged",false)){
            saveButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            navButtonlogin.setText(R.string.logout);

        }else{
            saveButton.setVisibility(View.INVISIBLE);
            deleteButton.setVisibility(View.INVISIBLE);
            navButtonlogin.setText(R.string.menu_login);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(foodIdView.getText().toString());
                String name = foodNameView.getText().toString();
                float price = Float.parseFloat(foodPriceView.getText().toString());
                String type = foodTypeView.getText().toString();
                result.setText("ID: " + id + " | NAME: " + name + " | PRICE: " + price + " | " + type);
                Food updatedFood = new Food(id, name, price, Type.valueOf(type));

                ArrayList<Food> foodArrayList = FoodList.getFoodList().getFoodArrayList();
                foodArrayList.add(updatedFood);
                FoodList.getFoodList().setFoodArrayList(foodArrayList);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deleteId = Integer.parseInt(foodIdView.getText().toString());
                ArrayList<Food> foodArrayList = FoodList.getFoodList().getFoodArrayList();
                ListIterator<Food> iterator = foodArrayList.listIterator();

                while(iterator.hasNext()) {
                    if (iterator.next().getId() == deleteId) {
                        iterator.remove();
                    }
                }

                FoodList.getFoodList().setFoodArrayList(foodArrayList);

                try {
                    Intent k = new Intent(getApplicationContext(), FoodListActivity.class);
                    startActivity(k);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
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
<<<<<<< Updated upstream
                    Intent k = new Intent(getApplicationContext(), FoodListActivity.class);
                    startActivity(k);
                } catch(Exception e) {
=======
                    ArrayList<Food> foodPlanList = (ArrayList<Food>) InternalStorage.readObject(getApplicationContext(), "foodPlanList");
                    foodPlanList.add(finalFood);
                    InternalStorage.writeObject(getApplicationContext(), "foodPlanList", foodPlanList);
                    Toast.makeText(getApplicationContext(), R.string.addedToFoodplan, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
>>>>>>> Stashed changes
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
    }
}