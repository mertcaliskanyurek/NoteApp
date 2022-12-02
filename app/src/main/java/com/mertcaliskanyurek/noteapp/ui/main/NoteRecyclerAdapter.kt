package com.mertcaliskanyurek.noteapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mertcaliskanyurek.noteapp.databinding.NoteRecyclerItemLayoutBinding
import com.mertcaliskanyurek.noteapp.domain.NoteEntity
import com.mertcaliskanyurek.noteapp.ui.common.GenericRecyclerAdapter

class NoteRecyclerAdapter(dataList: ArrayList<NoteEntity>): GenericRecyclerAdapter<NoteEntity>(dataList)
{
    var onActionListener: OnActionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<NoteEntity> {
        val binding = NoteRecyclerItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return object : BaseViewHolder<NoteEntity>(binding){}
    }

    override fun onBindViewHolder(holder: BaseViewHolder<NoteEntity>, position: Int) {
        val binding = holder.dataBinding as NoteRecyclerItemLayoutBinding
        binding.note = dataList[position]

        Glide.with(holder.itemView.context).load(dataList[position].imageUrl).into(binding.iwPicture)

        itemClickListener?.let { listener ->
            binding.root.setOnClickListener { listener.onItemClick(dataList[position],position) }
        }
        onActionListener?.let { listener ->
            binding.editButton.setOnClickListener { listener.onEditAction(dataList[position], position) }
            binding.deleteButton.setOnClickListener { listener.onDeleteAction(dataList[position], position) }
        }
    }

    interface OnActionListener {
        fun onEditAction(item: NoteEntity, position: Int)
        fun onDeleteAction(item:NoteEntity, position: Int)
    }
}