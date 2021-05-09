package com.example.android.politicalpreparedness.election.repository

import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class ElectionRepositoryTest {
    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository:ElectionRepository
    private lateinit var fakeDao: ElectionDao
    private lateinit var fakeAPIService: CivicsApiService

    @Before
    fun createRepository() {
        fakeDao = FakeDAO()
        fakeAPIService = FakeAPI()
        // Get a reference to the class under test
        repository = ElectionRepository(
                fakeDao, Dispatchers.Main, fakeAPIService
        )
    }

    @Test
    fun saveElectionsAndRetrive() = mainCoroutineRule.runBlockingTest {
        val division = Division("1001", "COUNTRY", "STATE")
        val election = Election(1001 , "Name Example", Date(), division)
        repository.saveElection(election)
        val retrivedElection = repository.findElection(election.id)
        Assert.assertEquals(election, retrivedElection)
    }
}