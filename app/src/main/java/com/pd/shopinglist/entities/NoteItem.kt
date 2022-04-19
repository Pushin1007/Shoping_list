package com.pd.shopinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//таблица для хранения заметок из блокнота

@Entity(tableName = "note_list")
data class NoteItem(
    @PrimaryKey(autoGenerate = true) //Идентификатор будет генерироваться первая колонка автоматически
    val id: Int?,

    @ColumnInfo(name = "title") // колонка с именем заметки
    val title: String,

    @ColumnInfo(name = "content") // колонка с текстом заметки
    val content: String,

    @ColumnInfo(name = "time") // колонка с временем создания заметки
    val time: String,

    @ColumnInfo(name = "category") // колонка категория заметки
    val category: String

)
