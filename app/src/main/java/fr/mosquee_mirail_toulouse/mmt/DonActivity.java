package fr.mosquee_mirail_toulouse.mmt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class DonActivity extends AppCompatActivity {
    private WebView mWebView;
    private final static String link = "http://www.mosquee-mirail-toulouse.fr/le-projet/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don);
        mWebView = (WebView) findViewById(R.id.activity_don_webview);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mWebView.loadUrl(link);

        setTitle("Faire un don");
    }
}
