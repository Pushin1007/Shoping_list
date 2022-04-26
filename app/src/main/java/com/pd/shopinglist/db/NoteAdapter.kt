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

class NoteAdapter : ListAdapter<NoteItem, NoteAdapter.ItemHolder>(ItemComparator()) {

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
        holder.setData(getItem(position))
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding =
            NoteListItemBinding.bind(itemView)//тоесть пожключить вью itemView к нашему классу NoteListItemBinding

        //будем заполнять нашу разметку из нашего класса  NoteItem
        fun setData(note: NoteItem) = with(binding) {
            tvTitle.text = note.title
            tvDescription.text = note.content
            tvDate.text = note.time
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


}