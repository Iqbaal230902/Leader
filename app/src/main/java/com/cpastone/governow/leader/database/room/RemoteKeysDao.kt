package com.cpastone.governow.leader.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cpastone.governow.leader.data.model.RemoteKey

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)

    fun insertAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remote_key WHERE id = :id")
    fun getRemoteKeyById(id: String): RemoteKey?

    @Query("DELETE FROM remote_key")
    fun deleteRemoteKey()
}
