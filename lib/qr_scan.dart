
import 'dart:async';

import 'package:flutter/services.dart';

class QrScan {
  static const MethodChannel _channel = MethodChannel('qr_scan');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static void scan() async{
    await _channel.invokeMethod('scan');
  }
  static Future pause() async{
    return _channel.invokeMethod('pause');
  }
  static Future resume() async{
    return _channel.invokeMethod('resume');
  }
  static void finish() async{
    await _channel.invokeMethod('finish');
  }
  static Future toast(String msg) async{
    return _channel.invokeMethod('toast', msg);
  }

  static void setOnResultCallback(Function(String result) callback){
    _channel.setMethodCallHandler((call) async{
      if(call.method == "result"){
        callback(call.arguments);
      }
      return;
    });
  }
}
