package kr.ac.duksung.computeactivity2homework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ComputeActivity extends AppCompatActivity {
    TextView textView;
    Button button;
    int answer; //연산 답
    int number1; //숫자1
    int number2; //숫자2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);
        setTitle("계산");

        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        Intent intent = getIntent();
        number1 = intent.getIntExtra("number1",0);
        final String operator = intent.getStringExtra("operator");
        number2 = intent.getIntExtra("number2",0);


        switch (operator){
            case "+":
                answer = number1+number2;
                textView.setText("("+number1+")"+operator+"("+number2+")="+answer);
                break;
            case "-":
                answer = number1-number2;
                textView.setText("("+number1+")"+operator+"("+number2+")="+answer);
                break;
            case "*":
                answer = number1*number2;
                textView.setText("("+number1+")"+operator+"("+number2+")="+answer);
                break;
            case "/":
                answer = number1/number2;
                textView.setText("("+number1+")"+operator+"("+number2+")="+answer);
                break;

        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = "("+number1+")"+operator+"("+number2+")="+answer;
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("appoint",result);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}

