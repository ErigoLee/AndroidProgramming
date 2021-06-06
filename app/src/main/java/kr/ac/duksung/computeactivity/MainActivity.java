package kr.ac.duksung.computeactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    EditText editText2;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("입력");

        editText = (EditText) findViewById(R.id.editText);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        editText2 = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),ComputeActivity.class);
                String num_str = editText.getText().toString();
                num_str = num_str.trim();

                if(num_str.equals(""))
                    Toast.makeText(getApplicationContext(),"첫 번째 정수 입력",Toast.LENGTH_LONG).show();
                else {
                    int num = Integer.parseInt(num_str);
                    intent.putExtra("number1",num);
                    switch (radioGroup.getCheckedRadioButtonId()) {
                        case R.id.radioButton:
                            intent.putExtra("operator", "+");
                            break;
                        case R.id.radioButton2:
                            intent.putExtra("operator", "-");
                            break;
                        case R.id.radioButton3:
                            intent.putExtra("operator", "*");
                            break;
                        case R.id.radioButton4:
                            intent.putExtra("operator", "/");
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "연산자 선택",
                                    Toast.LENGTH_LONG).show();
                            return;
                    }
                    String num2_str = editText2.getText().toString();
                    num2_str = num2_str.trim();
                    if(num2_str.equals("")) {
                        Toast.makeText(getApplicationContext(), "두 번째 정수 입력", Toast.LENGTH_LONG).show();
                    }
                    else {
                        int num2 = Integer.parseInt(num2_str);
                        intent.putExtra("number2", num2);
                        startActivity(intent);
                    }
                }

            }
        });

    }
}
