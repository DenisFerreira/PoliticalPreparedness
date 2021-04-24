package com.example.android.politicalpreparedness.election

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.election.repository.ElectionRepository

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(val repository: ElectionRepository) : ViewModel() {

    //TODO: Create live data val for upcoming elections
    val _upcomingElections = repository.getUpcomingElections()

    //TODO: Create live data val for saved elections
    val _savedElections = repository.getSavedElections()

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info
    private val _navigateToElectionDetail = MutableLiveData<Election>()
    val navigateToElectionDetail
        get() = _navigateToElectionDetail

    fun doneNavigating() {
        _navigateToElectionDetail.value = null
    }
}