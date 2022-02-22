package com.loongwind.qr.scan.qr_scan

object Bus {
    const val TYPE_PAUSE = "pause"
    const val TYPE_RESUME = "resume"
    const val TYPE_FINISH = "finish"

    private var listener : ((type:String) -> Unit)? = null
    private var resultListener : ((type:String) -> Unit)? = null

    fun setListener(listener : ((type:String) -> Unit)?){
        this.listener = listener
    }
    fun setResultListener(listener : ((result:String) -> Unit)?){
        this.resultListener = listener
    }
    fun cleanListener(){
        this.listener = null
    }

    fun sendMsg(type: String){
        listener?.invoke(type)
    }
    fun sendResult(result: String?){
        if(result != null){
            resultListener?.invoke(result)
        }
    }
}