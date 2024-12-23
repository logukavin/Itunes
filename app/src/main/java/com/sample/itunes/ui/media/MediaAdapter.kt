package com.sample.itunes.ui.media

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.itunes.databinding.ItemMediaBinding
import com.sample.itunes.model.MediaOption

class MediaAdapter : RecyclerView.Adapter<MediaAdapter.MediaViewHolder>() {
    private var options: List<MediaOption> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun updateMediaAdapter(newOptions: List<MediaOption>) {
        options = newOptions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        return MediaViewHolder(ItemMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(options[position])
    }

    override fun getItemCount() = options.size

    fun getCheckedItems(): List<MediaOption> {
        return options.filter { it.isSelected }
    }

    class MediaViewHolder(private val view: ItemMediaBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(mediaOption: MediaOption) {
            view.tvName.text = mediaOption.name
            view.checkBox.isChecked = mediaOption.isSelected
            view.checkBox.setOnCheckedChangeListener(null)
            view.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                mediaOption.isSelected=isChecked
            }
            itemView.setOnClickListener {
                mediaOption.isSelected = !mediaOption.isSelected
                view.checkBox.isChecked = mediaOption.isSelected
            }
        }
    }
}

