package kr.ac.duksung.homeworkmovie;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MealDataActivity extends AppCompatActivity {
    Integer[] images = {R.drawable.meal0, R.drawable.meal1, R.drawable.meal2};
    String [] meals= {"Caprese Salad","Chicken and Potatoes", "Pasta with Meatballs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealdata);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView textView = (TextView) findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.button);

        Intent intent = getIntent();
        final Integer position = intent.getIntExtra("image",0);
        imageView.setImageResource(images[position]);
        textView.setText(meals[position]);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Intent.ACTION_WEB_SEARCH);
                intent2.putExtra(SearchManager.QUERY, meals[position]);
                startActivity(intent2);
            }
        });


    }
}
