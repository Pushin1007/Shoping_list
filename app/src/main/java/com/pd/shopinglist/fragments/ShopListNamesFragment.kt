package com.pd.shopinglist.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.pd.shopinglist.activities.MainApp
import com.pd.shopinglist.databinding.FragmentShopListNamesBinding
import com.pd.shopinglist.db.MainViewModel
import com.pd.shopinglist.dialogs.NewListDialog


class ShopListNamesFragment : BaseFragment() {
    private lateinit var binding: FragmentShopListNamesBinding


    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCkickNew() {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.Listener {
            override fun onClick(name: String) {
                Log.d("MyLog", "Name : $name")
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopListNamesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) { //функция запускается когда все view созданы
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun observer() { //observerкоторая будет следить за изменениями в базе данных, и будет каждый раз выдавать новый обновленный список
        mainViewModel.allNotes.observe(viewLifecycleOwner, {

        })
    }


    private fun initRcView() = with(binding) { //инициализируем адаптер и RV

    }

    companion object {


        @JvmStatic
        fun newInstance() = ShopListNamesFragment()
    }


}