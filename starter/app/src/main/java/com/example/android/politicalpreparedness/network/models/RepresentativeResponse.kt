package com.example.android.politicalpreparedness.network.models

import com.example.android.politicalpreparedness.representative.model.Representative

data class RepresentativeResponse(
        val offices: List<Office>,
        val officials: List<Official>
) {
    fun representativesList(): List<Representative>? {
        return officials.mapIndexed { index, official ->
            Representative(
                    official,
                    offices.first { it.officials.contains(index) }
            )
        }
    }
}