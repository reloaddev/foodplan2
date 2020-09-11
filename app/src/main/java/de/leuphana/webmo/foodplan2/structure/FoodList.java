package de.leuphana.webmo.foodplan2.structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FoodList implements Serializable {
    private static FoodList foodList;
    private ArrayList<Food> foodArrayList = new ArrayList<Food>();

    private FoodList() {

        Food NudelnMitTomatensauce = new Food(1, "Nudeln mit Tomatensauce", (float) 1.5, Type.VEGAN);
        Food KaiserschmarrnMitSchattenmorellen = new Food(2, "Kaiserschmarrn mit Schattenmorellen", (float) 2.4, Type.VEGETARIAN);
        Food BockwurstMitSenf = new Food(3, "Bockwurst mit Senf", (float) 3.7, Type.MEAT);
        Food BrasilianischerFischeintopf = new Food(4, "Brasilianischer Fischeintopf", (float) 3.0, Type.FISH);
        Food VegetarischeLasagneMitSojahack = new Food(5, "Vegetarische Lasagne mit Sojahack", (float) 2.3, Type.VEGETARIAN);
        Food KartoffelspaltenMitKnoblauchdip = new Food(6, "Kartoffelspalten mit Knoblauchdip", (float) 1.5, Type.VEGETARIAN);
        Food KarottensticksMitTomatenreis = new Food(7, "Karottensticks mit Tomatenreis", (float) 2.2, Type.VEGAN);
        Food KohlwurstMitGrünkohl = new Food(8, "Kohlwurst mit Grünkohl", (float) 3.4, Type.MEAT);
        Food Sommernudeln = new Food(9, "Sommernudeln", (float) 2.1, Type.VEGAN);
        Food BunterSalatMitPinienkernen = new Food(10, "Bunter Salat mit Pinienkernen", (float) 1.8, Type.VEGAN);
        Food FischNuggetsMitRemoulade = new Food(11, "Fisch-Nuggets mit Remoulade", (float) 1.9, Type.FISH);
        Food CurrywurstMitPommes = new Food(12, "Currywurst mit Pommes", (float) 1.8, Type.MEAT);
        Food FrühlingsrollenMitSweetChiliSauce = new Food(13, "Frühlingsrollen mit Sweet-Chili-Sauce", (float) 1.7, Type.VEGAN);
        Food SojagyrosMitJoghurtDip = new Food(14, "Sojagyros mit Joghurt-Dip", (float) 2.3, Type.VEGETARIAN);
        Food NudelnMitSpinatKuhfetaSauce = new Food(15, "Nudeln mit Spinat-Kuhfeta-Sauce", (float) 1.8, Type.VEGETARIAN);
        Food SchweineschnitzelMitKartoffeln = new Food(16, "Schweineschnitzel mit Kartoffeln", (float) 2.8, Type.MEAT);
        Food DorschfiletMitSalzkartoffeln = new Food(17, "Dorschfilet mit Salzkartoffeln", (float) 3.1, Type.FISH);
        Food BratwurstMitNudelsalat = new Food(18, "Bratwurst mit Nudelsalat", (float) 3.0, Type.MEAT);
        Food AsiaWokGemüseMitReis = new Food(19, "Asia Wok-Gemüse mit Reis", (float) 2.2, Type.VEGAN);
        Food RumpsteakMitBratkartoffeln = new Food(20, "Rumpsteak mit Bratkartoffeln", (float) 4.5, Type.MEAT);

        foodArrayList.add(NudelnMitTomatensauce);
        foodArrayList.add(KaiserschmarrnMitSchattenmorellen);
        foodArrayList.add(BockwurstMitSenf);
        foodArrayList.add(BrasilianischerFischeintopf);
        foodArrayList.add(VegetarischeLasagneMitSojahack);
        foodArrayList.add(KartoffelspaltenMitKnoblauchdip);
        foodArrayList.add(KarottensticksMitTomatenreis);
        foodArrayList.add(KohlwurstMitGrünkohl);
        foodArrayList.add(Sommernudeln);
        foodArrayList.add(BunterSalatMitPinienkernen);
        foodArrayList.add(FischNuggetsMitRemoulade);
        foodArrayList.add(CurrywurstMitPommes);
        foodArrayList.add(SojagyrosMitJoghurtDip);
        foodArrayList.add(NudelnMitSpinatKuhfetaSauce);
        foodArrayList.add(SchweineschnitzelMitKartoffeln);
        foodArrayList.add(DorschfiletMitSalzkartoffeln);
        foodArrayList.add(BratwurstMitNudelsalat);
        foodArrayList.add(AsiaWokGemüseMitReis);
        foodArrayList.add(RumpsteakMitBratkartoffeln);
        foodArrayList.add(FrühlingsrollenMitSweetChiliSauce);
    }

    public static FoodList getFoodList() {
        if (foodList == null) {
            foodList = new FoodList();
        }
        return foodList;
    }

    public ArrayList<Food> getFoodArrayList() {
        return foodArrayList;
    }

    public void setFoodArrayList(ArrayList<Food> foodArrayList) {
        this.foodArrayList = foodArrayList;
    }

    public List<String> getFoodNameList() {
        List<String> foodNameList = new ArrayList<String>();

        for(int i = 0; i < foodArrayList.size(); i++) {
            foodNameList.add(foodArrayList.get(i).getName());
        }

        return foodNameList;
    }
}
