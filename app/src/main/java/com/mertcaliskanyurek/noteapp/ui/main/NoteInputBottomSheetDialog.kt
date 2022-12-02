package com.mertcaliskanyurek.noteapp.ui.main

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mertcaliskanyurek.noteapp.R
import com.mertcaliskanyurek.noteapp.databinding.NoteInputBottomSheetBinding
import com.mertcaliskanyurek.noteapp.domain.NoteEntity
import java.util.*


class NoteInputBottomSheetDialog(context: Context) : BottomSheetDialog(context) {

    private val binding: NoteInputBottomSheetBinding = NoteInputBottomSheetBinding.inflate(layoutInflater)
    var onEditFinished: (NoteEntity) -> Unit = {}

    init {
        setContentView(binding.root)
        setOnShowListener {
            Handler(Looper.getMainLooper()).postDelayed({
                val d = this
                val bottomSheet: View = d.findViewById<FrameLayout>(R.id.design_bottom_sheet) as View
                val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED)
            }, 100)
        }
        binding.etTitle.requestFocus()

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        binding.saveButton.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val text = binding.etText.text.toString()
            val imageUrl = binding.etImageUrl.text.toString()

            if (title.isEmpty()) {
                binding.tilTitle.error = context.getString(R.string.err_field_cannot_be_blank)
                return@setOnClickListener
            }

            if(text.isEmpty()) {
                binding.tilText.error = context.getString(R.string.err_field_cannot_be_blank)
                return@setOnClickListener
            }

            binding.note?.let {
                it.title = title
                it.text = text
                it.lastEditDate = Date()
                it.imageUrl = imageUrl
                onEditFinished(it)
                dismiss()
            }?: run {
                val newNote = NoteEntity(
                    id=0,
                    title=title,
                    text=text,
                    createDate = Date(),
                    lastEditDate = Date(),
                    imageUrl = imageUrl)
                onEditFinished(newNote)
                dismiss()
            }
        }
    }

    fun setNote(note: NoteEntity) {
        binding.note = note
    }
}