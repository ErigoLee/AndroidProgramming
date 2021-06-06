package kr.ac.duksung.dust;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {
    EditText editText1, editText2;

    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Dust");

        Button button = findViewById(R.id.button);
        editText1 = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeRequest();

            }
        });

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }


    public void makeRequest() {

        String id = editText1.getText().toString().trim();
        String passwd = editText2.getText().toString().trim();
        String url = "http://203.252.219.241:8080/FinalProject/loginPro.jsp?id="+id+"&passwd="+passwd;
        //http://203.252.219.241:8080/FinalProject/loginPro.jsp?id=erigo2018a&passwd=erigo2018a
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        android.util.Log.d("test2: ", response);
                        parseHtml(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "network error",
                                Toast.LENGTH_LONG).show();
                    }
                }
        );

        request.setShouldCache(false);
        requestQueue.add(request);
    }



    public void parseHtml(String json) {
        android.util.Log.d("test2: ", json);
        int request = Integer.parseInt(json.trim());
        if (request == 1) {

            Intent intent = new Intent(getApplicationContext(), AirActivity.class);
            startActivity(intent);
        } else {

            AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
            dlg.setTitle("Login Failed!");
            dlg.setMessage("Please try again");
            dlg.setPositiveButton("확인", null);
            dlg.show();
            //Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_LONG).show();
        }
    }
}
