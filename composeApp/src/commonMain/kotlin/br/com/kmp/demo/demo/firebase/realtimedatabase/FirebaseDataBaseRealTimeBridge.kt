package br.com.kmp.demo.demo.firebase.realtimedatabase

 @Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
 expect class FirebaseDataBaseRealTimeBridge: FirebaseRealTimeDataBase {

     override fun fetchDataNodo(
         nodoName: String,
         onSuccess: (String) -> Unit,
         onError: (Exception) -> Unit,
     )

     override fun putDataByNodoName(nodoName: String, keyNewNodo: String, valueForNodo: String)

 }