package com.pd.shopinglist.utils

import android.text.Html
import android.text.Spanned

object HtmlManadger {

    fun getFromHtml(text: String): Spanned { // получаем html кодировку и превращаем в  стили текста
        //делаем проверку какая версия андроид установлена
        return if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(text)
        } else {
            Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        }
    }

    fun toHtml(text: Spanned): String { //превращаем текст типа Spanned  в html кодировку

        return if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.N) {
            Html.toHtml(text)
        } else {
            Html.toHtml(text, Html.FROM_HTML_MODE_COMPACT)
        }
    }
}