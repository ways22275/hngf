package com.example.sideproject.data.local.User

import androidx.room.*
import com.example.sideproject.data.model.UserInfo
import com.example.sideproject.utils.Constant
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM ${Constant.TABLE_USER}")
    fun get() : Single<UserInfo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user : UserInfo) : Long

    @Update
    fun update(user : UserInfo)

    @Query("DELETE FROM ${Constant.TABLE_USER}")
    fun deleteAll()

    @Transaction
    fun merge(user : UserInfo) {
        val id = insert(user)
        if (id == -1L) {
            update(user)
        }
    }
}