package com.hd

import android.support.v7.app.AppCompatActivity
import com.hd.serialport.listener.SerialPortMeasureListener
import com.hd.serialport.method.DeviceMeasureController
import com.hd.serialport.param.SerialPortMeasureParameter
import java.io.OutputStream


/**
 * Created by hd on 2019-07-08 .
 *
 */

class KotlinWrite : AppCompatActivity() {
    
    fun testA(){
        val customTag= "自定义tag"
        DeviceMeasureController.measure(SerialPortMeasureParameter(tag = customTag, devicePath = "/dev/ttyS5", baudRate = 115200),object : SerialPortMeasureListener{
            
            override fun measuring(tag: Any?, path: String, data: ByteArray) {
                println(path)
                println(data)
                //根据tag响应
            }
    
            override fun write(tag: Any?, outputStream: OutputStream) {
                    println(outputStream)
                //根据tag响应
            }
    
            override fun measureError(tag: Any?, message: String) {
                println("Error Message $message")
                //根据tag响应
            }
        })

        
        //停止指定的串口，不传或者传null停止所有已经打开的串口
        DeviceMeasureController.write(listOf("1F001D091C".decodeHex()),customTag)
    }
    fun String.decodeHex(): ByteArray {
        check(length % 2 == 0) { "Must have an even length" }

        return chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
    }
}
