package com.sample.itunes.ui.grid

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.itunes.application.AppController
import com.sample.itunes.databinding.ItemGridBinding
import com.sample.itunes.extensions.loadUrl
import com.sample.itunes.model.ChildItem
import com.sample.itunes.remote.AppConstants
import com.sample.itunes.ui.dashboard.DashBoardActivity
import com.sample.itunes.ui.description.DescriptionDetailsActivity
import javax.inject.Inject

class GridItemAdapter @Inject constructor() :
    RecyclerView.Adapter<GridItemAdapter.GridViewHolder>() {

    private var childItem = mutableListOf<ChildItem>()

    fun setGridList(childItems: List<ChildItem>) {
        childItem.clear()
        childItem.addAll(childItems)
        notifyDataSetChanged()
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
            val intent = Intent(AppController.appContext, DescriptionDetailsActivity::class.java)
            intent.putExtra(AppConstants.COLLECTION_NAME, childItems.collectionName)
            intent.putExtra(AppConstants.ARTIST_NAME, childItems.artistName)
            intent.putExtra(AppConstants.PREVIEW_URL, childItems.previewUrl)
            intent.putExtra(AppConstants.PRIMARY_GENRE_NAME, childItems.primaryGenreName)
            intent.putExtra(AppConstants.LONG_DESCRIPTION, childItems.longDescription)
            intent.putExtra(AppConstants.ART_WORK_URL, childItems.artworkUrl)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            AppController.appContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return childItem.size
    }

}


