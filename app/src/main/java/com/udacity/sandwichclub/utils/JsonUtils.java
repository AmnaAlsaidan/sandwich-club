package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject sandwichObject = new JSONObject(json);
        JSONObject sandwichName = sandwichObject.getJSONObject("name");
        String sandwichMainName = sandwichName.getString("mainName");
        JSONArray alsoKnownAsArrayObject = sandwichName.getJSONArray("alsoKnownAs");
        String sandwichPlace = sandwichObject.getString("placeOfOrigin");
        String sandwichDescription = sandwichObject.getString("description");
        String sandwichImage = sandwichObject.getString("image");
        JSONArray ingredients = sandwichObject.getJSONArray("ingredients");
        Sandwich sandwich = new Sandwich();
        sandwich.setMainName(sandwichMainName);
        sandwich.setDescription(sandwichDescription);
        sandwich.setImage(sandwichImage);
        sandwich.setPlaceOfOrigin(sandwichPlace);
        List<String> alsoKnownList = new ArrayList<>();
        List<String> ingredientsList = new ArrayList<>();

        for (int i = 0; i < alsoKnownAsArrayObject.length(); ++i) {
            alsoKnownList.add(alsoKnownAsArrayObject.getString(i));
        }
        for (int i = 0; i < ingredients.length(); ++i) {
            ingredientsList.add(ingredients.getString(i));
        }
        sandwich.setAlsoKnownAs(alsoKnownList);
        sandwich.setIngredients(ingredientsList);

        return sandwich;
    }
}
