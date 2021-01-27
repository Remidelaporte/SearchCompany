package fr.esimed.searchcompany.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import fr.esimed.searchcompany.data.model.CodeNAFAPE

@Dao
interface CodeNafDAO {
    @Insert
    fun insert(codenaf: CodeNAFAPE)
    @Update
    fun update(codenaf: CodeNAFAPE)
    @Delete
    fun delete(codenaf: CodeNAFAPE)
}