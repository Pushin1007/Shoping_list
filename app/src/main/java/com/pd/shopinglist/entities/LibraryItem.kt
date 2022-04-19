package com.pd.shopinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//таблица которая хранит подсказки при набирании слова
@Entity(tableName = "library")
data class LibraryItem(
    @PrimaryKey(autoGenerate = true) //Идентификатор будет генерироваться первая колонка автоматически
    val id: Int?,

    @ColumnInfo(name = "name") // колонка с именем заметки
    val name: String
)
