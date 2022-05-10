package com.pd.shopinglist.db

import androidx.lifecycle.*
import com.pd.shopinglist.entities.NoteItem
import kotlinx.coroutines.launch


class MainViewModel(database: MainDataBase) : ViewModel() {

    val dao = database.getDao() //получаем нашу базу данных
    val allNotes: LiveData<List<NoteItem>> =
        dao.getAllNotes().asLiveData()// при обновлении заметки будет обновляться  LiveData

    fun insertNote(note: NoteItem) = viewModelScope.launch {
        //запускаем корутину т.к. процесс записи может быть длительным
        dao.insertNote(note)
    }

    fun updateNote(note: NoteItem) = viewModelScope.launch {
        //запускаем корутину т.к. процесс записи может быть длительным
        dao.updateNote(note)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        //запускаем корутину т.к. процесс записи может быть длительным
        dao.deleteNote(id)
    }

    class MainViewModelFactory(val database: MainDataBase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T { // эта функция будет создавать  ViewModel
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) { // если modelClass из группы MainViewModel
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }

    }

}