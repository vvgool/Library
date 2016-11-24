package org.kotlin.util

import android.widget.Toast
import org.kotlin.app.WApplication

/**
 * Created by wiesen on 2016/11/24.
 */
class ToastUtil {
    companion object{
        fun show(msg:Any){
            Toast.makeText(WApplication.getInstance(),msg.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}