package de.leuphana.webmo.foodplan2.structure;

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
}
