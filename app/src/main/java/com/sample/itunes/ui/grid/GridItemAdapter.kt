package com.sample.itunes.ui.grid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.itunes.databinding.ItemGridBinding
import com.sample.itunes.extensions.loadUrl
import com.sample.itunes.model.ChildItem
import javax.inject.Inject

class GridItemAdapter @Inject constructor() :
    RecyclerView.Adapter<GridItemAdapter.GridViewHolder>() {

    private var childItem = mutableListOf<ChildItem>()
    private var clickInterface: ClickInterface<ChildItem>? = null

    fun setGridList(childItems: List<ChildItem>) {
        childItem.clear()
        childItem.addAll(childItems)
        notifyDataSetChanged()
    }

    interface ClickInterface<T> {
        fun onClick(data: T)
    }

    class GridViewHolder(val view: ItemGridBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val binding = ItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val childItems = childItem[position]
        holder.view.tvName.text = childItems.collectionName
        holder.view.imgMovie.loadUrl(childItems.artworkUrl)
        holder.itemView.setOnClickListener {
            clickInterface?.onClick(childItems)
        }
    }

    override fun getItemCount(): Int {
        return childItem.size
    }
    fun setItemClick(clickInterface: ClickInterface<ChildItem>) {
        this.clickInterface = clickInterface
    }
}


