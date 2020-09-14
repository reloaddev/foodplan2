package de.leuphana.webmo.foodplan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.GridView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.leuphana.webmo.foodplan2.structure.Food;

import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";
    private int weekCounter;
    private int currentWeek;
    private int displayWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();
        currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);

        //Delete @ the next week the last week
        SharedPreferences df = getSharedPreferences("week", MODE_PRIVATE);
        if(df == null){
            df.edit().putInt("week", currentWeek).apply();
        }
        if(currentWeek != (df.getInt("week", currentWeek))){
            df.edit().putInt("week", currentWeek).apply();
            List<Food> foodPlanList = null;
            try {
                foodPlanList = (List<Food>) InternalStorage.readObject(getApplicationContext(), "foodPlanList");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            for( int i = 0; i <= 14 ; i++){
                try {
                    foodPlanList.remove(0);
                } catch (Exception e){
                    break;
                }
            }
            try {
                InternalStorage.writeObject(getApplicationContext(), "foodPlanList", foodPlanList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Wochenrechner
        final Button btn_currentWeek = findViewById(R.id.button_currentweek);
        final TextView text_weekno = findViewById(R.id.text_weekno);
        final Button btn_nextWeek = findViewById(R.id.button_nextWeek);
        weekCounter = 0;

        displayWeek = currentWeek + weekCounter;
        String weekText = getResources().getString(R.string.week) + String.valueOf(displayWeek);
        text_weekno.setText(weekText);

        btn_currentWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weekCounter = 0;
                displayWeek = currentWeek + weekCounter;
                String weekText = getResources().getString(R.string.week) + String.valueOf(displayWeek);
                text_weekno.setText(weekText);
                try {
                    fillFoodPlan();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_nextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (weekCounter <= 7) {
                    weekCounter = weekCounter + 1;
                    displayWeek = currentWeek + weekCounter;
                    String weekText = getResources().getString(R.string.week) + String.valueOf(displayWeek);
                    text_weekno.setText(weekText);
                    //R.string.txt_week + displayweek
                    //TODO Ausgabe ändern
                    try {
                        fillFoodPlan();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        try {
            InternalStorage.readObject(this, "foodPlanList");
        } catch (IOException | ClassNotFoundException e) {
            try {
                InternalStorage.writeObject(this, "foodPlanList", new ArrayList<Food>());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        try {
            deleteFoodPlanFood();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createNavigation();
        try {
            fillFoodPlan();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void deleteFoodPlanFood() throws IOException, ClassNotFoundException {
        List<Food> foodPlanList = (List<Food>) InternalStorage.readObject(getApplicationContext(), "foodPlanList");
        Bundle deleteFoodBundle = getIntent().getExtras();
        String deleteDay = deleteFoodBundle.getString("deleteDay");
        int deletePosition = deleteFoodBundle.getInt("deletePosition");
        switch (deleteDay) {
            case "MON":
                // TODO i * 15 für weitere Wochen
                if (deletePosition == (0 + weekCounter * 15)) {
                    foodPlanList.remove(0 + weekCounter * 15);
                }
                if (deletePosition == (1 + weekCounter * 15)) {
                    foodPlanList.remove(1 + weekCounter * 15);
                }
                if (deletePosition == (2 + weekCounter * 15)) {
                    foodPlanList.remove(2 + weekCounter * 15);
                }
                break;
            case "TUE":
                if (deletePosition == (0 + weekCounter * 15)) {
                    foodPlanList.remove(3 + weekCounter * 15);
                }
                if (deletePosition == (1 + weekCounter * 15)) {
                    foodPlanList.remove(4 + weekCounter * 15);
                }
                if (deletePosition == (2 + weekCounter * 15)) {
                    foodPlanList.remove(5 + weekCounter * 15);
                }
                break;
            case "WED":
                if (deletePosition == (0 + weekCounter * 15)) {
                    foodPlanList.remove(6 + weekCounter * 15);
                }
                if (deletePosition == (1 + weekCounter * 15)) {
                    foodPlanList.remove(7 + weekCounter * 15);
                }
                if (deletePosition == (2 + weekCounter * 15)) {
                    foodPlanList.remove(8 + weekCounter * 15);
                }
                break;
            case "THU":
                if (deletePosition == (0 + weekCounter * 15)) {
                    foodPlanList.remove(9 + weekCounter * 15);
                }
                if (deletePosition == (1 + weekCounter * 15)) {
                    foodPlanList.remove(10 + weekCounter * 15);
                }
                if (deletePosition == (2 + weekCounter * 15)) {
                    foodPlanList.remove(11 + weekCounter * 15);
                }
                break;
            case "FRI":
                if (deletePosition == (0 + weekCounter * 15)) {
                    foodPlanList.remove(12 + weekCounter * 15);
                }
                if (deletePosition == (1 + weekCounter * 15)) {
                    foodPlanList.remove(13 + weekCounter * 15);
                }
                if (deletePosition == (2 + weekCounter * 15)) {
                    foodPlanList.remove(14 + weekCounter * 15);
                }
                break;
        }
        InternalStorage.writeObject(getApplicationContext(), "foodPlanList", foodPlanList);

    }

    private void createNavigation() {

        Button navButtonfoods = findViewById(R.id.nav_foodsButton);
        Button navButtonlogin = findViewById(R.id.nav_loginButton);

        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);

        if (sp.getBoolean("logged", false)) {
            navButtonlogin.setText(R.string.logout);
        } else {
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
    }

    private void fillFoodPlan() throws IOException, ClassNotFoundException {
        ArrayList<Food> foodList = null;
        try {
            foodList = (ArrayList<Food>) InternalStorage.readObject(this, "foodPlanList");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<String> foodNameList = new ArrayList<String>();
        for (Food food : foodList) {
            foodNameList.add(food.getName());
        }

        fillFoodPlanRows(foodNameList);
    }

    private void fillFoodPlanRows(List<String> foodNameList) throws IOException, ClassNotFoundException {
        final List<Food> foodList = (List<Food>) InternalStorage.readObject(getApplicationContext(), "foodList");
        // Empty list before refilling
        final List<String> deleteFoodNameList = new ArrayList<String>();

        final GridView gridMondayFoods = findViewById(R.id.gridMonday);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_list_item_1, deleteFoodNameList.subList(0 + weekCounter * 15, 3 + weekCounter * 15));
        */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deleteFoodNameList);
        gridMondayFoods.setAdapter(adapter);
        adapter.clear();

        if (foodNameList.size() == (1 + weekCounter * 15)) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(0 + weekCounter * 15, 1 + weekCounter * 15));
            gridMondayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() == (2 + weekCounter * 15)) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(0 + weekCounter * 15, 2 + weekCounter * 15));
            gridMondayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() >= (3 + weekCounter * 15)) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(0 + weekCounter * 15, 3 + weekCounter * 15));
            gridMondayFoods.setAdapter(adapter);
        }
        gridMondayFoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int itemPosition = position;
                String foodName = (String) gridMondayFoods.getItemAtPosition(position);
                Boolean found = false;
                for (Food food : foodList) {
                    if (food.getName().equals(foodName)) {
                        Intent i = new Intent(getApplicationContext(), FoodPlanDetailActivity.class);
                        Bundle positionBundle = new Bundle();
                        positionBundle.putInt("foodId", food.getId());
                        positionBundle.putString("day", "MON");
                        positionBundle.putInt("position", position);
                        i.putExtras(positionBundle);
                        startActivity(i);
                        finish();
                        found = true;
                    }
                }
                if(found == false) {
                    Intent i = new Intent(getApplicationContext(), FoodPlanDetailActivity.class);
                    Bundle positionBundle = new Bundle();
                    positionBundle.putInt("foodId", -1);
                    positionBundle.putString("day", "MON");
                    positionBundle.putInt("position", position);
                    i.putExtras(positionBundle);
                    startActivity(i);
                    finish();
                }
            }
        });

        final GridView gridTuesdayFoods = findViewById(R.id.gridTuesday);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deleteFoodNameList);
        gridTuesdayFoods.setAdapter(adapter);
        adapter.clear();

        if (foodNameList.size() == (4 + weekCounter * 15)) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(3 + weekCounter * 15, 4 + weekCounter * 15));
            gridTuesdayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() == (5 + weekCounter * 15)) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(3 + weekCounter * 15, 5 + weekCounter * 15));
            gridTuesdayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() >= (6 + weekCounter * 15)) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(3 + weekCounter * 15, 6 + weekCounter * 15));
            gridTuesdayFoods.setAdapter(adapter);
        }

        gridTuesdayFoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            final List<Food> foodList = (List<Food>) InternalStorage.readObject(getApplicationContext(), "foodList");

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int itemPosition = position;
                Boolean found = false;
                String foodName = (String) gridTuesdayFoods.getItemAtPosition(position);
                for (Food food : foodList) {
                    if (food.getName().equals(foodName)) {
                        Intent i = new Intent(getApplicationContext(), FoodPlanDetailActivity.class);
                        Bundle positionBundle = new Bundle();
                        positionBundle.putInt("foodId", food.getId());
                        positionBundle.putString("day", "TUE");
                        positionBundle.putInt("position", position);
                        i.putExtras(positionBundle);
                        startActivity(i);
                        finish();
                        found = true;
                    }
                }
                if(found == false) {
                    Intent i = new Intent(getApplicationContext(), FoodPlanDetailActivity.class);
                    Bundle positionBundle = new Bundle();
                    positionBundle.putInt("foodId", -1);
                    positionBundle.putString("day", "TUE");
                    positionBundle.putInt("position", position);
                    i.putExtras(positionBundle);
                    startActivity(i);
                    finish();
                }

            }
        });

        final GridView gridWednesdayFoods = findViewById(R.id.gridWednesday);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deleteFoodNameList);
        gridWednesdayFoods.setAdapter(adapter);
        adapter.clear();

        if (foodNameList.size() == (7 + weekCounter * 15)) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(6 + weekCounter * 15, 7 + weekCounter * 15));
            gridWednesdayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() == (8 + weekCounter * 15)) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(6 + weekCounter * 15, 8 + weekCounter * 15));
            gridWednesdayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() >= (9 + weekCounter * 15)) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(6 + weekCounter * 15, 9 + weekCounter * 15));
            gridWednesdayFoods.setAdapter(adapter);
        }

        gridWednesdayFoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            final List<Food> foodList = (List<Food>) InternalStorage.readObject(getApplicationContext(), "foodList");

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int itemPosition = position;
                Boolean found = false;
                String foodName = (String) gridWednesdayFoods.getItemAtPosition(position);
                for (Food food : foodList) {
                    if (food.getName().equals(foodName)) {
                        Intent i = new Intent(getApplicationContext(), FoodPlanDetailActivity.class);
                        Bundle positionBundle = new Bundle();
                        positionBundle.putInt("foodId", food.getId());
                        positionBundle.putString("day", "WED");
                        positionBundle.putInt("position", position);
                        i.putExtras(positionBundle);
                        startActivity(i);
                        finish();
                        found = true;
                    }
                    if(found == false) {
                        Intent i = new Intent(getApplicationContext(), FoodPlanDetailActivity.class);
                        Bundle positionBundle = new Bundle();
                        positionBundle.putInt("foodId", -1);
                        positionBundle.putString("day", "WED");
                        positionBundle.putInt("position", position);
                        i.putExtras(positionBundle);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });

        final GridView gridThursdayFoods = findViewById(R.id.gridThursday);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deleteFoodNameList);
        gridThursdayFoods.setAdapter(adapter);
        adapter.clear();
        if (foodNameList.size() == (10 + weekCounter * 15)) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(9 + weekCounter * 15, 10 + weekCounter * 15));
            gridThursdayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() == (11 + weekCounter * 15)) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(9 + weekCounter * 15, 11 + weekCounter * 15));
            gridThursdayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() >= (12 + weekCounter * 15)) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(9 + weekCounter * 15, 12 + weekCounter * 15));
            gridThursdayFoods.setAdapter(adapter);
        }

        gridThursdayFoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            final List<Food> foodList = (List<Food>) InternalStorage.readObject(getApplicationContext(), "foodList");

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int itemPosition = position;
                Boolean found = false;
                String foodName = (String) gridThursdayFoods.getItemAtPosition(position);
                for (Food food : foodList) {
                    if (food.getName().equals(foodName)) {
                        Intent i = new Intent(getApplicationContext(), FoodPlanDetailActivity.class);
                        Bundle positionBundle = new Bundle();
                        positionBundle.putInt("foodId", food.getId());
                        positionBundle.putString("day", "THU");
                        positionBundle.putInt("position", position);
                        i.putExtras(positionBundle);
                        startActivity(i);
                        finish();
                        found = true;
                    }
                }
                if(found == false) {
                    Intent i = new Intent(getApplicationContext(), FoodPlanDetailActivity.class);
                    Bundle positionBundle = new Bundle();
                    positionBundle.putInt("foodId", -1);
                    positionBundle.putString("day", "THU");
                    positionBundle.putInt("position", position);
                    i.putExtras(positionBundle);
                    startActivity(i);
                    finish();
                }
            }
        });


        final GridView gridFridayFoods = findViewById(R.id.gridFriday);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deleteFoodNameList);
        gridFridayFoods.setAdapter(adapter);
        adapter.clear();
        if (foodNameList.size() == (13 + weekCounter * 15)) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(12 + weekCounter * 15, 13 + weekCounter * 15));
            gridFridayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() == (14 + weekCounter * 15)) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(12 + weekCounter * 15, 14 + weekCounter * 15));
            gridFridayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() >= (15 + weekCounter * 15)) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(12 + weekCounter * 15, 15 + weekCounter * 15));
            gridFridayFoods.setAdapter(adapter);
        }

        gridFridayFoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            final List<Food> foodList = (List<Food>) InternalStorage.readObject(getApplicationContext(), "foodList");

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int itemPosition = position;
                Boolean found = false;
                String foodName = (String) gridFridayFoods.getItemAtPosition(position);
                for (Food food : foodList) {
                    if (food.getName().equals(foodName)) {
                        Intent i = new Intent(getApplicationContext(), FoodPlanDetailActivity.class);
                        Bundle positionBundle = new Bundle();
                        positionBundle.putInt("foodId", food.getId());
                        positionBundle.putString("day", "FRI");
                        positionBundle.putInt("position", position);
                        i.putExtras(positionBundle);
                        startActivity(i);
                        finish();
                        found = true;
                    }
                    if(found == false) {
                        Intent i = new Intent(getApplicationContext(), FoodPlanDetailActivity.class);
                        Bundle positionBundle = new Bundle();
                        positionBundle.putInt("foodId", -1);
                        positionBundle.putString("day", "FRI");
                        positionBundle.putInt("position", position);
                        i.putExtras(positionBundle);
                        startActivity(i);
                        finish();
                    }
                }

            }
        });

    }
}