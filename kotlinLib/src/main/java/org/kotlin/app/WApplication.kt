package org.kotlin.app

import android.app.Application
import com.wepie.snake.base.NotNullSingleValueVar

/**
 * Created by wiesen on 2016/11/24.
 */
class WApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    companion object{
        private var mInstance :WApplication by NotNullSingleValueVar()

        fun getInstance():WApplication = mInstance
    }
}