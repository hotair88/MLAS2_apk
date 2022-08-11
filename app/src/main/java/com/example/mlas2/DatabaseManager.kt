package com.example.mlas2

import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

private const val PERSON_REFERENCE = "persons"

class DatabaseManager {

    private val database = FirebaseDatabase.getInstance()

    private fun createEntry(name: String, email: String, phoneNum: String, year: String, dept: String, member: String, food: String, size: String): Person {
        return Person(name, email, phoneNum, year, dept, member, food, size)
    }
    fun addEntry(name: String, email: String, phoneNum: String, year: String, dept: String, member: String, food: String, size: String, onSuccessAction: () -> Unit, onFailureAction: () -> Unit){
        val personReference = database.getReference(PERSON_REFERENCE)

        val key = personReference.push().key ?: ""
        val person = createEntry(name, email, phoneNum, year, dept, member, food, size)
        personReference.child(key)
            .setValue(person)
            .addOnSuccessListener { onSuccessAction() }
            .addOnFailureListener { onFailureAction() }
    }

}