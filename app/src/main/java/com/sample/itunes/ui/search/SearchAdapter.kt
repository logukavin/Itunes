package com.sample.itunes.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.itunes.databinding.ItemSearchBinding
import com.sample.itunes.model.MediaOption

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private var options: List<MediaOption> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun updateSearchAdapter(newOptions: List<MediaOption>) {
        options = newOptions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(options[position])
    }

    override fun getItemCount() = options.size

    fun getCheckedItems(): List<MediaOption> {
        return options.filter { it.isSelected }
    }

    class SearchViewHolder(private val view: ItemSearchBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(mediaOption: MediaOption) {
            view.tvSearch.text = mediaOption.name
        }
    }
    }