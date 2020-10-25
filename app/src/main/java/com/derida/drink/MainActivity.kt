package com.derida.drink

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_retrieve_token.setOnClickListener {
            if (checkGooglePlayServices()) {

            } else {
                //You won't be able to send notifications to this device
                Log.w("ALOHA", "Device doesn't have google play services")
            }
            // 1
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    // 2
                    if (!task.isSuccessful) {
                        Log.w("ALOHA", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }
                    // 3
                    val token = task.result?.token

                    // 4
                    Log.d("ALOHA", token)
                    Toast.makeText(baseContext, token, Toast.LENGTH_LONG).show()
                })
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, IntentFilter("MyData"))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver)

    }

    private fun checkGooglePlayServices(): Boolean {
        // 1
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        // 2
        return if (status != ConnectionResult.SUCCESS) {
            Log.e("ALOHA", "Error")
            // ask user to update google play services and manage the error.
            false
        } else {
            // 3
            Log.i("ALOHA", "Google play services updated")
            true
        }
    }

    private val messageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            text_view_notification.text = intent.extras?.getString("message")
        }
    }



}