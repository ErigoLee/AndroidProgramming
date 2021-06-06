package kr.ac.duksung.baseappcl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button button1;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);


//   함축2 - 교재 코드
        //버튼1
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "버튼을 눌렀어요",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //버튼2
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "두 번째 버튼을 눌렀어요",
                        Toast.LENGTH_SHORT).show();
            }
        });

//  함축1 - 중간형 코드(익명 클래스)
/*
        View.OnClickListener listener = new View.OnClickListener() {}
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "버튼을 눌렀어요",
                        Toast.LENGTH_SHORT).show();
            }
        };
        button1.setOnClickListener(listener);
*/

/*
        ButtonListener listener = new ButtonListener();
        button1.setOnClickListener(listener);

*/
    }


    /*
    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "버튼을 눌렀어요",
                    Toast.LENGTH_SHORT).show();
        }
    }
*/
}

