package com.sample.itunes.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.sample.itunes.R
import com.sample.itunes.extensions.loadUrl
import com.sample.itunes.model.ChildItem
import com.sample.itunes.model.ParentItem

class ListItemAdapter(
    private val parentItems: List<ParentItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val parentType = 0
    private val childType = 1

    // Get item count based on parent and child items
    override fun getItemCount(): Int {
        var count = 0
        parentItems.forEach { parent ->
            count += 1 // For the parent item
            if (parent.isExpanded) {
                count += parent.children.size // Add child items if expanded
            }
        }
        return count
    }

    // Define different types of views (parent and child)
    override fun getItemViewType(position: Int): Int {
        var currentPos = position
        for (parent in parentItems) {
            if (currentPos == 0) {
                return parentType
            }
            currentPos -= 1
            if (parent.isExpanded) {
                val childCount = parent.children.size
                if (currentPos < childCount) {
                    return childType
                }
                currentPos -= childCount
            }
        }
        return parentType
    }

    // Create ViewHolder for ParentItem
    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.parentTitle)

        fun bind(position: Int) {
            if (position < parentItems.size) {
                val parentItem = parentItems[position]
                title.text = parentItem.kind
            }


        }
    }

    // Create ViewHolder for ChildItem
    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val imageUrl: AppCompatImageView = itemView.findViewById(R.id.img_movie)

        fun bind(childItem: ChildItem) {
            name.text = childItem.collectionName
            imageUrl.loadUrl(childItem.artworkUrl)
        }
    }

    // Bind data to ViewHolders
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var currentPos = position
        for (parent in parentItems) {
            if (currentPos == 0) {
                (holder as ParentViewHolder).bind(position)
                return
            }
            currentPos -= 1
            if (parent.isExpanded) {
                val childCount = parent.children.size
                if (currentPos < childCount) {
                    (holder as ChildViewHolder).bind(parent.children[currentPos])
                    return
                }
                currentPos -= childCount
            }
        }
    }

    // Create the appropriate ViewHolder based on item view type
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == parentType) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_parent, parent, false)
            ParentViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
            ChildViewHolder(view)
        }
    }
}
