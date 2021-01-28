package fr.esimed.searchcompany.data

import androidx.room.*
import fr.esimed.searchcompany.data.model.CodeNAFAPE

@Dao
interface CodeNafDAO {

    @Query("Select * from codenafape where CodeNAFAPE LIKE :code")
    fun getNAFLike(code:String):List<CodeNAFAPE>

    @Query("Select * from codenafape where Description LIKE :code")
    fun getactiveLike(code:String):List<CodeNAFAPE>
    @Insert
    fun insert(codenaf: CodeNAFAPE)
    @Update
    fun update(codenaf: CodeNAFAPE)
    @Delete
    fun delete(codenaf: CodeNAFAPE)
}