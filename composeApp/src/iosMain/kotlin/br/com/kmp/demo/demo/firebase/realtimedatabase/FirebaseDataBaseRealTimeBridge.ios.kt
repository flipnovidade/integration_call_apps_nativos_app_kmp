package br.com.kmp.demo.demo.firebase.realtimedatabase

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
actual class FirebaseDataBaseRealTimeBridge(private val delegateFirebaseRealTimeDataBase: FirebaseRealTimeDataBase) : FirebaseRealTimeDataBase {
    actual override fun fetchDataNodo(
        nodoName: String,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit,
    ) {
        delegateFirebaseRealTimeDataBase.fetchDataNodo(nodoName, onSuccess, onError)
    }

    actual override fun putDataByNodoName(
        nodoName: String,
        keyNewNodo: String,
        valueForNodo: String,
    ) {
        delegateFirebaseRealTimeDataBase.putDataByNodoName(nodoName = nodoName, keyNewNodo = keyNewNodo, valueForNodo = valueForNodo)
    }
}