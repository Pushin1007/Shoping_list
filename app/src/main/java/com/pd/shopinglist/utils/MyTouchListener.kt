package com.pd.shopinglist.utils

import android.view.MotionEvent
import android.view.View

//специальный класс который нужен для перетаскивания colorPicker
class MyTouchListener : View.OnTouchListener {
    //переменные которые хранят позиции
    var xDelta = 0.0f
    var yDelta = 0.0f

    override fun onTouch(v: View, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> { //когда отпустили элемент
                xDelta = v.x - event.rawX
                yDelta = v.y - event.rawY
            }
            MotionEvent.ACTION_MOVE -> { //когда движем наш элемент
                v.x = xDelta + event.rawX
                v.y = yDelta + event.rawY
            }
        }
        return true
    }

}
