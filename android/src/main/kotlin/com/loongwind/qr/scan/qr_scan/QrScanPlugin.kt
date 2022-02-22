package com.loongwind.qr.scan.qr_scan

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.annotation.NonNull
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** QrScanPlugin */
class QrScanPlugin: FlutterPlugin, MethodCallHandler , ActivityAware{
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var  flutterPluginBinding :FlutterPlugin.FlutterPluginBinding
  private lateinit var activity:Activity

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    this.flutterPluginBinding = flutterPluginBinding
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "qr_scan")
    channel.setMethodCallHandler(this)
    Bus.setResultListener {
      channel.invokeMethod("result",  it)
    }
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    }else if(call.method == "scan"){
      activity.startActivity(Intent(activity, QrScanActivity::class.java))
      result.success("")
    }else if(call.method == Bus.TYPE_FINISH || call.method == Bus.TYPE_PAUSE || call.method == Bus.TYPE_RESUME){
      Bus.sendMsg(call.method)
      result.success("")
    }else if(call.method == "toast"){
      Toast.makeText(activity.applicationContext, call.arguments.toString(), Toast.LENGTH_SHORT).show()
      result.success("")
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override
  fun onAttachedToActivity(binding: ActivityPluginBinding) {
    this.activity = binding.activity
  }

  override fun onDetachedFromActivityForConfigChanges() {
    TODO("Not yet implemented")
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    TODO("Not yet implemented")
  }

  override fun onDetachedFromActivity() {
    TODO("Not yet implemented")
  }


}
