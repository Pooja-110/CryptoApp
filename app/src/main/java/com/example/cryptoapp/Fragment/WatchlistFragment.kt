package com.example.cryptoapp.Fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.cryptoapp.Adapter.MarketAdapter
import com.example.cryptoapp.Api.ApiIterface
import com.example.cryptoapp.Api.ApiUtilites
import com.example.cryptoapp.Models.CryptoCurrency
import com.example.cryptoapp.databinding.FragmentWatchlistBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale


class WatchlistFragment : Fragment() {
    private lateinit var binding: FragmentWatchlistBinding
    private lateinit var watchlist:ArrayList<String>

    private lateinit var watchListItem:ArrayList<CryptoCurrency>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchlistBinding.inflate(layoutInflater, container, false)
        readData()
        lifecycleScope.launch(Dispatchers.IO){
            val res= ApiUtilites.getInstance().create(ApiIterface::class.java)
                .getMarketData()
            if (res.body()!=null){
                withContext(Dispatchers.Main){
                    watchListItem=ArrayList()
                    watchListItem.clear()
                    for (watchData in watchlist){
                        for (item in res.body()!!.data.cryptoCurrencyList) {
                            if (watchData==item.symbol){
                                watchListItem.add(item)

                            }
                        }
                    }
                    binding.spinKitView.visibility = GONE
                    binding.watchlistRecyclerView.adapter = MarketAdapter(requireContext(),watchListItem,"WatchlistFragment")
                }
            }
        }
        searchCoin()
        return binding.root
    }
    lateinit var searchText: String
    private fun searchCoin() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                searchText = p0.toString().lowercase(Locale.getDefault())


                val filteredList = watchListItem.filter { item ->
                    item.name?.lowercase(Locale.getDefault())?.contains(searchText) == true ||
                            item.symbol?.lowercase(Locale.getDefault())?.contains(searchText) == true
                }

                (binding.watchlistRecyclerView.adapter as MarketAdapter).upDateData(filteredList)
            }
        })
    }
    private fun readData() {
        val sharedPreferences=requireContext().getSharedPreferences("watchList", Context.MODE_PRIVATE)
        val gson= Gson()
        val json=sharedPreferences.getString("watchList",ArrayList<String>().toString())
        val type=object : TypeToken<ArrayList<String>>(){}.type
        watchlist=gson.fromJson(json,type)
    }

}