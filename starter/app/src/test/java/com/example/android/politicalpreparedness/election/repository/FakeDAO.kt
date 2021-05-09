package com.example.android.politicalpreparedness.election.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.runBlocking

class FakeDAO : ElectionDao {
    private var list = mutableListOf<Election>()
    private val observableList = MutableLiveData<List<Election>>()
    private val observableElement = MutableLiveData<Election>()

    override fun insert(vararg election: Election) {
        list.addAll(election)
        Log.i("RepositoryTest", "inserted: $election")
    }

    override fun findAll(): LiveData<List<Election>> {
        observableList.value = list
        return observableList
    }

    override fun findElection(id: Int): LiveData<Election> {
        observableElement.value = list.get(id)
        Log.i("RepositoryTest", "inserted: $observableElement")
        return observableElement
    }

    override fun deleteElections(vararg election: Election) {
        list.removeAll(election)
    }

    override fun clearElections() {
        list.clear()
    }

}
