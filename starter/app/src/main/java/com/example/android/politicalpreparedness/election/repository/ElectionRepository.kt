package com.example.android.politicalpreparedness.election.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ElectionRepository(private val electionDao: ElectionDao, val dispatcher: CoroutineDispatcher = Dispatchers.IO, val apiService: CivicsApiService) {

    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    private val _representatives = MutableLiveData<List<Representative>?>()
    val representatives: LiveData<List<Representative>?>
        get() = _representatives

    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo

    private val _correspondenceAddress = MutableLiveData<String>()
    val correspondenceAddress: LiveData<String>
        get() = _correspondenceAddress

    private val _votingLocationURL = MutableLiveData<String>()
    val votingLocationURL: LiveData<String>
        get() = _votingLocationURL

    private val _ballotInfoUrl = MutableLiveData<String>()
    val ballotInfoUrl: LiveData<String>
        get() = _ballotInfoUrl

    val savedElections = electionDao.findAll()

    suspend fun  saveElection(election: Election) = withContext(dispatcher){electionDao.insert(election)}

    fun findElection(id: Int) = electionDao.findElection(id)

    suspend fun removeElection(election: Election) = withContext(dispatcher){electionDao.deleteElections(election)}

    suspend fun refreshUpComingElections() = withContext(dispatcher) {
        try {
            val response = apiService
                    .getElections()
            val elections = response.elections
            withContext(Dispatchers.Main) {
                _upcomingElections.postValue(elections)
                Log.e("ElectionRepository", "Data refreshed")
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("ElectionRepository", errorBody ?: "No internet Connection")
        }
    }

    suspend fun refreshVoterinfo(electionId: Int, address: String) = withContext(dispatcher) {
        try {
            val response = apiService
                    .getVoterInfo(address, electionId.toLong())
            withContext(Dispatchers.Main) {
                _voterInfo.postValue(response)
                _correspondenceAddress.postValue(response.state?.get(0)?.electionAdministrationBody?.correspondenceAddress?.toFormattedString())
                _votingLocationURL.postValue(response.state?.get(0)?.electionAdministrationBody?.votingLocationFinderUrl)
                _ballotInfoUrl.postValue(response.state?.get(0)?.electionAdministrationBody?.ballotInfoUrl)
                Log.e("ElectionRepository", "Data refreshed")
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("ElectionRepository", errorBody ?: "No internet Connection")
        }
    }

    suspend fun refreshRepresentatives(address: String) {
        try {
            val response = apiService
                    .getRepresentatives(address)
            val representatives = response.representativesList()

            withContext(Dispatchers.Main) {
                _representatives.value = representatives
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("ElectionRepository", errorBody ?: "No internet Connection")

        }
    }


}