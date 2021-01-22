package fr.esimed.searchcompany.data

import java.io.Serializable

class Company(var libelle:String,var depart:Int,var adress:String,var activity:String,var siret:String):Serializable {

    override fun toString(): String {
        return "$libelle"
    }
}