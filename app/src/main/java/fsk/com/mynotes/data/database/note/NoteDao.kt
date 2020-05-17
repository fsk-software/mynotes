package fsk.com.mynotes.data.database.note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fsk.com.mynotes.data.NoteColor
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface NoteDao {

    @Query("SELECT * from note_table ORDER BY id ASC")
    fun getAllNotes(): Flowable<List<Note>>

    @Query("SELECT * from note_table WHERE id =:id ORDER BY id ASC LIMIT 1")
    fun getNoteById(id: Long): Single<Note>

    @Query("SELECT * from note_table WHERE color in(:colors) ORDER BY id ASC")
    fun getNotesForColors(colors: Set<NoteColor>): Flowable<List<Note>>

    @Query("SELECT * from note_table WHERE color in(:colors) AND text like :text OR title like :text ORDER BY id ASC")
    fun getNotesContainingText(text: String, colors: Set<NoteColor> = NoteColor.values().toSet()): Flowable<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note): Single<Long>

    @Update
    fun update(note: Note): Completable

    @Delete
    fun delete(note: Note): Completable

}