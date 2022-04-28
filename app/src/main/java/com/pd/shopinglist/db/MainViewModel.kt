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