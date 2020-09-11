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
import android.widget.SearchView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.leuphana.webmo.foodplan2.structure.Food;
import de.leuphana.webmo.foodplan2.structure.FoodList;
import de.leuphana.webmo.foodplan2.structure.Type;

import static android.R.layout.simple_list_item_1;

public class FoodListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        try {
            InternalStorage.readObject(this, "foodList");
        } catch (IOException | ClassNotFoundException e){
            ArrayList<Food> foodList = FoodList.getFoodList().getFoodArrayList();
            try {
                InternalStorage.writeObject(this, "foodList", foodList);
            } catch (IOException i) {
                i.printStackTrace();
            }
        }


        createNavigation();

        try {
            fillFoodList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createNavigation() {
        Button navButtonplanfoods =  findViewById(R.id.nav_foodplanButton);
        Button navButtonlogin =  findViewById(R.id.nav_loginButton);
        Button navButtonsettings =  findViewById(R.id.nav_settingsButton);

        //TODO Hide ADD und Test
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
        navButtonsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });
    }

    private void fillFoodList() throws IOException, ClassNotFoundException {
        final List<Food> foodList = (List<Food>) InternalStorage.readObject(this, "foodList");
        List<String> foodNameList = new ArrayList<String>();
        for(Food food: foodList) {
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

                // Find food with foodName in FoodList.foodArrayList
                for (Food food : foodList) {
                    if (food.getName().equals(foodName)) {
                        Intent i = new Intent(getApplicationContext(), FoodDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("foodId", food.getId());
                        i.putExtras(bundle);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });

        final Button addButton = findViewById(R.id.addButton);
        final EditText inputFoodname = findViewById(R.id.inputFoodname);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputFoodname.getText().toString();
                if(!name.equals("name of Food") && !name.isEmpty()) {
                    Food newFood = new Food(Food.genId(), name, 0, Type.NOTASSIGNED);
                    foodList.add(newFood);
                    try {
                        InternalStorage.writeObject(getApplicationContext(), "foodList", foodList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //refresh activity
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    private void configureSearchBar()throws IOException, ClassNotFoundException {
      ListView listView = (ListView) findViewById(R.id.foodList);
      SearchView  editsearch = (SearchView) findViewById(R.id.searchView);

        final List<Food> foodList = (List<Food>) InternalStorage.readObject(this, "foodList");
        List<String> foodNameList = new ArrayList<String>();
        for(Food food: foodList) {
            foodNameList.add(food.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getApplicationContext(), simple_list_item_1, foodNameList);

        listView.setAdapter(adapter);

        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                adapter.filter(text);
                return false;
            }
        });
    }


}