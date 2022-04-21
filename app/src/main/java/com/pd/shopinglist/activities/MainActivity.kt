package com.pd.shopinglist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pd.shopinglist.R
import com.pd.shopinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)// инициализируем   binding
        setContentView(binding.root)
        setBottomVavListener()
    }

    private fun setBottomVavListener() { // слушатель нажатий  в меню Bottom Navigation View
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> {
                    Log.d("MyLog","settings")
                }
                R.id.notes -> {
                    Log.d("MyLog","notes")
                }
                R.id.shop_list -> {
                    Log.d("MyLog","shop_list")
                }
                R.id.new_note -> {
                    Log.d("MyLog","new_note")
                }
            }
            true
        }
    }
}