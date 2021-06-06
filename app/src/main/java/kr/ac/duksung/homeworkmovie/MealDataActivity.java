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
    //Integer[] images = {R.drawable.meal0, R.drawable.meal1, R.drawable.meal2};
    //String [] meals= {"Caprese Salad","Chicken and Potatoes", "Pasta with Meatballs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealdata);

        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        Button button = (Button) findViewById(R.id.button);

        Intent intent = getIntent();
        final String meal = intent.getStringExtra("memu_name");
        textView.setText(meal+" 선택!");
        textView2.setText("추가 정보를 원하시면 MORE 버튼 클릭!");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Intent.ACTION_WEB_SEARCH);
                intent2.putExtra(SearchManager.QUERY, meal);
                startActivity(intent2);
            }
        });


    }
}
