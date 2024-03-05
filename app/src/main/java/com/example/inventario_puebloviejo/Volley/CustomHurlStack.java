package com.example.inventario_puebloviejo.Volley;

import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class CustomHurlStack extends HurlStack {
    private final SSLContext mSslContext;
    public CustomHurlStack(SSLContext sslContext) {
        super();
        mSslContext = sslContext;
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection connection = super.createConnection(url);
        if ("https".equals(url.getProtocol()) && mSslContext != null) {
            ((HttpsURLConnection) connection).setSSLSocketFactory(mSslContext.getSocketFactory());
        }
        return connection;
    }
}