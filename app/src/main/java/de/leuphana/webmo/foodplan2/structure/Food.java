package de.leuphana.webmo.foodplan2.structure;

import java.util.List;

public class Food {
    private int id;
    private String name;
    private float price;
    private Type type;

    public Food (int id, String name, float price, Type type){
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public int getId() {return id;}
    public String getName() {
        return name;
    }
    public float getPrice(){ return price; }
    public Type getType() {return type; }

    //With this Method we will never get Id´s lower than the maxId
    //But it´s enough for our context
    public static int genId() {
        List<Food> foodArrayList = FoodList.getFoodList().getFoodArrayList();
        int maxId = 0;
        for (Food food: foodArrayList) {
            if (food.id > maxId) {
                maxId = food.id;
            }
            maxId++;
        }
        return maxId;
    }
}
