package com.loongwind.qr.scan.qr_scan

import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.hmsscankit.RemoteView
import kotlin.math.min


class QrScanActivity : AppCompatActivity() {

    private var remoteView:RemoteView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scan)

        initRemoteView(savedInstanceState)
    }

    private fun initRemoteView(savedInstanceState: Bundle?) {
        val frameLayout = findViewById<FrameLayout>(R.id.rim)

        // 设置扫码识别区域，您可以按照需求调整参数。
        val dm = DisplayMetrics()
        val windowManager =
            application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = windowManager.defaultDisplay
        val outPoint = Point()
        // 可能有虚拟按键的情况
        display.getRealSize(outPoint)
        val mScreenWidth = outPoint.x
        val mScreenHeight = outPoint.y
        // 当前Demo扫码框的宽高是300dp。
        val SCAN_FRAME_SIZE = min(mScreenWidth, mScreenHeight) * 0.7
        val scanFrameSize = SCAN_FRAME_SIZE.toInt()
        val rect = Rect()
        print("width :$mScreenWidth  height:$mScreenHeight  SCAN_FRAME_SIZE:$SCAN_FRAME_SIZE")
        rect.left = (mScreenWidth / 2 - scanFrameSize / 2)
        rect.right = (mScreenWidth / 2 + scanFrameSize / 2)
        rect.top = (mScreenHeight / 2 - scanFrameSize / 2)
        rect.bottom = (mScreenHeight / 2 + scanFrameSize / 2)
        print(rect)
        // 初始化RemoteView，并通过如下方法设置参数:setContext()（必选）传入context、setBoundingBox()设置扫描区域、setFormat()设置识别码制式，设置完毕调用build()方法完成创建。通过setContinuouslyScan（可选）方法设置非连续扫码模式。
        remoteView =
            RemoteView.Builder().setContext(this).setBoundingBox(rect).setContinuouslyScan(true)
                .build()
        // 将自定义view加载到activity的frameLayout中。
        remoteView?.onCreate(savedInstanceState)
        val params = FrameLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        frameLayout.addView(remoteView, params)


        remoteView?.setOnResultCallback {
            // 获取到扫码结果HmsScan
            remoteView?.pauseContinuouslyScan()
            Bus.sendResult(it.firstOrNull()?.originalValue)
        }

        Bus.setListener {
            when(it){
                Bus.TYPE_PAUSE ->  remoteView?.pauseContinuouslyScan()
                Bus.TYPE_RESUME -> remoteView?.resumeContinuouslyScan()
                Bus.TYPE_FINISH -> finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // 侦听activity的onStart
        remoteView?.onStart()
    }
    override fun onResume() {
        super.onResume()
        // 侦听activity的onResume
        remoteView?.onResume()
    }
    override fun onPause() {
        super.onPause()
        // 侦听activity的onPause
        remoteView?.onPause()
    }
    override fun onStop() {
        super.onStop()
        // 侦听activity的onStop
        remoteView?.onStop()
    }
    override fun onDestroy() {
        super.onDestroy()
        // 侦听activity的onDestroy
        remoteView?.onDestroy()
        Bus.cleanListener()
    }
}