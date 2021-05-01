package com.xk.cd.common.helpers

import android.content.Context
import com.xk.cd.App
import io.realm.Realm
import io.realm.RealmConfiguration

object RealmHelper {

    fun init(context: Context) {
        Realm.init(context)
        val realmConfiguration = RealmConfiguration.Builder()
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(realmConfiguration)

        // Open realm to force migration
        Realm.getDefaultInstance().close()
    }

    fun initRealmIfNotInitialized() {
        try {
            Realm.getDefaultInstance()
        } catch (e: java.lang.Exception) {
            init(App.appContext)
        }
    }
}
