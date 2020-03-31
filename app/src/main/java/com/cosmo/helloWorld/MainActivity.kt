package com.cosmo.helloWorld

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.w3c.dom.Text
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the PeripheralManager
 * For example, the snippet below will open a GPIO pin and set it to HIGH:
 *
 * val manager = PeripheralManager.getInstance()
 * val gpio = manager.openGpio("BCM6").apply {
 *     setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * }
 * gpio.value = true
 *
 * You can find additional examples on GitHub: https://github.com/androidthings
 */
class MainActivity : Activity() {

    private var mTimer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Hello World Started")


        setContentView(R.layout.activity_main)

        val sGenerator = MarqueeGenerator(
            "Hello World. My name is John Foushee".split("(?!^)".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray(),
            " ",
            3
        )
        val cGenerator = MarqueeGenerator(
            arrayOf(
                Color.RED,
                Color.RED,
                Color.RED,
                Color.YELLOW,
                Color.YELLOW,
                Color.YELLOW,
                Color.GREEN,
                Color.GREEN,
                Color.GREEN,
                Color.CYAN,
                Color.CYAN,
                Color.CYAN,
                Color.BLUE,
                Color.BLUE,
                Color.BLUE,
                Color.MAGENTA,
                Color.MAGENTA,
                Color.MAGENTA,
                Color.WHITE,
                Color.WHITE,
                Color.WHITE
            ),
            Color.BLACK,
            6
        )
        mTimer = Timer()
        mTimer!!.scheduleAtFixedRate(object : TimerTask() {

            private fun join(arr: ArrayList<String>): String {
                val strBuilder = StringBuilder()
                for (i in arr.indices) {
                    strBuilder.append(arr[i])
                }
                return strBuilder.toString()
            }

            private fun downcast(arr: ArrayList<Int>): IntArray {
                Log.d(TAG, Arrays.toString(arr.toTypedArray()))
                val response = IntArray(arr.size)
                for (i in arr.indices) {
                    response[arr.size - i - 1] = arr[i]
                }
                return response
            }

            override fun run() {
                Log.d(TAG, "This'll run 700 milliseconds later")
            }
        }, 0, 200)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mTimer != null) {
            mTimer!!.cancel()
            mTimer = null
        }
    }

    private class MarqueeGenerator<T> internal constructor(
        seedValues: Array<T>,
        private val emptyVal: T,
        prefixSize: Int
    ) {
        private var start = 0

        private val values: ArrayList<T>

        private fun getEmptyArray(size: Int, emptyVal: T): ArrayList<T> {
            val response = ArrayList<T>(size + 1)
            for (i in 0..size - 1) {
                response.add(i, emptyVal)
            }
            return response
        }

        init {
            this.values = getEmptyArray(seedValues.size + prefixSize, emptyVal)
            for (i in seedValues.indices) {
                this.values[prefixSize + i] = seedValues[i]
            }
        }

        internal fun next(read: Int): ArrayList<T> {
            Log.d(TAG, "Marquee Generator next() invoked")
            val response = getEmptyArray(read, this.emptyVal)
            val size = values.size
            if (start >= size) {
                start = 0
                return response
            }
            for (i in 0..read - 1) {
                response[i] = if (start + i < size) values[start + i] else emptyVal
            }
            start++
            return response
        }

    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        // Default LED brightness
        private val LEDSTRIP_BRIGHTNESS = 1
    }
}
