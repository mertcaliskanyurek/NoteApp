package com.mertcaliskanyurek.noteapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mertcaliskanyurek.noteapp.data.AppDatabase
import com.mertcaliskanyurek.noteapp.data.dao.NoteRoomDao
import com.mertcaliskanyurek.noteapp.domain.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
@SmallTest
class NotesDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var notesDao: NoteRoomDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java)
            .allowMainThreadQueries().build()
        notesDao = database.noteDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertNote_returnsNoteInserted() = runBlocking<Unit> {
        val note = NoteEntity(1,"test","test","", Date(),Date())
        val job = async(Dispatchers.IO) {
            notesDao.insert(note)
            val inserted = notesDao.get(1)
            assertThat(inserted.toString()).isEqualTo(note.toString())
        }
        job.cancelAndJoin()
    }

    @Test
    fun updateNote_returnsNoteUpdated() = runBlocking<Unit> {
        val job = async(Dispatchers.IO) {
            val toUpdate = notesDao.get(1)!!
            toUpdate.text = "test updated"
            notesDao.update(toUpdate)
            val updated = notesDao.get(1)!!
            assertThat(updated.toString()).isEqualTo(toUpdate.toString())
        }
        job.cancelAndJoin()
    }

    @Test
    fun putTrashNote_returnsNull() = runBlocking<Unit> {
        val job = async(Dispatchers.IO) {
            notesDao.putTrash(1)
            val updated = notesDao.get(1)
            assertThat(updated).isEqualTo(null)
        }
        job.cancelAndJoin()
    }

    @Test
    fun undoTrashNote_returnsNote() = runBlocking<Unit> {
        val job = async(Dispatchers.IO) {
            notesDao.undoTrash(1)
            val updated = notesDao.get(1)
            assertThat(updated).isNotEqualTo(null)
        }
        job.cancelAndJoin()
    }

}