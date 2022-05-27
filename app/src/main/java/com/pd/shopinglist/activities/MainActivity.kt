package com.pd.shopinglist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pd.shopinglist.R
import com.pd.shopinglist.databinding.ActivityMainBinding
import com.pd.shopinglist.dialogs.NewListDialog
import com.pd.shopinglist.fragments.FragmentManager
import com.pd.shopinglist.fragments.NoteFragment
import com.pd.shopinglist.fragments.ShopListNamesFragment

class MainActivity : AppCompatActivity(), NewListDialog.Listener {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)// инициализируем   binding
        setContentView(binding.root)
        FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
        setBottomVavListener()
    }

    private fun setBottomVavListener() { // слушатель нажатий  в меню Bottom Navigation View
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> {
                    Log.d("MyLog", "settings")
                }
                R.id.notes -> {
                    FragmentManager.setFragment(NoteFragment.newInstance(), this)
                }
                R.id.shop_list -> {
                    FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
                }
                R.id.new_note -> {
                    FragmentManager.currentFragment?.onCkickNew()

                }
            }
            true
        }
    }

    override fun onClick(name: String) {
        Log.d("MyLog", "Name :$name")
    }
}