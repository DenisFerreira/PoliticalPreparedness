package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.election.repository.ElectionRepository
import com.example.android.politicalpreparedness.network.models.Election

class ElectionsFragment : Fragment() {

    //DONE: Declare ViewModel
    private val viewModel by viewModels<ElectionsViewModel> {
        ElectionsViewModelFactory(ElectionRepository(ElectionDatabase.getInstance(requireContext()).electionDao))
    }
    private lateinit var binding: FragmentElectionBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        //DONE: Add ViewModel values and create ViewModel
        binding = FragmentElectionBinding.inflate(layoutInflater, container, false)
        //DONE: Add binding values
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        //DONE: Link elections to voter info
        viewModel.navigateToElectionDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(it.id, it.division))
                viewModel.doneNavigating()
            }
        })
        //DONE: Initiate recycler adapters
        val clickListener: (Election) -> Unit = { election ->
            viewModel.navigateToElectionDetail.value = election
        }
        val savedAdapter = ElectionListAdapter(ElectionListener(clickListener))
        val upComingAdapter = ElectionListAdapter(ElectionListener(clickListener))
        binding.savedElectionsRecyclerView.adapter = savedAdapter
        binding.upcomingElectionsRecyclerView.adapter = upComingAdapter

        //DONE: Populate recycler adapters
        viewModel._savedElections.observe(viewLifecycleOwner, Observer {
            it?.let {
                savedAdapter.submitList(it)
            }
        })
        viewModel._upcomingElections.observe(viewLifecycleOwner, Observer {
            it?.let {
                upComingAdapter.submitList(it)
            }
        })
        return binding.root
    }

    //DONE: Refresh adapters when fragment loads
    override fun onResume() {
        super.onResume()
        viewModel.refreshElectionData()
    }
}