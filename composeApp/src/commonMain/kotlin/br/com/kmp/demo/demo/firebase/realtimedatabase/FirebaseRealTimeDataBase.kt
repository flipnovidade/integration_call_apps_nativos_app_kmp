package br.com.kmp.demo.demo.firebase.realtimedatabase

interface FirebaseRealTimeDataBase {
    fun fetchDataNodo(nodoName: String = "messages",
                      onSuccess: (String) -> Unit,
                      onError: (Exception) -> Unit)
    fun putDataByNodoName(nodoName: String, keyNewNodo: String, valueForNodo: String)
}