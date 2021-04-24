package com.example.android.politicalpreparedness.election.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers

class ElectionRepository(private val database: ElectionDatabase) {

    fun getUpcomingElections() = liveData(Dispatchers.IO) {
        val upComing = CivicsApi.retrofitService.getElections()
        emit(upComing.elections)
    }


    fun getSavedElections(): LiveData<List<Election>> {
        return database.electionDao.findAll()
    }

    fun saveElection(election: Election) = database.electionDao.insert(election)
}