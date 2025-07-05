package br.com.kmp.demo.demo.firebase.realtimedatabase

import br.com.kmp.demo.demo.ui.components.KmpLogger
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
actual class FirebaseDataBaseRealTimeBridge : FirebaseRealTimeDataBase {

    private val myFireBaseDataBase: FirebaseDatabase = Firebase.database

    actual override fun fetchDataNodo(nodoName: String,
                                      onSuccess: (String) -> Unit,
                                      onError: (Exception) -> Unit) {

        myFireBaseDataBase.getReference( nodoName)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    KmpLogger.d("fetchDataNodo", "Value is: $snapshot")
                    onSuccess(snapshot.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    onCancelled(error)
                }
            })
    }

    actual override fun putDataByNodoName(nodoName: String, keyNewNodo: String, valueForNodo: String) {

        val newValues = mapOf(
            keyNewNodo to valueForNodo
        )

        myFireBaseDataBase.getReference(nodoName)
            .setValue(newValues)
            .addOnSuccessListener {
                KmpLogger.d("putDataByNodoName", "Sucess $nodoName")
            }
            .addOnFailureListener { error ->
                KmpLogger.d("putDataByNodoName", "Fail $error")
                throw Exception("Fail save")
            }
    }


}