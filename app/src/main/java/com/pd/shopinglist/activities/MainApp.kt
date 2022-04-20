package com.pd.shopinglist.activities

import android.app.Application
import com.pd.shopinglist.db.MainDataBase

class MainApp:Application() { // наследуемся от главного класса приложения, как только запускается приложение
    val database by lazy { // мы получаем доступ к бала в любом месте приложения, в любой активити
        MainDataBase.getDataBase(this)
    }
}