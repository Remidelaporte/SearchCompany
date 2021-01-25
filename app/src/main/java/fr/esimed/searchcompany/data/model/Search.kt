package fr.esimed.searchcompany.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value=["id"],unique = true)])
class Search(@PrimaryKey(autoGenerate = true)var id:Int?=null,var query:String,var URL:String,var date:String) {
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
        return "$query"
    }
}