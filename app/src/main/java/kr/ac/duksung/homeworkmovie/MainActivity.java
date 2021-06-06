package kr.ac.duksung.homeworkmovie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //String [] meals= {"Caprese Salad","Chicken and Potatoes", "Pasta with Meatballs"};
    ArrayList<String> meals;
    String delete_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meals = new ArrayList<String>();
        meals.add("Caprese Salad"); meals.add("Chicken and Potatoes"); meals.add("Pasta with Meatballs");

        ListView menuList = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,meals);

        menuList.setAdapter(adapter);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),meals.get(i),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),MealDataActivity.class);
                intent.putExtra("memu_name",meals.get(i));
                startActivity(intent);
            }
        });

        Button addBtn = (Button) findViewById(R.id.button2);
        Button backBtn = (Button) findViewById(R.id.button3);
        final EditText addEdit = (EditText) findViewById(R.id.editText);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meals.add(addEdit.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(delete_name.equals(""))
                    return;
                meals.add(delete_name);
                adapter.notifyDataSetChanged();
                delete_name="";
            }
        });

        menuList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int which = i;
                AlertDialog.Builder dig = new AlertDialog.Builder(MainActivity.this);
                dig.setTitle("삭제?");
                dig.setMessage(meals.get(i));
                dig.setNegativeButton("취소",null);
                dig.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete_name = meals.get(which);
                        meals.remove(which);
                        adapter.notifyDataSetChanged();
                    }
                });

                dig.show();
                return true;
            }
        });

    }
}
