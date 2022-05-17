package com.pd.shopinglist.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.pd.shopinglist.R
import com.pd.shopinglist.databinding.ActivityNewNoteBinding
import com.pd.shopinglist.entities.NoteItem
import com.pd.shopinglist.fragments.NoteFragment.Companion.EDIT_STATE_KEY
import com.pd.shopinglist.fragments.NoteFragment.Companion.NEW_NOTE_KEY
import com.pd.shopinglist.utils.HtmlManadger
import com.pd.shopinglist.utils.MyTouchListener
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
        init()
        onClickColorPicker()
        actionMenuCallback()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() { //инициализируем функцию перетаскивания
        binding.colorPicker.setOnTouchListener(MyTouchListener()) // делаем перетаскивание colorPicker
    }

    private fun onClickColorPicker() = with(binding) {  // делаем слушатель нажатий на цвета

        imRed.setOnClickListener { setColorForSelectedText(R.color.picker_red) }
        imBlack.setOnClickListener { setColorForSelectedText(R.color.picker_black) }
        imBlue.setOnClickListener { setColorForSelectedText(R.color.picker_blue) }
        imGreen.setOnClickListener { setColorForSelectedText(R.color.picker_green) }
        imYellow.setOnClickListener { setColorForSelectedText(R.color.picker_yellow) }
        imOrange.setOnClickListener { setColorForSelectedText(R.color.picker_orange) }
    }


    private fun getNote() {
        val serializableNote = intent.getSerializableExtra(NEW_NOTE_KEY)
        if (serializableNote != null) {
            note = serializableNote as NoteItem
            fillNote()
        }


    }

    private fun fillNote() = // функция заполения заметки
        with(binding) { // эта функция запустится только если Note не равен null

            edTitle.setText(note?.title)
            edDescription.setText(
                HtmlManadger.getFromHtml(note?.content!!).trim()
            )// берем из бызы html и превращаем его в Spanneble текст

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
        } else if (item.itemId == R.id.id_bold) {
            setBoldForSelectedText()
        } else if (item.itemId == R.id.id_color) {
            if (binding.colorPicker.isShown) { // isShown показаг ли на экране
                closeColorPicker()
            } else {
                openColorPicker()
            }
        }
        return super.onOptionsItemSelected(item)

    }

    //функция которая делает выделенны текст жирным
    private fun setBoldForSelectedText() = with(binding) {
        val startPosition = edDescription.selectionStart // начальная позиция выделенного текста
        val endPosition = edDescription.selectionEnd // конечная позиция выделенного текста
        val styles = edDescription.text.getSpans(
            startPosition,
            endPosition,
            StyleSpan::class.java
        ) // функция говорит сколько стилей в выделенном тексте

        //если выбранное слово уже жирным шрифтом

        var boldStyle: StyleSpan? = null

        if (styles.isNotEmpty()) {
            edDescription.text.removeSpan(styles[0]) // если есть стиль, то обнудяем его
        } else {
            boldStyle = StyleSpan(Typeface.BOLD) // если нет стиля то делаем жирным
        }
        edDescription.text.setSpan(
            boldStyle,
            startPosition,
            endPosition,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )// тип добавления
        edDescription.text.trim() //функция удаляет все пробелы
        edDescription.setSelection(startPosition)//возвращаем курсор в начальную позицию
    }

    //функция которая делает цвет текста
    private fun setColorForSelectedText(colorId: Int) = with(binding) {
        val startPosition = edDescription.selectionStart // начальная позиция выделенного текста
        val endPosition = edDescription.selectionEnd // конечная позиция выделенного текста
        val styles = edDescription.text.getSpans(
            startPosition,
            endPosition,
            ForegroundColorSpan::class.java
        )
        if (styles.isNotEmpty()) edDescription.text.removeSpan(styles[0])
        edDescription.text.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this@NewNoteActivity, colorId)),
            startPosition,
            endPosition,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )// тип добавления
        edDescription.text.trim() //функция удаляет все пробелы
        edDescription.setSelection(startPosition)//возвращаем курсор в начальную позицию
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
            content = HtmlManadger.toHtml(edDescription.text)
        )
    }

    private fun createNoteItem(): NoteItem { // заполняем нашу заметку
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            HtmlManadger.toHtml(binding.edDescription.text), // при создании мы записываем в базу в виде html
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

    private fun actionBarSettings() {//подключаем кнопку назад в actionBara
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun openColorPicker() {
        binding.colorPicker.visibility = View.VISIBLE //делаем видимым колор пиккер
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.open_color_picker)
        binding.colorPicker.startAnimation(openAnim)
    }

    private fun closeColorPicker() {

        val openAnim = AnimationUtils.loadAnimation(this, R.anim.close_color_picker)
        openAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.colorPicker.visibility = View.GONE //скрываем когда заканчивается анимация
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
        binding.colorPicker.startAnimation(openAnim)
    }

    private fun actionMenuCallback() { //коллбек который ожидает появление верхнего меню и стирает его
        val actionCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                p1?.clear() //очищаем наше меню
                return true
            }

            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                p1?.clear() //очищаем наше меню
                return true
            }

            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
                return true
            }

            override fun onDestroyActionMode(p0: ActionMode?) {

            }

        }
        binding.edDescription.customSelectionActionModeCallback = actionCallback //коллбек который следит за выделением
    }
}