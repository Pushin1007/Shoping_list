package com.pd.shopinglist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.pd.shopinglist.R
import com.pd.shopinglist.databinding.ActivityNewNoteBinding
import com.pd.shopinglist.entities.NoteItem
import com.pd.shopinglist.fragments.NoteFragment.Companion.EDIT_STATE_KEY
import com.pd.shopinglist.fragments.NoteFragment.Companion.NEW_NOTE_KEY
import java.text.SimpleDateFormat
import java.util.*

class NewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewNoteBinding
    private var note: NoteItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)// подключаем байдинг
        actionBarSettings()
        getNote()
    }

    private fun getNote() {
        val serializableNote = intent.getSerializableExtra(NEW_NOTE_KEY)
        if (serializableNote != null) {
            note = serializableNote as NoteItem
            fillNote()
        }


    }

    private fun fillNote() =
        with(binding) { // эта функция запустится только если Note не равен null

            edTitle.setText(note?.title)
            edDescription.setText(note?.content)

        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.id_save) { // при нажатии на кнопку мы передаем результат

            setMainResult()

        } else if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)

    }

    private fun setMainResult() {
        var editState = "new"
        val tempNote: NoteItem?

        if (note == null) {// проверка на то создаем заметку или обновляем
            tempNote = createNoteItem()
        } else {
            editState = "update"
            tempNote = updateNote()
        }
        val intent = Intent().apply {
            putExtra(
                NEW_NOTE_KEY,
                tempNote
            )//взяли новую заметку, заполнили поля, сериализовали и поместили в наш интент
            putExtra(
                EDIT_STATE_KEY,
                editState
            )//взяли новую заметку, заполнили поля, сериализовали и поместили в наш интент
        }
        setResult(RESULT_OK, intent) // отправляем результат
        finish() //закрываем активити после передачи данных
    }

    private fun updateNote(): NoteItem? = with(binding) {
        return note?.copy(
            title = edTitle.text.toString(),
            content = edDescription.text.toString()
        )
    }

    private fun createNoteItem(): NoteItem { // заполняем нашу заметку
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            binding.edDescription.text.toString(),
            getCurrentTime(),
            ""
        )

    }

    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat( //указываем в каком формате мы хотим получить время
            "hh:mm - dd/MM/yyyy",
            Locale.getDefault()
        )
        return formatter.format(Calendar.getInstance().time)
    }

    private fun actionBarSettings() {//подключаем кнопку назад в actionBar
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }
}