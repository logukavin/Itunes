package com.sample.itunes.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.itunes.databinding.ItemListBinding
import com.sample.itunes.extensions.loadUrl
import com.sample.itunes.model.ChildItem
import javax.inject.Inject

class ListItemAdapter @Inject constructor() :
    RecyclerView.Adapter<ListItemAdapter.ListViewHolder>() {

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

    class ListViewHolder(val view: ItemListBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
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

