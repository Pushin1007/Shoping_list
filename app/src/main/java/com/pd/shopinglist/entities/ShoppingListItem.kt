package com.pd.shopinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//таблица - список продуктов
@Entity(tableName = "shop_list_item")
data class ShoppingListItem(

    @PrimaryKey(autoGenerate = true)
    val id: Int, //Идентификатор будет генерироваться первая колонка автоматически

    @ColumnInfo(name = "name")
    val name: String, //название продукта

    @ColumnInfo(name = "itemInfo")
    val itemInfo: String, //дополнительное инфо о продукте

    @ColumnInfo(name = "itemChecked")
    val itemChecked: Int=0, //чек бокс о купленном продукте, по умолчанию равен 0

    @ColumnInfo(name = "listId")
    val listId: Int, //идентификатор нашего списка

    @ColumnInfo(name = "itemType")
    val itemType: String="item", //идентификатор нашего списка

)
