package com.mertcaliskanyurek.noteapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mertcaliskanyurek.noteapp.R
import com.mertcaliskanyurek.noteapp.databinding.ActivityMainBinding
import com.mertcaliskanyurek.noteapp.domain.NoteEntity
import com.mertcaliskanyurek.noteapp.data.source.SourceResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val adapter = NoteRecyclerAdapter(arrayListOf())

    private val lifecycleOwner = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.fabAddNote.setOnClickListener {
            showNoteInputDialog(null) { newNote->
                viewModel.addNew(newNote).observe(lifecycleOwner) { res->
                    when(res) {
                        is SourceResult.Success -> adapter.addNewItem(res.data)
                        is SourceResult.Failure -> handleError(res.exception)
                        else -> handleError(null)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getNotes().observe(lifecycleOwner) { res->
            when(res) {
                is SourceResult.Success -> initRecyclerView(res.data)
                is SourceResult.Failure -> handleError(res.exception)
                else -> handleError(null)
            }
        }
    }

    private fun initRecyclerView(noteList: List<NoteEntity>) {
        binding.rvNoteList.layoutManager = LinearLayoutManager(lifecycleOwner)
        binding.rvNoteList.adapter = adapter
        adapter.changeDataSet(ArrayList(noteList))
        adapter.onActionListener = object : NoteRecyclerAdapter.OnActionListener {
            override fun onEditAction(item: NoteEntity, position: Int) {
                showNoteInputDialog(item) {
                    viewModel.update(item).observe(lifecycleOwner) { res->
                        when(res) {
                            is SourceResult.Success -> adapter.updateItem(res.data, position)
                            is SourceResult.Failure -> handleError(res.exception)
                            else -> handleError(null)
                        }
                    }
                }
            }

            override fun onDeleteAction(item: NoteEntity, position: Int) {
                lifecycleScope.launch {
                    viewModel.delete(item).observe(lifecycleOwner) { deleteRes->
                        when(deleteRes) {
                            is SourceResult.Success -> adapter.removeItem(position).also {
                                showSnackbar(binding.root) {
                                    //when undo pressed
                                    viewModel.undoDeleteNote(deleteRes.data).observe(lifecycleOwner) { undoRes->
                                        when(undoRes) {
                                            is SourceResult.Success -> adapter.restoreItem(undoRes.data, position)
                                            is SourceResult.Failure -> handleError(undoRes.exception)
                                            else -> handleError(null)
                                        }
                                    }
                                }
                            }
                            is SourceResult.Failure -> handleError(deleteRes.exception)
                            else -> handleError(null)
                        }
                    }
                }
            }
        }
    }

    private fun showNoteInputDialog(item: NoteEntity?, onEditFinished: (NoteEntity) -> Unit) {
        val dialog = NoteInputBottomSheetDialog(this)
        item?.let { note->
            dialog.setNote(note)
        }
        dialog.onEditFinished = onEditFinished
        dialog.show()
    }

    private fun handleError(e: Throwable?) {
        e?.printStackTrace()
        Toast.makeText(this, R.string.err_general,Toast.LENGTH_SHORT).show()
    }

    private fun showSnackbar(rootView: View, onUndo: ()->Unit) {
        Snackbar.make(rootView, R.string.note_deleted, Snackbar.LENGTH_LONG)
            .setAction(R.string.undo) { onUndo() }
            .show()
    }
}