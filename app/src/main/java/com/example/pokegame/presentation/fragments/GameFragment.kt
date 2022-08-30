package com.example.pokegame.presentation.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.example.pokegame.R
import com.example.pokegame.databinding.FragmentGameBinding
import com.example.pokegame.domain.Game
import com.example.pokegame.data.entities.UserPoints
import com.example.pokegame.presentation.viewmodel.GameViewModel
import com.google.android.material.textfield.TextInputEditText
import com.shashank.sony.fancytoastlib.FancyToast
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentGameBinding
    private val gameViewModel : GameViewModel by viewModel()

    private var timeRunner = 5000
    private lateinit var timerCounter : CountDownTimer
    private lateinit var winCounter : CountDownTimer
    private lateinit var lossCounter : CountDownTimer

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
        binding = FragmentGameBinding.inflate(inflater, container, false)

        createTimer()
        initClicks()
        initObserver()

        return binding.root
    }

    private fun initObserver() {
        gameViewModel.round.observe(requireActivity()) {
            if (it == -1) {
                endGame()
            } else if (it != 0){
                setGameInfo(gameViewModel.getActualGame())
            }
        }

        gameViewModel.pokemonList.observe(requireActivity()) {
            if(it != null) {
                hideLoading()
            }
        }
    }
    private fun endGame() {
        binding.llInitial.visibility = View.VISIBLE
        binding.clGame.visibility = View.GONE

        showAlertScore()
        gameViewModel.resetGame()
    }
    private fun initClicks() {
        binding.btnPlay.setOnClickListener {
            startGame()
        }

        binding.btnOption1.setOnClickListener {
            checkGameResult(binding.btnOption1.text.toString(), gameViewModel.getActualGame())
        }
        binding.btnOption2.setOnClickListener {
            checkGameResult(binding.btnOption2.text.toString(), gameViewModel.getActualGame())
        }
        binding.btnOption3.setOnClickListener {
            checkGameResult(binding.btnOption3.text.toString(), gameViewModel.getActualGame())
        }
        binding.btnOption4.setOnClickListener {
            checkGameResult(binding.btnOption4.text.toString(), gameViewModel.getActualGame())
        }
        binding.btnInfo.setOnClickListener {
            showInfo()
        }
    }
    private fun startGame() {
        binding.llInitial.visibility = View.GONE
        binding.clGame.visibility = View.VISIBLE

        gameViewModel.createGame()
        setGameInfo(gameViewModel.getActualGame())
    }
    private fun setGameInfo(game : Game) {
        val shuffleList = game.pokemonList.shuffled()

        activeButtons(false)
        binding.tvTimer.setText("5 Segundos")
        binding.btnOption1.setText(shuffleList[0].name)
        binding.btnOption2.setText(shuffleList[1].name)
        binding.btnOption3.setText(shuffleList[2].name)
        binding.btnOption4.setText(shuffleList[3].name)

        Picasso.get()
            .load(game.correctPoke.getImage())
            .error(R.drawable.ic_mockup_team)
            .into(binding.ivPokemonHide, object : Callback {
                override fun onSuccess() {
                    startTimer()
                    activeButtons(true)
                }

                override fun onError(e: Exception?) {
                    Toast.makeText(requireContext(), "Sem conexão com a Internet", Toast.LENGTH_SHORT).show()
                    endGame()
                }
            })
    }
    private fun activeButtons(clickable : Boolean) {
        binding.btnOption1.isClickable = clickable
        binding.btnOption2.isClickable = clickable
        binding.btnOption3.isClickable = clickable
        binding.btnOption4.isClickable = clickable
    }

    private fun checkGameResult(result : String, game : Game) {
        timerCounter.cancel()

        if (game.checkResult(result)){
            resultWinGame()
        } else {
            resultLossGame()
        }
    }

    private fun resultWinGame() {
        correctToast()
        winCounter.start()
    }

    private fun resultLossGame() {
        FancyToast.makeText(requireContext(), "Errou!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
        lossCounter.start()
    }

    private fun startTimer() {
        timeRunner = 5000
        timerCounter.cancel()
        timerCounter.start()
    }
    private fun createTimer() {
        timerCounter = object: CountDownTimer(5000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                timeRunner -= 100

                binding.tvTimer.setText("${(millisUntilFinished/1000).toInt()} Segundos")
            }

            override fun onFinish() {
                lossCounter.start()
            }
        }

        winCounter = object: CountDownTimer(1000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                ImageViewCompat.setImageTintList(binding.ivPokemonHide, null)
                activeButtons(false)
            }

            override fun onFinish() {
                gameViewModel.winGame(timeRunner)
                ImageViewCompat.setImageTintList(binding.ivPokemonHide, ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.hide_pokemon_color)))
                activeButtons(true)
            }
        }

        lossCounter = object: CountDownTimer(1000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                ImageViewCompat.setImageTintList(binding.ivPokemonHide, null)
                activeButtons(false)
            }

            override fun onFinish() {
                gameViewModel.lossGame()
                timeRunner = 5000
                ImageViewCompat.setImageTintList(binding.ivPokemonHide, ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.hide_pokemon_color)))
                activeButtons(true)
            }
        }
    }
    private fun hideLoading() {
        binding.llLoading.visibility = View.GONE
        binding.clGame.visibility = View.GONE
        binding.llInitial.visibility = View.VISIBLE
    }
    private fun showLoading() {
        binding.llLoading.visibility = View.VISIBLE
        binding.clGame.visibility = View.GONE
        binding.llInitial.visibility = View.GONE
    }
    private fun showInfo() {
        val builder = AlertDialog.Builder(requireContext()).create()
        builder.setTitle("")

        val view = layoutInflater.inflate(R.layout.card_info, null)

        builder.setView(view)
        builder.show()
    }

    private var genderSelect = ""
    private var teamSelect = ""

    private fun showAlertScore() {
        val builder = AlertDialog.Builder(requireContext()).create()
        builder.setTitle("")

        val gamePoints = gameViewModel.getPoints()

        val view = layoutInflater.inflate(R.layout.card_player_score, null)
        val edtUser = view.findViewById<TextInputEditText>(R.id.tie_name_user)
        val tvPoints = view.findViewById<TextView>(R.id.tv_points)
        val ibMale = view.findViewById<ImageButton>(R.id.ib_boy)
        val ibFemale = view.findViewById<ImageButton>(R.id.ib_girl)
        val ibRed = view.findViewById<ImageButton>(R.id.ib_team_red)
        val ibBlue = view.findViewById<ImageButton>(R.id.ib_team_blue)
        val ibYellow = view.findViewById<ImageButton>(R.id.ib_team_yellow)
        val btnSave = view.findViewById<Button>(R.id.btn_save)

        tvPoints.text = "$gamePoints pontos"
        selectPerson(ibMale, ibFemale)
        selectTeam(ibRed, ibBlue, ibYellow)
        saveRecord(btnSave, edtUser, builder, gamePoints)

        builder.setView(view)
        builder.show()
    }
    private fun saveRecord(
        btnSave: Button,
        username: TextInputEditText,
        builder: AlertDialog,
        points: Int
    ) {
        btnSave.setOnClickListener {
            val newRecord = UserPoints(
                null,
                username.text.toString(),
                points.toString(),
                teamSelect,
                genderSelect
            )

            gameViewModel.insertRecord(newRecord)

            builder.dismiss()
        }
    }
    private fun selectPerson(viewMale : ImageButton, viewFemale : ImageButton) {
        viewMale.setOnClickListener {
            genderSelect = "M"
        }

        viewFemale.setOnClickListener {
            genderSelect = "F"
        }
    }
    private fun selectTeam(viewRed : ImageButton, viewBlue : ImageButton, viewYellow : ImageButton) {
        viewRed.setOnClickListener {
            teamSelect = "R"
        }

        viewBlue.setOnClickListener {
            teamSelect = "B"
        }

        viewYellow.setOnClickListener {
            teamSelect = "Y"
        }
    }

    override fun onResume() {
        gameViewModel.getAllPokemon()
        showLoading()

        super.onResume()
    }

    override fun onStop() {
        timerCounter.cancel()
        gameViewModel.resetGame()
        super.onStop()
    }

    private fun correctToast() {
        val toast = FancyToast.makeText(requireContext(), "Acertou!", FancyToast.SUCCESS, Toast.LENGTH_SHORT, false)
        toast.show()

        val handler = Handler()
        handler.postDelayed({ toast.cancel() }, 500)
    }


    companion object {
        val listRecords = arrayListOf<UserPoints>()

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}