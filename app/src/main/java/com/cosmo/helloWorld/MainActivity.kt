package com.cosmo.helloWorld

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Hello World Started")

        setContentView(R.layout.activity_main)

        buttonRight.setOnClickListener {
            changeText("Hola Tomás!")
        }

        buttonLeft.setOnClickListener {
            changeText("Hola Mateo!")
        }

        timer = Timer();
    }

    fun changeText(newText: String) {
        Toast.makeText(
            this@MainActivity,
            "Click " + newText + " !",
            Toast.LENGTH_SHORT
        ).show()

        textView.setText(newText)

        val timerTask = object : TimerTask() {
            override fun run() {
                this@MainActivity.runOnUiThread(Runnable {
                    textView.setText(R.string.start_text)
                });
            }
        }
        timer.schedule(timerTask, 4000)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        timer.purge()
    }

    companion object {
        private lateinit var timer: Timer
        private val TAG = MainActivity::class.java.simpleName
    }
}
