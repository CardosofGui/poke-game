package com.example.pokegame.presenter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokegame.databinding.FragmentLeaderboardBinding
import com.example.pokegame.framework.viewmodel.RecordViewModel
import com.example.pokegame.presenter.adapter.LeaderboardAdapter
import com.example.pokegame.presenter.fragments.GameFragment.Companion.listRecords
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
    private lateinit var leaderboardAdapter: LeaderboardAdapter

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
            }
        }
    }

    private fun initRecycler() {
        leaderboardAdapter = LeaderboardAdapter(listRecords)
        binding.rcvRecords.adapter = leaderboardAdapter
    }

    override fun onResume() {
        super.onResume()

        leaderboardViewModel.getAllRecords()
        showLoading()
    }

    override fun onStop() {
        super.onStop()

        leaderboardViewModel.resetRecords()
    }
    private fun showLoading() {
        binding.llLoading.visibility = View.VISIBLE
        binding.rcvRecords.visibility = View.GONE
    }
    private fun hideLoading() {
        binding.llLoading.visibility = View.GONE
        binding.rcvRecords.visibility = View.VISIBLE
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