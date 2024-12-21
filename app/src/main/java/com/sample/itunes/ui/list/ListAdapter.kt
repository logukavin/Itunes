package com.sample.itunes.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sample.itunes.R
import com.sample.itunes.model.ChildItem
import com.sample.itunes.remote.AppConstants
import com.sample.itunes.utils.CommonUI.capitalizeWords

class ListAdapter(
    private val context: Context,
    private val groupList: List<String>,
    private val childList: Map<String, List<ChildItem>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return groupList.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getGroup(groupPosition: Int): Any {
        return groupList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return childList[groupList[groupPosition]]?.get(childPosition) ?: ""
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var view = convertView
        if (view == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_parent, parent, false)
        }

        val groupName = getGroup(groupPosition) as String
        val textView = view?.findViewById<TextView>(R.id.parentTitle)
        textView?.text = capitalizeWords(groupName)
        return view!!
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var view = convertView
        if (view == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_list_view, parent, false)
        }

        val childItem = getChild(groupPosition, childPosition) as ChildItem

        val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_list_item)
        val listItemAdapter = ListItemAdapter()
        recyclerView?.adapter = listItemAdapter
        val childItemsList = childList[groupList[groupPosition]]
        if (childItemsList != null) {
            listItemAdapter.setGridList(childItemsList)
        }

        return view!!
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}