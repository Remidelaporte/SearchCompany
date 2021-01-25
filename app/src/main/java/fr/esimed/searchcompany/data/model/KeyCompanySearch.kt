package fr.esimed.searchcompany.data.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Search::class,
            parentColumns=["id"],
            childColumns = ["idSearch"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Company::class,
            parentColumns = ["id"],
            childColumns = ["idCompany"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["idSearch","idCompany"])
class KeyCompanySearch(var idSearch:Long,var idCompany: Long) {
    override fun equals(other: Any?): Boolean {
        if(this===other)return true
        if (javaClass!= other?.javaClass)return false

        other as KeyCompanySearch
        if (idSearch !=other.idSearch||idCompany!=other.idCompany) return false
        return true

    }


    override fun toString(): String {
        return "KeyCompanySearch(idSearch=$idSearch, idCompany=$idCompany)"
    }

    override fun hashCode(): Int {
        var result = idSearch.hashCode()
        result = 31 * result + idCompany.hashCode()
        return result
    }

}