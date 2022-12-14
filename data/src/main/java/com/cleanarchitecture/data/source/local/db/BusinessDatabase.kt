package com.cleanarchitecture.data.source.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cleanarchitecture.data.source.local.dao.BusinessDao
import com.cleanarchitecture.data.source.local.model.BusinessDBModel
import com.cleanarchitecture.data.source.local.model.LoginDBModel
import com.cleanarchitecture.domain.model.login.LoginModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [BusinessDBModel::class,LoginDBModel::class], version = 2, exportSchema = false)
public abstract class BusinessDatabase : RoomDatabase() {

    abstract fun businessDao(): BusinessDao

    companion object {

        @Volatile
        private var INSTANCE: BusinessDatabase? = null

        fun getDatabase(context: Context): BusinessDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BusinessDatabase::class.java,
                    "business_item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                // return instance
                instance
            }
        }
    }

}