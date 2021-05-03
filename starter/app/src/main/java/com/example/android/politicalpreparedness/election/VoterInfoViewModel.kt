package com.example.android.politicalpreparedness.election

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.election.repository.ElectionRepository
import kotlinx.coroutines.launch


class VoterInfoViewModel(private val repository: ElectionRepository, private val electionId: Int, private val address: String) : ViewModel() {
    init {
        refresh()
    }

    //TODO: Add live data to hold voter info
    val voterInfo = repository.voterInfo
    val correspondenceAddress = repository.correspondenceAddress
    val votingLocationURL = repository.votingLocationURL
    val ballotInfoURL = repository.ballotInfoUrl
    val savedElections = repository.savedElections
    val buttonText = MutableLiveData<String?>()


    //TODO: Add var and methods to populate voter info
    private fun refresh() {
        viewModelScope.launch {
            repository.refreshVoterinfo(electionId, address)
        }
    }

    //TODO: Add var and methods to support loading URLs
    private val _url = MutableLiveData<Uri>()
    val url: LiveData<Uri>
        get() = _url

    fun navigateURL(url: String?) {
        url?.let {
            _url.value = Uri.parse(it)
        }
    }

    fun endNavigateURL() {
        _url.value = null
    }

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status
    //TODO: change live data to use mutablelivedata to be refreshed after button
    fun toggleSaveElection() {
        val election = voterInfo.value?.election ?: return

        savedElections.value?.let { savedElections ->
            val idsList = savedElections.map { it.id }
            if (idsList.contains(election.id)) {
                viewModelScope.launch {
                    repository.removeElection(election)
                    Log.i("VoterInfoViewModel", "election removed")
                }
                return
            }
        }

        viewModelScope.launch {
            repository.saveElection(election)
            Log.i("VoterInfoViewModel", "election Saved")
        }
    }

    fun isElectionSaved(electionId: Int) = savedElections.value?.map { it.id }?.contains(electionId)


    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}