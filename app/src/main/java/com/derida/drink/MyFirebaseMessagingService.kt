package com.derida.drink
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.firebase.messaging.RemoteMessage
import android.content.Intent

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var broadcaster: LocalBroadcastManager? = null

    override fun onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        handleMessage(remoteMessage)
    }

    private fun handleMessage(remoteMessage: RemoteMessage) {
        //1
        val handler = Handler(Looper.getMainLooper())

        //2
        handler.post(Runnable {
            Toast.makeText(baseContext, "Check Notification",
                Toast.LENGTH_LONG).show()
        }
        )

        remoteMessage.notification?.let {
            val intent = Intent("MyData")
            intent.putExtra("message", it.body);
            broadcaster?.sendBroadcast(intent);
        }

    }



}