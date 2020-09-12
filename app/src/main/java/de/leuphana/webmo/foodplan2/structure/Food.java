package de.leuphana.webmo.foodplan2.structure;

import java.io.Serializable;
import java.util.List;

import de.leuphana.webmo.foodplan2.InternalStorage;

public class Food implements Serializable {
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
    public static int genId(List<Food> foodList) {
        int maxId = 0;
        for (Food food: foodList) {
            if (food.id > maxId) {
                maxId = food.id;
            }
            maxId++;
        }
        return maxId;
    }
}
