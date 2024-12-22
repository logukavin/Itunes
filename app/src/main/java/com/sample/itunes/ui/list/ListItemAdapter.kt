package com.sample.itunes.ui.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.itunes.application.AppController
import com.sample.itunes.databinding.ItemListBinding
import com.sample.itunes.extensions.loadUrl
import com.sample.itunes.model.ChildItem
import com.sample.itunes.ui.description.DescriptionDetailsActivity
import javax.inject.Inject

class ListItemAdapter @Inject constructor() :
    RecyclerView.Adapter<ListItemAdapter.ListViewHolder>() {

    private var childItem = mutableListOf<ChildItem>()

    fun setGridList(childItems: List<ChildItem>) {
        childItem.clear()
        childItem.addAll(childItems)
        notifyDataSetChanged()
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
            holder.itemView.setOnClickListener {
                val intent = Intent(AppController.appContext, DescriptionDetailsActivity::class.java)
                intent.putExtra("COLLECTION_NAME", childItems.collectionName)
                intent.putExtra("ARTIST_NAME", childItems.artistName)
                intent.putExtra("PREVIEW_URL", childItems.previewUrl)
                intent.putExtra("PRIMARY_GENRE_NAME", childItems.primaryGenreName)
                intent.putExtra("LONG_DESCRIPTION", childItems.longDescription)
                intent.putExtra("ART_WORK_URL", childItems.artworkUrl)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                AppController.appContext.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return childItem.size
    }

}

