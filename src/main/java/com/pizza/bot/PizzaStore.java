package main.java.com.pizza.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PizzaStore {

    public final static List<String>PIZZA_TYPE_LIST = new ArrayList<>(Arrays.asList("Margherita", "Hawaiian" , "Veggie", "Pepperoni"));
    
    public final static Map<String, Double>DRINKS_MAP = new HashMap<>();
    public static final  Map<String, Double> PIZZA_SIZES_MAP = new HashMap<>();;


    static {

        PIZZA_SIZES_MAP.put("Small", (double) 25);
        PIZZA_SIZES_MAP.put("Medium", (double) 50);
        PIZZA_SIZES_MAP.put("Large", (double) 100);


        DRINKS_MAP.put("Cola", 2.6);
        DRINKS_MAP.put("Coffee", 3.2);
        DRINKS_MAP.put("Water", 1.5);
        
    }
}
