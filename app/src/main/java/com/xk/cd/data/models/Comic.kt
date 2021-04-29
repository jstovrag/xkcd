package com.xk.cd.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Comic(
    @PrimaryKey
    var id: Long = 0,
    var name: String = ""
) : RealmObject()
