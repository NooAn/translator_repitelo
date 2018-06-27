package ru.bit.espanola

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action?.equals(Intent.ACTION_BOOT_COMPLETED) ?: return) {
            Log.e("LOG", "action boot")
            context?.applicationContext?.registerReceiver(ScreenReceiver(), IntentFilter(Intent.ACTION_SCREEN_ON))
        }
    }
}

class ScreenReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_SCREEN_ON.equals(intent?.action)) {
            Log.e("LOG", "screen on")
            context!!.startActivity(Intent(context, BootActivity::class.java))
        }
    }
}