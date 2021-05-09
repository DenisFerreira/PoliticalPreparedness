package com.example.android.politicalpreparedness.election.repository

import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.*
import java.util.*

class FakeAPI : CivicsApiService {
    private var election3: Election
    private var election2: Election
    private var election1: Election
    private var division: Division
    private var office: Office
    private var official: Official
    private var address: Address

    init {
        division = Division("1001", "COUNTRY", "STATE")
        election1 = Election(1001 , "Name Example", Date(), division)
        election2 = Election(1002 , "Name Example", Date(), division)
        election3 = Election(1003 , "Name Example", Date(), division)
        office = Office("Office Name", division, listOf(1001))
        address = Address("line1", null, "city", "state", "zip")
        official = Official("Official Name", listOf(address), "party", urls = listOf("url"), photoUrl = "photourl")
    }

    override suspend fun getElections(): ElectionResponse {
        return ElectionResponse(kind = "Kind Example", elections = listOf(election1, election2, election3))
    }

    override suspend fun getVoterInfo(address: String, electionId: Long, officialOnly: Boolean, returnAllAvailableData: Boolean): VoterInfoResponse {
        return VoterInfoResponse(election1)
    }

    override suspend fun getRepresentatives(address: String): RepresentativeResponse {
        return RepresentativeResponse(listOf(office), listOf(official))
    }

}
