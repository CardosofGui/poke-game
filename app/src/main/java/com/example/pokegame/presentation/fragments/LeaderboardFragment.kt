package com.example.pokegame.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import com.example.pokegame.databinding.FragmentLeaderboardBinding
import com.example.pokegame.presentation.viewmodel.RecordViewModel
import com.example.pokegame.presentation.adapter.LeaderboardAdapter
import com.example.pokegame.presentation.fragments.GameFragment.Companion.listRecords
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LeaderboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeaderboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentLeaderboardBinding
    private val leaderboardAdapter : LeaderboardAdapter by inject()
    private val leaderboardViewModel : RecordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false)

        initRecycler()
        initObserver()

        return binding.root
    }

    private fun initObserver() {
        leaderboardViewModel.allRecords.observe(requireActivity()) {
            if(!it.isNullOrEmpty()){
                leaderboardAdapter.updateList(it)
                hideLoading()
            } else {
                showLoading()
            }
        }

        leaderboardViewModel.error.observe(requireActivity()) {
            if(it != null && leaderboardViewModel.allRecords.value.isNullOrEmpty()) {
                binding.clLeaderboard.visibility = View.GONE
                binding.llLoading.visibility = View.GONE
                binding.llError.visibility = View.VISIBLE

                binding.tvError.text = "$it"
            }
        }
    }

    private fun initRecycler() {
        leaderboardAdapter.updateList(listRecords)
        binding.rcvRecords.adapter = leaderboardAdapter
    }

    override fun onResume() {
        super.onResume()

        leaderboardViewModel.getAllRecords()
    }
    private fun showLoading() {
        binding.llLoading.visibility = View.VISIBLE
        binding.clLeaderboard.visibility = View.GONE
        binding.llError.visibility = View.GONE
    }
    private fun hideLoading() {
        binding.llLoading.visibility = View.GONE
        binding.llError.visibility = View.GONE
        binding.clLeaderboard.visibility = View.VISIBLE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LeaderboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeaderboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}