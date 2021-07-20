package org.bedu.realmexample

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

//Se escribe open para poder heredar
open public class Contact: RealmObject() {

    //normalmente este id puede ser un string aleatorio UUID.randomUUID().toString(), pero en este caso dejaremos un incremental
    @PrimaryKey
    var id: Int? = null

    var name: String? = null

    var job: String? = null

    var company: String? = null

    var city: String? = null
}