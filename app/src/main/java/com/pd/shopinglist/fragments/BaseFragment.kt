package com.pd.shopinglist.fragments

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {//базовый фрагмент от которого будут наследоваться все наши фрагменты
    abstract fun onCkickNew()
}