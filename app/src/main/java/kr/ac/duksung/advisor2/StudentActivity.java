package kr.ac.duksung.advisor2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    private Document doc;
    ArrayList<String> students;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        students = new ArrayList<String>();
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, students);
        listView.setAdapter(adapter);
        // local server
        //       String urlString = "http://10.0.2.2:8080/FinalProject/advisorPro.jsp";
        String urlString = "http://203.252.219.241:8080/FinalProject/advisorPro2.jsp";
        Intent intent = getIntent();
        String professor = intent.getStringExtra("professor");

        urlString = urlString + "?advisor=" + professor;
        JsoupAsyncTask advisorTask = new JsoupAsyncTask();
        advisorTask.execute(urlString);
    }

    private class JsoupAsyncTask extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... params) {
            try {
                doc = Jsoup.connect(params[0]).get();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "network error",
                        Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {
            ArrayList<String> student = new ArrayList<String>();
            ArrayList<String> id = new ArrayList<String>();
            Elements elements = doc.select("h5");
            Elements elements2 = doc.select("i");
            for(Element element : elements) {
                student.add(element.text());
            }
            for(Element element : elements2){
                id.add(element.text());
            }
            int size = id.size();
            for(int i =0;i<size;i++){
             students.add(student.get(i)+" / "+id.get(i));
            }
            adapter.notifyDataSetChanged();
        }
    }
}
