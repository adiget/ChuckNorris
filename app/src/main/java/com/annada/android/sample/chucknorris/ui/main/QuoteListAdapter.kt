package com.annada.android.sample.chucknorris.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.annada.android.sample.chucknorris.R
import com.annada.android.sample.chucknorris.databinding.ItemQuoteBinding
import com.annada.android.sample.chucknorris.model.entities.Joke

class QuoteListAdapter : RecyclerView.Adapter<QuoteListAdapter.ViewHolder>() {
    private lateinit var quoteList: List<Joke>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemQuoteBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_quote, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteListAdapter.ViewHolder, position: Int) {
        holder.bind(quoteList[position])
    }

    fun updateQuoteList(quoteList: List<Joke>){
        this.quoteList = quoteList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if(::quoteList.isInitialized) quoteList.size else 0
    }


    class ViewHolder(private val binding: ItemQuoteBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = JokeViewModel()

        fun bind(quote: Joke){
            viewModel.bind(quote)
            binding.viewModel = viewModel
        }
    }
}