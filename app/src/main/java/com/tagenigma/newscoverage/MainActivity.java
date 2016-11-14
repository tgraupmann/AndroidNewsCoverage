package com.tagenigma.newscoverage;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by tgraupmann on 11/13/2016.
 */
public class MainActivity extends Activity {

    private float mX;

    private final String[] mSources = {
            "http://www.huffingtonpost.com/",
            "http://www.wsj.com/",
            "http://www.nytimes.com/",
            "http://www.cbsnews.com/",
            "http://www.latimes.com/",
            "https://www.washingtonpost.com/",
            "http://time.com/",
            "http://www.npr.org/sections/news/",
            "http://www.bbc.com/news",
            "http://www.usatoday.com/",
            "http://www.nbcnews.com/",
            "http://abcnews.go.com/",
            "http://www.cnn.com/",
            "http://www.foxnews.com/",
    };

    private int mSourceIndex = 0;

    private LinearLayout[] mParents = null;

    private WebView[] mWebViews = null;

    private void updatreLayout() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout rootLayout = mParents[mSourceIndex];
                setContentView(rootLayout);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mParents = new LinearLayout[mSources.length];
        for (int i = 0; i < mSources.length; ++i) {
            LinearLayout rootLayout = new LinearLayout(MainActivity.this);
            mParents[i] = rootLayout;
            rootLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            rootLayout.setOrientation(LinearLayout.VERTICAL);

            TextView textView = new TextView(MainActivity.this);
            textView.setText("("+i+") "+mSources[i]);
            rootLayout.addView(textView);

            LinearLayout buttonRow = new LinearLayout(MainActivity.this);
            buttonRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            buttonRow.setOrientation(LinearLayout.HORIZONTAL);
            rootLayout.addView(buttonRow);

            Button btnLeft = new Button(MainActivity.this);
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.gravity = Gravity.LEFT;
            layout.weight = 1;
            btnLeft.setLayoutParams(layout);
            btnLeft.setText("More Left");
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveLeft();
                }
            });
            buttonRow.addView(btnLeft);

            Button btnRight = new Button(MainActivity.this);
            layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.gravity = Gravity.RIGHT;
            layout.weight = 1;
            btnRight.setLayoutParams(layout);
            btnRight.setText("More Right");
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveRight();
                }
            });
            buttonRow.addView(btnRight);
        }

        mWebViews = new WebView[mSources.length];
        for (int i = 0; i < mSources.length; ++i) {
            WebView webView = new WebView(this);

            mParents[i].addView(webView);

            mWebViews[i] = webView;

            webView.getSettings().setJavaScriptEnabled(false);

            webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; <Android Version>; <Build Tag etc.>) AppleWebKit/<WebKit Rev> (KHTML, like Gecko) Chrome/<Chrome Rev> Mobile Safari/<WebKit Rev>");

            webView.getSettings().setLoadsImagesAutomatically(false);

            webView.getSettings().setBuiltInZoomControls(true);

            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);

            webView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                }
            });

            webView.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(MainActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                }
            });

            webView.loadUrl(mSources[i]);
        }

        mSourceIndex = mSources.length / 2;
        updatreLayout();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updatreLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatreLayout();
    }

    private void moveLeft() {
        if (mSourceIndex > 0) {
            mSourceIndex = mSourceIndex - 1;
            updatreLayout();
        }
    }

    private void moveRight() {
        if ((mSourceIndex + 1) < mSources.length) {
            mSourceIndex = mSourceIndex + 1;
            updatreLayout();
        }
    }
}
