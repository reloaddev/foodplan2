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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.leuphana.webmo.foodplan2.structure.Food;

import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";
    private int weekcounter;
    private int currentweek;
    private int displayweek;

    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Wochenrechner
        final Button btn_currentWeek = findViewById(R.id.button_currentweek);
        final TextView text_weekno = findViewById(R.id.text_weekno);
        final Button btn_nextWeek = findViewById(R.id.button_nextWeek);
        weekcounter = 0;
        Calendar calendar = Calendar.getInstance();
        currentweek = calendar.get(Calendar.WEEK_OF_YEAR);
        displayweek = currentweek + weekcounter;
        text_weekno.setText("Week:" + displayweek);

        btn_currentWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weekcounter = 0;
                displayweek = currentweek + weekcounter;
                text_weekno.setText("Week:" + displayweek);
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
                if (weekcounter <= 7) {
                    weekcounter = weekcounter + 1;
                    displayweek = currentweek + weekcounter;
                    text_weekno.setText("Week:" + displayweek);
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

                if (deletePosition == 0 + weekcounter * 15) {
                    foodPlanList.remove(0 + weekcounter * 15);
                }
                if (deletePosition == 1 + weekcounter * 15) {
                    foodPlanList.remove(1 + weekcounter * 15);
                }
                if (deletePosition == 2 + weekcounter * 15) {
                    foodPlanList.remove(2 + weekcounter * 15);
                }
                break;
            case "TUE":
                if (deletePosition == 0 + weekcounter * 15) {
                    foodPlanList.remove(3 + weekcounter * 15);
                }
                if (deletePosition == 1 + weekcounter * 15) {
                    foodPlanList.remove(4 + weekcounter * 15);
                }
                if (deletePosition == 2 + weekcounter * 15) {
                    foodPlanList.remove(5 + weekcounter * 15);
                }
                break;
            case "WED":
                if (deletePosition == 0 + weekcounter * 15) {
                    foodPlanList.remove(6 + weekcounter * 15);
                }
                if (deletePosition == 1 + weekcounter * 15) {
                    foodPlanList.remove(7 + weekcounter * 15);
                }
                if (deletePosition == 2 + weekcounter * 15) {
                    foodPlanList.remove(8 + weekcounter * 15);
                }
                break;
            case "THU":
                if (deletePosition == 0 + weekcounter * 15) {
                    foodPlanList.remove(9 + weekcounter * 15);
                }
                if (deletePosition == 1 + weekcounter * 15) {
                    foodPlanList.remove(10 + weekcounter * 15);
                }
                if (deletePosition == 2 + weekcounter * 15) {
                    foodPlanList.remove(11 + weekcounter * 15);
                }
                break;
            case "FRI":
                if (deletePosition == 0 + weekcounter * 15) {
                    foodPlanList.remove(12 + weekcounter * 15);
                }
                if (deletePosition == 1 + weekcounter * 15) {
                    foodPlanList.remove(13 + weekcounter * 15);
                }
                if (deletePosition == 2 + weekcounter * 15) {
                    foodPlanList.remove(14 + weekcounter * 15);
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

        final GridView gridMondayFoods = findViewById(R.id.gridMonday);
        // if (foodNameList.size() >= 1 + 15 * i
        if (foodNameList.size() == 1) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(0, 1));
            gridMondayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() == 2) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(0, 2));
            gridMondayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() >= 3) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(0, 3));
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
        if (foodNameList.size() == 4) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(3, 4));
            gridTuesdayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() == 5) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(3, 5));
            gridTuesdayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() >= 6) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(3, 6));
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
        if (foodNameList.size() == 7) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(6, 7));
            gridWednesdayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() == 8) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(6, 8));
            gridWednesdayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() >= 9) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(6, 9));
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
        if (foodNameList.size() == 10) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(9, 10));
            gridThursdayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() == 11) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(9, 11));
            gridThursdayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() >= 12) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(9, 12));
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
        if (foodNameList.size() == 13) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(12, 13));
            gridFridayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() == 14) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(12, 14));
            gridFridayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() >= 15) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(12, 15));
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


    private void fillFoodPlan(){
        ArrayList<Food> foodList = null;
        try {
            foodList = (ArrayList<Food>) InternalStorage.readObject(this, "foodPlanList");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        List<String> foodNameList = new ArrayList<String>();
        for(Food food: foodList) {
            foodNameList.add(food.getName());
        }
        fillFoodPlanRows(foodNameList);
    }

    private void fillFoodPlanRows(final List<String> foodNameList) {
         //Monday Foodplan
        final GridView gridMondayFoods = findViewById(R.id.gridMonday);

        if (foodNameList.size() == 1 + weekcounter * 15) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(0 + weekcounter * 15, 1 + weekcounter * 15));
            gridMondayFoods.setAdapter(adapter);
        }

        if (foodNameList.size() == 2 + weekcounter * 15) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(0 + weekcounter * 15, 2 + weekcounter * 15));
            gridMondayFoods.setAdapter(adapter);
        }

        if (foodNameList.size() >= 3 + weekcounter * 15) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(0 + weekcounter * 15, 3 + weekcounter * 15));
            gridMondayFoods.setAdapter(adapter);
        }
        //Tuesday Fooplan
        final GridView gridTuesdayFoods = findViewById(R.id.gridTuesday);

        if (foodNameList.size() == 4 + weekcounter * 15) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(3 + weekcounter * 15, 4 + weekcounter * 15));
            gridTuesdayFoods.setAdapter(adapter);
        }

        if (foodNameList.size() == 5 + weekcounter * 15) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(3 + weekcounter * 15, 5 + weekcounter * 15));
            gridTuesdayFoods.setAdapter(adapter);
        }

        if (foodNameList.size() >= 6 + weekcounter * 15) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(3 + weekcounter * 15, 6 + weekcounter * 15));
            gridTuesdayFoods.setAdapter(adapter);
        }
        //Wednesday Fodplan
        final GridView gridWednesdayFoods = findViewById(R.id.gridWednesday);

        if (foodNameList.size() == 7 + weekcounter * 15) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(6 + weekcounter * 15, 7 + weekcounter * 15));
            gridWednesdayFoods.setAdapter(adapter);
        }

        if (foodNameList.size() == 8 + weekcounter * 15) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(6 + weekcounter * 15, 8 + weekcounter * 15));
            gridWednesdayFoods.setAdapter(adapter);
        }

        if (foodNameList.size() >= 9 + weekcounter * 15) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(6 + weekcounter * 15, 9 + weekcounter * 15));
            gridWednesdayFoods.setAdapter(adapter);
        }
        //Thursday Foodplan
        final GridView gridThursdayFoods = findViewById(R.id.gridThursday);

        if (foodNameList.size() == 10 + weekcounter * 15) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(9 + weekcounter * 15, 10 + weekcounter * 15));
            gridThursdayFoods.setAdapter(adapter);
        }

        if (foodNameList.size() == 11 + weekcounter * 15) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(9 + weekcounter * 15, 11 + weekcounter * 15));
            gridThursdayFoods.setAdapter(adapter);
        }
        if (foodNameList.size() >= 12 + weekcounter * 15) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(9 + weekcounter * 15, 12 + weekcounter * 15));
            gridThursdayFoods.setAdapter(adapter);
        }
        //Friday Foodplan
        final GridView gridFridayFoods = findViewById(R.id.gridFriday);
        if (foodNameList.size() == 13 + weekcounter * 15) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(12 + weekcounter * 15, 13 + weekcounter * 15));
            gridFridayFoods.setAdapter(adapter);
        }

        if (foodNameList.size() == 14 + weekcounter * 15) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(12 + weekcounter * 15, 14 + weekcounter * 15));
            gridFridayFoods.setAdapter(adapter);
        }

        if (foodNameList.size() >= 15 + weekcounter * 15) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, foodNameList.subList(12 + weekcounter * 15, 15 + weekcounter * 15));
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