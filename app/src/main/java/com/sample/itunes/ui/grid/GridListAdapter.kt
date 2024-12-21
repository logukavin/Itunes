package com.sample.itunes.ui.grid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.itunes.databinding.ItemGridBinding
import com.sample.itunes.extensions.loadUrl
import com.sample.itunes.model.ChildItem
import javax.inject.Inject

class GridListAdapter @Inject constructor() :
    RecyclerView.Adapter<GridListAdapter.GridViewHolder>() {

    var childItem = mutableListOf<ChildItem>()
    private var clickInterface: ClickInterface<ChildItem>? = null

    fun gridList(childItems: List<ChildItem>) {
        childItem.clear()
        childItem.addAll( childItems.toMutableList())
       notifyDataSetChanged()
    }

    interface ClickInterface<T> {
        fun onClick(data: T)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        return GridViewHolder(
            ItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val childItems = childItem[position]
        holder.view.tvName.text = childItems.collectionName
        holder.view.imgMovie.loadUrl(childItems.artworkUrl)

    }

    override fun getItemCount(): Int {
        return childItem.size
    }

    fun setItemClick(clickInterface: ClickInterface<ChildItem>) {
        this.clickInterface = clickInterface
    }

    class GridViewHolder(val view: ItemGridBinding) : RecyclerView.ViewHolder(view.root)
}

