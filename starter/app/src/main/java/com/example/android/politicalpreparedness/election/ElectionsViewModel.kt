package com.example.android.politicalpreparedness.election

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.election.repository.ElectionRepository
import kotlinx.coroutines.launch

//DONE: Construct ViewModel and provide election datasource
class ElectionsViewModel(private val repository: ElectionRepository) : ViewModel() {

    //DONE: Create live data val for upcoming elections
    val _upcomingElections = repository.upcomingElections

    //DONE: Create live data val for saved elections
    val _savedElections = repository.savedElections

    //DONE: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    fun refreshElectionData() = viewModelScope.launch {
        repository.refreshUpComingElections();
    }

    //DONE: Create functions to navigate to saved or upcoming election voter info
    private val _navigateToElectionDetail = MutableLiveData<Election>()
    val navigateToElectionDetail
        get() = _navigateToElectionDetail
    fun doneNavigating() {
        _navigateToElectionDetail.value = null
    }

}