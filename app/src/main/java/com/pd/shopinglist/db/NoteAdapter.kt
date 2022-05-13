package com.pd.shopinglist.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.pd.shopinglist.R
import com.pd.shopinglist.databinding.NoteListItemBinding
import com.pd.shopinglist.entities.NoteItem
import com.pd.shopinglist.utils.HtmlManadger

class NoteAdapter(private val listener: Listener) :
    ListAdapter<NoteItem, NoteAdapter.ItemHolder>(ItemComparator()) {

/*
 класс ItemHolder специальный класс который будет создавать и содержать разметку note_list_item
внутрь передаем  разметку itemView, которрую потом необходимо надуть с помощью LayoutInflater
Данный класс можно вынести в отдельный файл
 */

    //функция будет создавать для каждой заметки свой собственный  ItemHolder и будет создавать разметку
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    //функция будет заполнять разметку созданную выше
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener) //каждый раз когда заполняется элемент добавится 50 слушателей
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding =
            NoteListItemBinding.bind(itemView)//тоесть пожключить вью itemView к нашему классу NoteListItemBinding

        //будем заполнять нашу разметку из нашего класса  NoteItem
        fun setData(note: NoteItem, listener: Listener) = with(binding) {
            tvTitle.text = note.title
            tvDescription.text = HtmlManadger.getFromHtml(note.content).trim() // берем из бызы html и превращаем его в Spanneble текст
            tvDate.text = note.time
            // при нажатии на кнопку
            btnDelete.setOnClickListener {
                listener.deleteItem(note.id!!) // удаляем заметку по идентификатору
            }
            itemView.setOnClickListener {  //слушатель нажатий на весь элемент
                listener.onClickItem(note)
            }
        }

        companion object {
            //делвем статическию функцию, тогда мы сможем инициализировать наш класс
            fun create(parent: ViewGroup): ItemHolder {
//LayoutInflater будет брать нашу разметку и загружать ее в память и сможем ей пользоваться. И так для каждого элемента
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.note_list_item, parent, false)
                )
            }
        }
    }


    /*
DiffUtil Class будет сравнивать элементы и перерисовывать нужные
 */
    class ItemComparator : DiffUtil.ItemCallback<NoteItem>() {
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener { //специальный интерфейс для реализации структуры mvwm
        fun deleteItem(id: Int) // передаем идентификатор заметки
        fun onClickItem(note :NoteItem) // передаем идентификатор заметки

    }
}