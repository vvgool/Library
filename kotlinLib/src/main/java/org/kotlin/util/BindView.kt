package org.kotlin.util

import android.app.Activity
import android.app.Fragment
import android.view.View
import android.view.ViewGroup
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
/**
 * Created by wiesen on 2016/11/23.
 */
class BindView<V> (val id:Int):ReadOnlyProperty<Any,V>{
    private var mContext:Any? = null

    constructor(id: Int, context:Any):this(id){
        mContext = context
    }
    override fun getValue(thisRef: Any, property: KProperty<*>): V {
        val context = mContext?:thisRef
        return when (context){
                is Activity -> context.findViewById(id) as V
                is Fragment -> context.activity.findViewById(id) as V
                is ViewGroup -> context.findViewById(id) as V
                is View -> context.findViewById(id) as V
                else -> throw Exception("the context is error")
            }
    }

}