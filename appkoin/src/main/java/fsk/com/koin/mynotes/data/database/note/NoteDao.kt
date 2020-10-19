package fsk.com.koin.mynotes.data.database.note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Dao to interact with the note in the database
 */
@Dao
interface NoteDao {

    /**
     * Get all of the notes in the database sorted by creation order.
     *
     * @return an emitter that emits a list of all notes sorted by creation order.
     */
    @Query("SELECT * from note_table ORDER BY id ASC")
    fun getAllNotes(): Flowable<List<Note>>

    /**
     * Get the first note found with the id.
     *
     * @return [Single] containing the found note.
     */
    @Query("SELECT * from note_table WHERE id =:id ORDER BY id ASC LIMIT 1")
    fun getNoteById(id: Long?): Single<Note>

    /**
     * Insert the note into the database
     *
     * @return [Single] containing the inserted note.  The note's id will be non-null after successful insertion.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note): Single<Long>

    /**
     * Update the note in the database
     *
     * @return [Completable] to monitor for operation success.
     */
    @Update
    fun update(note: Note): Completable

    /**
     * Delete the note from the database
     *
     * @return [Completable] to monitor for operation success.
     */
    @Delete
    fun delete(note: Note): Completable

}