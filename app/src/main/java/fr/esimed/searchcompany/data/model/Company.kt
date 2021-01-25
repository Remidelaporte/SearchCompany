package fr.esimed.searchcompany.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(indices=[Index(value = ["id"],unique = true)])
class Company(@PrimaryKey(autoGenerate = true)var id:Int?,var libelle:String, var depart:Int, var adress:String, var activity:String, var siret:String):Serializable {

    override fun equals(other: Any?): Boolean {
        if(this===other)return true
        if (javaClass!= other?.javaClass)return false

        other as Search
        if (id !=other.id) return false
        return true
    }
    override fun hashCode(): Int {
        return id?.hashCode() ?:0
    }
    override fun toString(): String {
        return "$libelle"
    }
}