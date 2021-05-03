package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.election.repository.ElectionRepository

//DONE: Create Factory to generate VoterInfoViewModel with provided election datasource
@Suppress("UNCHECKED_CAST")
class VoterInfoViewModelFactory(private val repository: ElectionRepository, val electionId: Int, val address: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VoterInfoViewModel::class.java))
            return VoterInfoViewModel(repository, electionId, address) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}