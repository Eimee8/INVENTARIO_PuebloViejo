package com.example.inventario_puebloviejo.Volley;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class VolleyGET extends Volley implements Response.Listener<String>, Response.ErrorListener{
    private final Context context;
    private final VolleyRequest request;
    private final CallBack callback;

    public VolleyGET(String url, Context context, CallBack callBack) {
        this.context = context;
        this.callback = callBack;
        this.request = new VolleyRequest(Request.Method.GET, url, this::onResponse, this::onErrorResponse);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println(error);
        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
        error.printStackTrace();
        //Log.e("Error Volley", error.getMessage());
        close();
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            callback.callback(jsonObject);

        } catch (JSONException e) {

            Log.e("GET", e.getMessage());
        }
    }

    public void start(){
        SSLContext sslContext = null;

        try {
            // Crear un TrustManager que confíe en todos los certificados
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[0];
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {  }
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {  }
                    }
            };

            // Instalar el TrustManager
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        newRequestQueue(context,new CustomHurlStack(sslContext)).add(request);
    }

    public void close(){
        request.cancel();
    }
    protected static class VolleyRequest extends StringRequest{
        public VolleyRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }
    }
}
