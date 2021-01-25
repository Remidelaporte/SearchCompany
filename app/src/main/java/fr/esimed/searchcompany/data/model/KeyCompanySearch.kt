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
class KeyCompanySearch(var idSearch:Int,var idCompany: Int) {
    override fun equals(other: Any?): Boolean {
        if(this===other)return true
        if (javaClass!= other?.javaClass)return false

        other as KeyCompanySearch
        if (idSearch !=other.idSearch||idCompany!=other.idCompany) return false
        return true

    }

    override fun hashCode(): Int {
        var result = idSearch
        result = 31 * result + idCompany
        return result
    }

    override fun toString(): String {
        return "KeyCompanySearch(idSearch=$idSearch, idCompany=$idCompany)"
    }

}