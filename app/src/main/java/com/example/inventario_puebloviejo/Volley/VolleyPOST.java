package com.example.inventario_puebloviejo.Volley;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
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

public class VolleyPOST extends Volley implements Response.Listener<String>, Response.ErrorListener {
    private VolleyRequest request;
    private Context context;
    private CallBack callback;

    public VolleyPOST(String url, Context context, JSONObject data, CallBack callback) {
        this.callback = callback;
        this.context = context;

        request = new VolleyRequest(Request.Method.POST, url, this::onResponse,this::onErrorResponse){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return data.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
        close();
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            callback.callback(jsonObject);

        } catch (JSONException e) {
            System.out.println(response);
            Log.e("GET", e.getMessage());
        }
    }

    public void start(){ //Iniciamos el proceso de la consulta
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

    public void close(){ //Cancela la ejecucion asociado del request
        if (request != null){
            request.cancel();
        }
    }
    protected static class VolleyRequest extends StringRequest{
        public VolleyRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }
    }
}