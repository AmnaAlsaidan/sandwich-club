package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    @BindView(R.id.also_known_tv)
    TextView alsoKnowTextView;
    @BindView(R.id.description_tv)
    TextView descriptionTextView;
    @BindView(R.id.origin_tv)
    TextView placeOfOriginTextView;
    @BindView(R.id.ingredients_tv)
    TextView ingredientsTextView;
    @BindView(R.id.image_iv)
    ImageView ingredientsIv;
    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        //populate Also Known As
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnowTextView.setText("-");
        } else {
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); ++i) {
                alsoKnowTextView.append(sandwich.getAlsoKnownAs().get(i) + "\n");
            }
        }


        //populate Place Of Origin

        if (sandwich.getPlaceOfOrigin().equals("")) {
            placeOfOriginTextView.setText("-");
        } else {
            placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());

        }

        //populate Ingredients

        for (int i = 0; i < sandwich.getIngredients().size(); ++i) {
            ingredientsTextView.append(sandwich.getIngredients().get(i) + "\n");

        }

        //populate Description

        descriptionTextView.setText(sandwich.getDescription());
    }
}
