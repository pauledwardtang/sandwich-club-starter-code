package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich.getDescription(),
            sandwich.getIngredients(),
            sandwich.getAlsoKnownAs(),
            sandwich.getPlaceOfOrigin());

        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(String description,
                            List<String> ingredients,
                            List<String> alsoKnownAs,
                            String placeOfOrigin) {

        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);
        TextView originTv = findViewById(R.id.origin_tv);

        if (description != null && !description.isEmpty()) {
            descriptionTv.setText(description);
        }

        ingredientsTv.setText(TextUtils.join(", ", ingredients));
        alsoKnownTv.setText(TextUtils.join(", ", alsoKnownAs));

        if (placeOfOrigin != null && !placeOfOrigin.isEmpty()) {
            originTv.setText(placeOfOrigin);
        }
    }
}
