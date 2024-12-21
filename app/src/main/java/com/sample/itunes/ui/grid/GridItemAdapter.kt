package com.sample.itunes.ui.grid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sample.itunes.R
import com.sample.itunes.model.ChildItem
import com.sample.itunes.model.ParentItem

class GridItemAdapter (private val parentItems: List<ParentItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val parentType = 0
    private val childType = 1

    override fun getItemCount(): Int {
        var count = 0
        parentItems.forEach { parent ->
            count += 1 // For the parent item
            if (parent.isExpanded) {
                count += parent.children.size
            }
        }
        return count
    }

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

    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.parentTitle)

        fun bind(position: Int) {
            if (position < parentItems.size) {
                val parentItem = parentItems[position]
                title.text = parentItem.kind
            }


        }
    }

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: RecyclerView = itemView.findViewById(R.id.rv_item)

        fun bind(childItem: List<ChildItem>) {
            val gridListAdapter = GridListAdapter()
            name.adapter = gridListAdapter
            gridListAdapter.gridList(childItem)

        }
    }

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
                    (holder as ChildViewHolder).bind(parent.children)
                    return
                }
                currentPos -= childCount
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == parentType) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_parent, parent, false)
            ParentViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
            ChildViewHolder(view)
        }
    }
}
