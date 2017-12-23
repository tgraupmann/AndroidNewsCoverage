package com.tagenigma.newscoverage;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
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

    private static final String[] sSources = {
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

    private static int sSourceIndex = sSources.length / 2;;

    private static String[] sLocations;

    private LinearLayout[] mParents = null;

    private WebView[] mWebViews = null;

    private void updatreLayout() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout rootLayout = mParents[sSourceIndex];
                setContentView(rootLayout);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebViews[sSourceIndex].canGoBack()) {
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebViews[sSourceIndex].canGoBack()) {
                mWebViews[sSourceIndex].goBack();
                return false;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null == sLocations) {
            sLocations = new String[sSources.length];
            for (int i = 0; i < sSources.length; ++i) {
                sLocations[i] = sSources[i];
            }
        }

        mParents = new LinearLayout[sSources.length];
        for (int i = 0; i < sSources.length; ++i) {
            LinearLayout rootLayout = new LinearLayout(MainActivity.this);
            mParents[i] = rootLayout;
            rootLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            rootLayout.setOrientation(LinearLayout.VERTICAL);

            TextView textView = new TextView(MainActivity.this);
            textView.setText("(" + i + ") " + sSources[i]);
            rootLayout.addView(textView);

            LinearLayout buttonRow = new LinearLayout(MainActivity.this);
            buttonRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            buttonRow.setOrientation(LinearLayout.HORIZONTAL);
            rootLayout.addView(buttonRow);

            Button btnDemocrat = new Button(MainActivity.this);
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.gravity = Gravity.LEFT;
            layout.weight = 1;
            btnDemocrat.setLayoutParams(layout);
            btnDemocrat.setText("D");
            btnDemocrat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveFarLeft();
                }
            });
            buttonRow.addView(btnDemocrat);

            Button btnLeft = new Button(MainActivity.this);
            layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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

            Button btnTop = new Button(MainActivity.this);
            layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.gravity = Gravity.CENTER_HORIZONTAL;
            layout.weight = 1;
            btnTop.setLayoutParams(layout);
            btnTop.setText("Top");
            btnTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = sSources[sSourceIndex];
                    sLocations[sSourceIndex] = url;
                    mWebViews[sSourceIndex].loadUrl(url);
                }
            });
            buttonRow.addView(btnTop);

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

            Button btnRepublican = new Button(MainActivity.this);
            layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.gravity = Gravity.RIGHT;
            layout.weight = 1;
            btnRepublican.setLayoutParams(layout);
            btnRepublican.setText("R");
            btnRepublican.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveFarRight();
                }
            });
            buttonRow.addView(btnRepublican);
        }

        mWebViews = new WebView[sSources.length];
        for (int i = 0; i < sSources.length; ++i) {
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

            String url = sLocations[i];
            webView.loadUrl(url);
        }

        updatreLayout();
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (int i = 0; i < sSources.length; ++i) {
            sLocations[i] = mWebViews[i].getUrl();
        }
    }

    private void moveFarLeft() {
        if (sSourceIndex > 0) {
            sSourceIndex = 0;
            updatreLayout();
        }
    }

    private void moveLeft() {
        if (sSourceIndex > 0) {
            sSourceIndex = sSourceIndex - 1;
            updatreLayout();
        }
    }

    private void moveRight() {
        if ((sSourceIndex + 1) < sSources.length) {
            sSourceIndex = sSourceIndex + 1;
            updatreLayout();
        }
    }

    private void moveFarRight() {
        if ((sSourceIndex + 1) < sSources.length) {
            sSourceIndex = sSources.length - 1;
            updatreLayout();
        }
    }
}
