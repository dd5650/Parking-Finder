package com.parkingnow.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    public static final String APP_URL = "https://dd5650.github.io/parkingnow/";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private WebView webView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private GeolocationPermissions.Callback mGeoCallback;
    private String mGeoOrigin;
    private long backPressedTime = 0;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 내비 사용 시 화면 꺼짐 방지
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        webView = findViewById(R.id.webView);

        initWebView();
        initSwipeRefresh();

        // 메인 웹앱 로드
        if (savedInstanceState == null) {
            webView.loadUrl(APP_URL);
        } else {
            webView.restoreState(savedInstanceState);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setDatabaseEnabled(true);
        ws.setGeolocationEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(false);
        ws.setDisplayZoomControls(false);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        ws.setUserAgentString(ws.getUserAgentString() + " ParkingNowApp/1.0.0");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                } else {
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                mGeoCallback = callback;
                mGeoOrigin = origin;
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, LOCATION_PERMISSION_REQUEST_CODE);
                } else {
                    callback.invoke(origin, true, false);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                return handleCustomUrl(url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return handleCustomUrl(url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeResources(R.color.primary);
        swipeRefreshLayout.setOnRefreshListener(() -> webView.reload());
    }

    private boolean handleCustomUrl(String url) {
        if (url == null) return false;

        // 전화 걸기
        if (url.startsWith("tel:")) {
            try {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "전화 앱을 열 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        // 카카오내비, 네이버지도, 티맵 등 커스텀 스킴
        if (url.startsWith("kakaonavi:") || url.startsWith("nmap:") || url.startsWith("tmap:")) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            } catch (Exception e) {
                // 앱 미설치 시 플레이스토어 안내
                if (url.startsWith("kakaonavi:")) {
                    openPlayStore("com.locnall.KimGiSa", "카카오내비");
                } else if (url.startsWith("nmap:")) {
                    openPlayStore("com.nhn.android.nmap", "네이버 지도");
                } else if (url.startsWith("tmap:")) {
                    openPlayStore("com.skt.tmap.ku", "티맵");
                }
                return true;
            }
        }

        // intent:// 스킴 처리
        if (url.startsWith("intent:")) {
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                if (intent != null) {
                    PackageManager pm = getPackageManager();
                    if (intent.resolveActivity(pm) != null) {
                        startActivity(intent);
                        return true;
                    }
                    String fallbackUrl = intent.getStringExtra("browser_fallback_url");
                    if (fallbackUrl != null) {
                        webView.loadUrl(fallbackUrl);
                        return true;
                    }
                    String packageName = intent.getPackage();
                    if (packageName != null) {
                        openPlayStore(packageName, "해당 앱");
                        return true;
                    }
                }
            } catch (Exception ignored) {}
            return true;
        }

        // 일반 웹 주소는 내부 웹뷰에서 탐색
        return !url.startsWith("http://") && !url.startsWith("https://");
    }

    private void openPlayStore(String packageName, String appName) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
            startActivity(intent);
        } catch (Exception e) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            startActivity(intent);
        }
        Toast.makeText(this, appName + " 설치 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            boolean granted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (mGeoCallback != null && mGeoOrigin != null) {
                mGeoCallback.invoke(mGeoOrigin, granted, false);
            }
            if (granted) {
                webView.reload();
            } else {
                Toast.makeText(this, "위치 권한을 허용해야 현재 위치 기준 주차장을 찾을 수 있습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            super.onBackPressed();
        } else {
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로 버튼을 한 번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }
}
