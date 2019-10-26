package org.mechdancer.uartdemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var uart: Uart

    private var thread: Thread? = null

    @Volatile
    private var running = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        uart = Uart(this)
        uart.init(Uart.Config())
        thread = thread {
            running = true
            while (running)
                runOnUiThread {
                    Toast.makeText(this, String(uart.read(4096)), Toast.LENGTH_LONG).show()
                }
            running = false
        }
    }

    override fun onDestroy() {
        running = false
        thread = null
        uart.close()
        super.onDestroy()
    }
}
