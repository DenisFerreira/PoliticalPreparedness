package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election


@Dao
interface ElectionDao {

    //TODO: Add insert query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg election: Election)

    //TODO: Add select all election query
    @Query("SELECT * FROM election_table")
    fun findAll() : LiveData<List<Election>>

    //TODO: Add select single election query
    @Query("SELECT * FROM election_table WHERE id = :id")
    fun findElection(id: Int): LiveData<Election>

    //TODO: Add delete query
    @Delete
    fun deleteElections(vararg election: Election)

    //TODO: Add clear query
    @Query("DELETE FROM election_table")
    fun clearElections()


}