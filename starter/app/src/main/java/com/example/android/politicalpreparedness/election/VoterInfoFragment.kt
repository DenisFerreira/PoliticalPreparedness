package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.election.repository.ElectionRepository


class VoterInfoFragment : Fragment() {

    private lateinit var binding: FragmentVoterInfoBinding
    private lateinit var viewModel: VoterInfoViewModel


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        //TODO: Add ViewModel values and create ViewModel
        val electionId = VoterInfoFragmentArgs.fromBundle(requireArguments()).argElectionId
        val address = VoterInfoFragmentArgs.fromBundle(requireArguments()).argDivision.toFormattedString()
        viewModel = VoterInfoViewModelFactory(
                ElectionRepository(ElectionDatabase.getInstance(requireContext()).electionDao),
                electionId,
                address
        ).create(VoterInfoViewModel::class.java)

        //TODO: Add binding values
        binding = FragmentVoterInfoBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */
        viewModel.ballotInfoURL.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.stateBallot.visibility = View.VISIBLE
            } else
                binding.stateBallot.visibility = View.GONE
        })

        viewModel.votingLocationURL.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.stateLocations.visibility = View.VISIBLE
            } else
                binding.stateLocations.visibility = View.GONE
        })

        //DONE: Create method to load URL intents
        //DONE: Handle loading of URLs
        viewModel.url.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = it
                startActivity(intent)
                viewModel.endNavigateURL()
            }
        })
        //TODO: Handle save button UI state
        viewModel.isElectionSaved.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.toogleFollowButton.apply {
                    text = "Unfollow election"
                    setOnClickListener {
                        viewModel.unfollowElection()
                    }
                }

            } else {

                binding.toogleFollowButton.apply {
                    text = "Follow election"
                    setOnClickListener {
                        viewModel.followElection()
                    }
                }


            }
        })
        //TODO: cont'd Handle save button clicks

        return binding.root
    }


}