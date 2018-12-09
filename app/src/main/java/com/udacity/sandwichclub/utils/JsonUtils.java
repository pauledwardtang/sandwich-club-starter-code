package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();

        try {
            JSONObject root = new JSONObject(json);

            ////////////////////////////////
            // Name (s)
            ////////////////////////////////
            // Name
            JSONObject name = root.getJSONObject("name");
            sandwich.setMainName(name.getString("mainName"));

            // Aliases
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");

            List<String> nameAliasList = new ArrayList<>(alsoKnownAs.length());
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                nameAliasList.add(alsoKnownAs.getString(i));
            }
            sandwich.setAlsoKnownAs(nameAliasList);

            ////////////////////////////////
            // Place of origin
            ////////////////////////////////
            String placeOfOrigin = root.getString("placeOfOrigin");
            sandwich.setPlaceOfOrigin(placeOfOrigin);

            ////////////////////////////////
            // Description
            ////////////////////////////////
            String description = root.getString("description");
            sandwich.setDescription(description);

            ////////////////////////////////
            // Image URL
            ////////////////////////////////
            String imageUrl = root.getString("image");
            sandwich.setImage(imageUrl);

            ////////////////////////////////
            // Ingredients
            ////////////////////////////////
            JSONArray ingredients = root.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>(ingredients.length());
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsList.add(ingredients.getString(i));
            }
            sandwich.setIngredients(ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
