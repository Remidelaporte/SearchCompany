package fr.esimed.searchcompany.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(indices=[Index(value=["CodeNAFAPE"],unique = true)])
class CodeNAFAPE(@PrimaryKey(autoGenerate = false)var CodeNAFAPE:String,
                 var Description:String,
                 var Section:String,
                 var DescripSection:String):Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CodeNAFAPE

        if (CodeNAFAPE != other.CodeNAFAPE) return false
        if (Description != other.Description) return false
        if (Section != other.Section) return false
        if (DescripSection != other.DescripSection) return false

        return true
    }

    override fun hashCode(): Int {
        var result = CodeNAFAPE.hashCode()
        result = 31 * result + Description.hashCode()
        result = 31 * result + Section.hashCode()
        result = 31 * result + DescripSection.hashCode()
        return result
    }

    override fun toString(): String {
        return "$CodeNAFAPE - $Description"
    }


}