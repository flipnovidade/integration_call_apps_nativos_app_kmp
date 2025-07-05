package br.com.kmp.demo.demo.firebase.realtimedatabase

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
actual class FirebaseDataBaseRealTimeBridge :
    FirebaseRealTimeDataBase {
    actual override fun fetchDataNodo(
        nodoName: String,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit,
    ) {
    }

    actual override fun putDataByNodoName(
        nodoName: String,
        keyNewNodo: String,
        valueForNodo: String,
    ) {
    }
}