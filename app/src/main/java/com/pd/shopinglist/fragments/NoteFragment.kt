package com.pd.shopinglist.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.pd.shopinglist.R
import com.pd.shopinglist.activities.MainApp
import com.pd.shopinglist.activities.NewNoteActivity
import com.pd.shopinglist.databinding.FragmentNoteBinding
import com.pd.shopinglist.db.MainViewModel


class NoteFragment : BaseFragment() {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>//для того чтобы ждать результата

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCkickNew() {

        editLauncher.launch(Intent(activity, NewNoteActivity::class.java)) // запускаем интент
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onEditResult()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun onEditResult() {
        // инициализируем editLauncher , а также ждем результата от запущенной активити
        editLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                Log.d("MyLog", "title: ${it.data?.getStringExtra(TITLE_KEY)} ")
                Log.d("MyLog", "description: ${it.data?.getStringExtra(DESCRIPTION_KEY)} ")
            }
        }
    }

    companion object {
        const val TITLE_KEY = "title_key"
        const val DESCRIPTION_KEY = "description_key"

        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}