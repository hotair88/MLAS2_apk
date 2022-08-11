package com.example.mlas2

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(
    val name: String = "",
    val emailAddress: String = "",
    val phoneNumber: String = "",
    val year: String = "",
    val dept: String = "",
    val memberOrNot: String = "",
    val vegOrNon: String = "",
    val tshirtSize: String = "",
) : Parcelable {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "Name" to name,
            "Email" to emailAddress,
            "Number" to phoneNumber,
            "Year" to year,
            "Department" to dept,
            "Membership" to memberOrNot,
            "Food Preference" to vegOrNon,
            "Tshirt Size" to tshirtSize
        )
    }

    override fun describeContents() = 0

}