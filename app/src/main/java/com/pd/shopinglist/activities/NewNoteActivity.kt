package com.pd.shopinglist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.pd.shopinglist.R
import com.pd.shopinglist.databinding.ActivityNewNoteBinding
import com.pd.shopinglist.fragments.NoteFragment.Companion.DESCRIPTION_KEY
import com.pd.shopinglist.fragments.NoteFragment.Companion.TITLE_KEY

class NewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)// подключаем байдинг
        actionBarSettings()
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
        val intent = Intent().apply {
            putExtra(
                TITLE_KEY,
                binding.edTitle.toString()
            )//взяли текст из вью и поместили в наш интент
            putExtra(
                DESCRIPTION_KEY,
                binding.edDescription.toString()
            )//взяли текст из вью и поместили в наш интент
        }
        setResult(RESULT_OK,intent) // отправляем результат
        finish() //закрываем активити после передачи данных
    }

    private fun actionBarSettings() {//подключаем кнопку назад в actionBar
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }
}