package com.hd

import android.os.Bundle
import android.serialport.SerialPortFinder
import android.support.v7.app.AppCompatActivity
import com.aio.usbserialport.listener.ReceiveResultListener
import com.aio.usbserialport.method.AIODeviceMeasure
import com.aio.usbserialport.result.ParserResult
import com.hd.serialport.listener.SerialPortMeasureListener
import com.hd.serialport.method.DeviceMeasureController
import com.hd.serialport.param.SerialPortMeasureParameter
import com.hd.serialport.utils.L
import com.hd.usbserialport.sample.AIODeviceType
import java.io.OutputStream


/**
 * Created by hd on 2017/8/29 .
 *
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val paths=SerialPortFinder().allDevicesPath

        KotlinWrite().testA()
 // testA()
    }

    override fun onResume() {
        super.onResume()
        AIODeviceMeasure.with(AIODeviceType.UNKNOWN_DEVICE, object : ReceiveResultListener {
            override fun receive(parserResult: ParserResult) {
                L.d("receivekkk : $parserResult")
            }

            override fun error(msg: String) {
                L.d("error : $msg")
            }
        }).startMeasure()
    }

    override fun onPause() {
        super.onPause()
        AIODeviceMeasure.stopMeasure()
    }

    fun testA(){
        val customTag= "自定义tag"
        DeviceMeasureController.measure(SerialPortMeasureParameter(tag = customTag, devicePath = "/dev/ttyS5" , baudRate = 115200),object :
            SerialPortMeasureListener {

            override fun measuring(tag: Any?, path: String, data: ByteArray) {

                //根据tag响应
            }

            override fun write(tag: Any?, outputStream: OutputStream) {

                //根据tag响应
            }

            override fun measureError(tag: Any?, message: String) {
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
