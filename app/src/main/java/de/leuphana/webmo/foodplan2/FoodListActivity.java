package de.leuphana.webmo.foodplan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import de.leuphana.webmo.foodplan2.structure.Food;
import de.leuphana.webmo.foodplan2.structure.FoodList;

import static android.R.layout.simple_list_item_1;

public class FoodListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        final List<Food> foodList = FoodList.getFoodList().getFoodArrayList();
        List<String> foodNameList = FoodList.getFoodList().getFoodNameList();

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

    }
}