//
//  SwiftFirebaseDataBaseRealTime.swift
//  iosApp
//
//  Created by Felippe Ferreira on 06/07/2025.
//

import FirebaseDatabase
import ComposeApp

@objc public class SwiftFirebaseDataBaseRealTime: NSObject, FirebaseRealTimeDataBase {
    let myFireBaseDataBase = Database.database()
    
    @objc public func fetchDataNodo(nodoName: String,
                                    onSuccess: @escaping (String) -> Void,
                                    onError: @escaping (KotlinException) -> Void) {
        
        let reference = myFireBaseDataBase.reference(withPath: nodoName)
        reference.observe(.value) { snapshot in
            print("fetchDataNodo: Value snapshot is: \(snapshot)")
            onSuccess(snapshot.description)
        } withCancel: { error in
            print("fetchDataNodo: Cancelled with error: \(error.localizedDescription)")
            onError(error as! KotlinException)
        }
    }
    
    @objc public func putDataByNodoName(nodoName: String, keyNewNodo: String, valueForNodo: String) {
        
        let values: [String: String] = [
                    keyNewNodo: valueForNodo
                ]
                
        let reference = myFireBaseDataBase.reference(withPath: nodoName)
        reference.updateChildValues(values) { error, _ in
            if let error = error {
                print("putDataByNodoName: Fail \(error.localizedDescription)")
                // Se quiser lançar, pode usar uma completion ou delegate
            } else {
                print("putDataByNodoName: Success \(nodoName)")
            }
        }
    }
    
}
