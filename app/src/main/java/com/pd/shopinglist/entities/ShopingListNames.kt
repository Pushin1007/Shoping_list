package com.pd.shopinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "shoping_list_names")
data class ShopingListNames(
    @PrimaryKey(autoGenerate = true) //Идентификатор будет генерироваться первая колонка автоматически
    val id: Int?,

    @ColumnInfo(name = "name") // колонка с именем списка
    val name: String,

    @ColumnInfo(name = "time") // колонка с временем создания
    val time: String,

    @ColumnInfo(name = "allItemCounter") // колонка  общего количество элементов
    val allItemCounter: Int,

    @ColumnInfo(name = "checkItemCounter") // колонка отмеченных элементов
    val checkItemCounter: Int,

    @ColumnInfo(name = "itemsIds") // колонка всех идентификатор которые есть в этом списке
    val itemsIds: String,

    ):Serializable
