package com.pd.shopinglist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pd.shopinglist.entities.NoteItem
import kotlinx.coroutines.flow.Flow


// класс с помощью которого мы будем получать доступ  к базе данных чтобы записывать или считывать
@Dao
interface Dao {
    @Query("SELECT * FROM note_list")//получение всех заметок
fun getAllNotes(): Flow<List<NoteItem>> // Flow будет подключать базу данных к нашему списку и будет автоматически все обновлять

    @Insert
    //suspend - т.к функция будет работать внутри корутины
    suspend fun insertNote(note: NoteItem)// функция записи в базу данных заметки
}