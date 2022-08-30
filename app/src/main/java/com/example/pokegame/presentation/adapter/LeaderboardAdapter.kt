package com.example.pokegame.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegame.R
import com.example.pokegame.databinding.CardRecordBinding
import com.example.pokegame.data.entities.UserPointsModel
import com.squareup.picasso.Picasso

class LeaderboardAdapter(
    private var listLeaderboard : List<UserPointsModel>
) : RecyclerView.Adapter<ViewHolderLeaderboard>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLeaderboard {
        return ViewHolderLeaderboard(CardRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderLeaderboard, position: Int) {
        holder.bind(listLeaderboard[position])
    }

    override fun getItemCount(): Int = listLeaderboard.size

    fun updateList(newList : List<UserPointsModel>){
        listLeaderboard = newList
        notifyDataSetChanged()
    }

}

class ViewHolderLeaderboard(val binding : CardRecordBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(record : UserPointsModel) {
        binding.tvName.setText("${position+1} - ${record.username}")
        binding.tvPoints.setText(record.points + " Pontos")

        if (record.person.toUpperCase() == "M") {
            Picasso.get()
                .load(R.drawable.treinador).fit().centerInside()
                .into(binding.ivGender)
        } else {
            Picasso.get()
                .load(R.drawable.treinadora).fit().centerInside()
                .into(binding.ivGender)
        }

        when(record.team.toUpperCase()) {
            "R" -> {
                Picasso.get()
                    .load(R.drawable.team_red).fit().centerInside()
                    .into(binding.ivTeam)

                binding.clRecord.setBackgroundResource(R.color.background_team_valor)
            }

            "B" -> {
                Picasso.get()
                    .load(R.drawable.team_blue).fit().centerInside()
                    .into(binding.ivTeam)

                binding.clRecord.setBackgroundResource(R.color.background_team_mystic)
            }

            "Y" -> {
                Picasso.get()
                    .load(R.drawable.team_yellow).fit().centerInside()
                    .into(binding.ivTeam)

                binding.clRecord.setBackgroundResource(R.color.background_team_instinct)
            }
        }
    }
}