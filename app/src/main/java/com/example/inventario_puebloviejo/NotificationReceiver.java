package com.example.inventario_puebloviejo;

import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends  BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, Inicio_Sesion.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
