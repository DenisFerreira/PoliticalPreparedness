package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.*
import com.example.android.politicalpreparedness.election.ElectionsViewModel
import com.example.android.politicalpreparedness.election.repository.ElectionRepository
import com.example.android.politicalpreparedness.network.models.Address
import kotlinx.coroutines.launch

class RepresentativeViewModel(val repository: ElectionRepository) : ViewModel() {
    //TODO: Establish live data for representatives and address
    val representatives = repository.representatives
    private val address = MutableLiveData<Address?>()
    val addressLine1 = MutableLiveData<String>("")
    val addressLine2 = MutableLiveData<String>()
    val city = MutableLiveData<String>("")
    val state = MutableLiveData<String>("")
    val zip = MutableLiveData<String>("")


    //TODO: Create function to fetch representatives from API from a provided address

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields
    fun findRepresentatives(address: Address) {
        this.address.value = address
        addressLine1.value = address.line1
        address.line2?.let { addressLine2.value = it }
        city.value = address.city
        state.value = address.state
        zip.value = address.zip
        viewModelScope.launch {
            repository.refreshRepresentatives(address.toFormattedString())
        }
    }

    fun findRepresentatives() {
        if (isFormValid()) {
            val tempAddress = Address(addressLine1.value!!, addressLine2.value, city.value!!, state.value!!, zip.value!!)
            this.address.value = tempAddress

            viewModelScope.launch {
                repository.refreshRepresentatives(tempAddress.toFormattedString())
            }
        }
    }

    fun isFormValid(): Boolean {
        return !(addressLine1.value.isNullOrEmpty() ||
                city.value.isNullOrEmpty() ||
                state.value.isNullOrEmpty() ||
                zip.value.isNullOrEmpty())
    }

    val valid = MediatorLiveData<Boolean>()

    init {
        valid.value = false
        valid.addSource(addressLine1) {
            valid.value = isFormValid()
        }
        valid.addSource(city) {
            valid.value = isFormValid()
        }
        valid.addSource(state) {
            valid.value = isFormValid()
        }
        valid.addSource(zip) {
            valid.value = isFormValid()
        }

    }


}

class RepresentativeViewModelFactory(private val repository: ElectionRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepresentativeViewModel::class.java))
            return RepresentativeViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
