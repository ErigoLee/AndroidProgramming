package kr.ac.duksung.chosun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {
    Button backBtn;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        backBtn = (Button) findViewById(R.id.backBtn);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSet = webView.getSettings();
        webSet.setJavaScriptEnabled(true);

        Intent intent = getIntent();
        String urlStr = intent.getStringExtra("urlStr");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        class MyWebViewClient extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        }
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(urlStr);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
