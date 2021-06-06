package kr.ac.duksung.chosun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> topics;
    ArrayList<String> links;
    ArrayAdapter<String> adapter;
    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topics = new ArrayList<String>();
        links = new ArrayList<String>();
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topics);
        listView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        makeRequest();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), topics.get(i), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                intent.putExtra("urlStr", links.get(i));
                startActivity(intent);
            }
        });
    }

    public void makeRequest() {
        String url = "http://www.chosun.com/";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            Toast.makeText(getApplicationContext(), "encoding error",
                                    Toast.LENGTH_LONG).show();
                        }
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

    public void parseHtml(String html) {
        Document doc = Jsoup.parse(html);

        Elements elements = doc.select("h2 a");
        for (Element element : elements) {
            topics.add(element.text().trim());
            links.add(element.attr("href").trim());
        }

        elements = doc.select("dl.news_item dt a");
        for (Element element : elements) {
            topics.add(element.text().trim());
            android.util.Log.d("test: ", element.attr("href"));
            links.add(element.attr("href").trim());
        }
        android.util.Log.d("test: ", topics.size() + " topics");
        android.util.Log.d("test: ", links.size() + " links");

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        topics.clear();
        links.clear();
        makeRequest();
        return true;
    }
}
