package com.example.inventario_puebloviejo;

import android.content.Context;
import android.content.Intent;

import com.example.inventario_puebloviejo.ui.slideshow.SlideshowFragment;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, SlideshowFragment.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
    }
}
