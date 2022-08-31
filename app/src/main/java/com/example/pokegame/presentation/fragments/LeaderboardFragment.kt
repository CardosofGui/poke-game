package com.example.pokegame.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokegame.databinding.FragmentLeaderboardBinding
import com.example.pokegame.presentation.adapter.LeaderboardAdapter
import com.example.pokegame.presentation.viewmodel.RecordViewModel
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

        /*
        initClicks()
        initRecycler()
        initObserver()

         */

        return binding.root
    }

    /*
    private fun initClicks() {
        binding.btnRestart.setOnClickListener {
            loadingRecords()
        }
    }

    private fun initObserver() {
        leaderboardViewModel.allRecords.observe(requireActivity()) {
            if (it != null) {
                if(it.isNotEmpty()){
                    leaderboardAdapter.updateList(it)
                    showHideElements(initial = true)
                } else {
                    loadingRecords()
                }
            }
        }

        leaderboardViewModel.error.observe(requireActivity()) {
            if(it != null && leaderboardViewModel.allRecords.value.isNullOrEmpty()) {
                showHideElements(error = true)
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

        loadingRecords()
    }

    private fun loadingRecords() {
        leaderboardViewModel.getAllRecords()

        if (leaderboardViewModel.allRecords.value.isNullOrEmpty()) showHideElements(loading = true)
    }
    private fun showHideElements(initial : Boolean = false, loading : Boolean = false, error : Boolean = false) {
        binding.clLeaderboard.visibility = if(initial) View.VISIBLE else View.GONE
        binding.llLoading.visibility = if(loading) View.VISIBLE else View.GONE
        binding.llError.visibility = if(error) View.VISIBLE else View.GONE

    }

     */

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