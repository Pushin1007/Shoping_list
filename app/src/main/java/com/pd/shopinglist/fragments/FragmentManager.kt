package com.pd.shopinglist.fragments

import androidx.appcompat.app.AppCompatActivity
import com.pd.shopinglist.R

object FragmentManager {
    // object позволяет использовать функции данного класса без его инициализации

    var currentFragment: BaseFragment? = null // идентификатор текущего фрагмента

    //функция с помощью которой мы будем переключаться между фрагментами
    fun setFragment(newFragment: BaseFragment, activity: AppCompatActivity) {

        // с помощью этого мы можем удалять, заменять ,менять местами фрагменты
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.placeHolder,
            newFragment
        ) // помещаем в наш фрейм лайаут наш фрагмент
        transaction.commit()//применим все предыдущие действия
        currentFragment = newFragment
    }


}