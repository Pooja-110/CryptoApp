package com.example.cryptoapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoapp.Models.CryptoCurrency
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.TopCurrencyLayoutBinding



class TopMarketAdapter(private val context: Context,private val list: List<CryptoCurrency>) :
    RecyclerView.Adapter<TopMarketAdapter.TopMarketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMarketViewHolder {
        val binding = TopCurrencyLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return TopMarketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopMarketViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class TopMarketViewHolder(private val binding:TopCurrencyLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: CryptoCurrency) {

            binding.topCurrencyNameTextView.text = position.name
            Glide.with(context)
                .load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + position.id + ".png")
                .thumbnail(
                    Glide.with(context).load(
                        R.drawable.spinner
                    )
                ).into(binding.topCurrencyImageView)

            if (position.quotes!![0].percentChange24h > 0){
                binding.topCurrencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
                binding.topCurrencyChangeTextView.text="+ ${String.format("%.02f",position.quotes[0].percentChange24h)} %"
            }else{
                binding.topCurrencyChangeTextView.setTextColor(context.resources.getColor(R.color.red))
                binding.topCurrencyChangeTextView.text="${String.format("%.02f",position.quotes[0].percentChange24h)} %"
            }
        }

    }
}