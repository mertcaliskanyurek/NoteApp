package com.mertcaliskanyurek.noteapp.ui.common

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class GenericRecyclerAdapter<T> (
    protected var dataList: ArrayList<T>
) : RecyclerView.Adapter<GenericRecyclerAdapter.BaseViewHolder<T>>() {

    var itemClickListener: ItemClickListener<T>? = null

    override fun getItemCount(): Int = dataList.size

    fun changeDataSet(dataList: ArrayList<T>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    fun addNewItem(item: T) {
        dataList.add(item)
        notifyItemInserted(dataList.size-1)
    }

    fun removeItem(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: T, position: Int) {
        dataList.add(position, item)
        notifyItemInserted(position)
    }

    fun updateItem(item: T, position: Int) {
        dataList[position] = item
        notifyItemChanged(position)
    }

    abstract class BaseViewHolder<T>(val dataBinding: ViewDataBinding)
        : RecyclerView.ViewHolder(dataBinding.root)

    fun interface ItemClickListener<T> {
        fun onItemClick(item: T, position: Int)
    }

}