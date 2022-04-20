package com.pd.shopinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pd.shopinglist.entities.LibraryItem
import com.pd.shopinglist.entities.NoteItem
import com.pd.shopinglist.entities.ShoppingListItem
import com.pd.shopinglist.entities.ShoppingListNames

//аннотация что это база данных которая включает в себя следующие entity
@Database(
    entities = [
        LibraryItem::class,
        NoteItem::class,
        ShoppingListItem::class,
        ShoppingListNames::class],
    version = 1
)

//  будет создавать базу данных либо давать доступ к ней
abstract class MainDataBase : RoomDatabase() {

    companion object {
        //  companion object дает возможность использовать то что внутри без инициализации класса,
        //  аналог в джава статическая функция

        @Volatile //все что записываем в эту переменную, а именно базу MainDataBase будет дуступен для всех потоков
        private var INSTANCE: MainDataBase? = null
        fun getDataBase(context: Context): MainDataBase {
            return INSTANCE
                ?: synchronized(this) { //т.е. бпзы данных нет то она будет создаваться здесь
                    // ?: - оператор элвиса. Если слева от него нул то он возвращет то что справа от него
                    //synchronized() блокировка (захват потока), не позволяет разным потокам использовать одновременно

                    //временная переменная в которой создаем базу данных.
                    val instanse = Room.databaseBuilder(
                        context.applicationContext,
                        MainDataBase::class.java,
                        "shopping_list.db"
                    ).build()
                    //context.applicationContext - контекст всего приложения

                    instanse
                }

        }
    }
}