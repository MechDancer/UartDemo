package org.mechdancer.uartdemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

open class MainActivity : AppCompatActivity() {

    private lateinit var uart: Uart


    fun Any.toast(prefix: String) = Toast.makeText(this@MainActivity, "$prefix: ${toString()}", Toast.LENGTH_SHORT).show()
    fun Any.log(prefix: String) = Log.w("Uart", "$prefix: ${toString()}")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        uart = Uart(this)
        uart.requestPermission().log("Permission")
        uart.getAndOpenDevice().log("Device")
        uart.initUartDevice().log("Init")
        uart.setConfig(Uart.Config(
            baudRate = Uart.BaudRate._19200,
            parity = 2
        )).log("Config")

        thread {
            while (true) {
                uart.read()?.let {
                    it.toAsciiString().log("Ascii")
                    it.toHexString().log("Hex")
                }
            }
        }

    }


    override fun onDestroy() {
        uart.close()
        super.onDestroy()
    }
}
