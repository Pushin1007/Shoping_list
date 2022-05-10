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
import androidx.recyclerview.widget.LinearLayoutManager
import com.pd.shopinglist.R
import com.pd.shopinglist.activities.MainApp
import com.pd.shopinglist.activities.NewNoteActivity
import com.pd.shopinglist.databinding.FragmentNoteBinding
import com.pd.shopinglist.db.MainViewModel
import com.pd.shopinglist.db.NoteAdapter
import com.pd.shopinglist.entities.NoteItem


class NoteFragment : BaseFragment(), NoteAdapter.Listener {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>//для того чтобы ждать результата
    private lateinit var adapter: NoteAdapter //адаптер для recycler view


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
                val editState = it.data?.getStringExtra(EDIT_STATE_KEY)
                if(editState=="update"){
                    mainViewModel.updateNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem) // отправляем по байтам как класс NoteItem
                }else{
                    mainViewModel.insertNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem) // отправляем по байтам как класс NoteItem
                }

            }
        }
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
            adapter.submitList(it)
        })
    }


    private fun initRcView() = with(binding) { //инициализируем адаптер и RV
        rcViewNote.layoutManager =
            LinearLayoutManager(activity) // layoutManager заметки будут идти по вертикали
        adapter = NoteAdapter(this@NoteFragment) //передаем лисенер фрагмента
        rcViewNote.adapter = adapter
    }

    companion object {
        const val NEW_NOTE_KEY = "new_note_key"
        const val EDIT_STATE_KEY = "edit_state_key"

        @JvmStatic
        fun newInstance() = NoteFragment()
    }

    override fun deleteItem(id: Int) {
        mainViewModel.deleteNote(id)
    }

    override fun onClickItem(note: NoteItem) {
        val intent = Intent(activity, NewNoteActivity::class.java).apply {
            putExtra(NEW_NOTE_KEY, note)
        }
        editLauncher.launch(intent)
    }
}