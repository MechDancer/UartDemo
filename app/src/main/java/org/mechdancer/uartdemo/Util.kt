package org.mechdancer.uartdemo

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

private val hexArray = "0123456789ABCDEF".toCharArray()

fun ByteArray.toAsciiString() = StandardCharsets.US_ASCII.decode(ByteBuffer.wrap(this))

fun ByteArray.toHexString(separator: CharSequence = ","): String {
    val sb = StringBuilder()
    for (i in indices) {
        if (i > 0) {
            sb.append(separator)
        }
        val v: Int = this[i].toInt() and 255
        sb.append(hexArray[v shr 4])
        sb.append(hexArray[v and 15])
    }
    return sb.toString()
}