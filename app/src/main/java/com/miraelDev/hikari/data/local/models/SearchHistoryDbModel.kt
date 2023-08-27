package com.miraelDev.hikari.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searchHistory")
data class SearchHistoryDbModel(
    @PrimaryKey val searchHistoryItem : String
)