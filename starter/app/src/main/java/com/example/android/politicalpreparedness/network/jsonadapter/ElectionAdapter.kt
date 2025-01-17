package com.example.android.politicalpreparedness.network.jsonadapter

import com.example.android.politicalpreparedness.network.models.Division
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class ElectionAdapter {
    @FromJson
    fun divisionFromJson(ocdDivisionId: String): Division {
        val countryDelimiter = "country:"
        val stateDelimiter = "state:"
        val country = ocdDivisionId.substringAfter(countryDelimiter, "")
                .substringBefore("/")
        val state = ocdDivisionId.substringAfter(stateDelimiter, "")
                .substringBefore("/")
        return Division(ocdDivisionId, country, state)
    }

    @ToJson
    fun divisionToJson(division: Division): String {
        return division.id
    }

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    @FromJson
    fun electionDayFromJson(electionDay: String): Date? {
        try {
            synchronized(dateFormat) {
                return dateFormat.parse(electionDay)
            }
        } catch (e: Exception) {
            return null
        }
    }

    @ToJson
    fun electionDayToJson(electionDay: Date): String {
        synchronized(dateFormat) {
            return electionDay.toString()
        }
    }
}