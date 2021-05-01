package com.xk.cd.data.store

import com.xk.cd.App
import com.xk.cd.data.models.comic.Comic
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RealmCache<T : RealmObject> @Inject constructor() {

//class RealmCache<T : RealmObject> {

    private val realm by lazy {
        Realm.getDefaultInstance()
    }

    fun load(entityClass: Class<T>): List<T> {
        return realm.where(entityClass).findAll()
    }

    fun save(realmObject: T) {
        realm.beginTransaction()
        realm.copyToRealm(realmObject)
        realm.commitTransaction()
    }

    /**
     * Object must have PrimaryKey
     */
    fun updateOrCreate(`object`: T) {
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(`object`)
        realm.commitTransaction()
    }

    /**
     * Object must have PrimaryKey.
     */
    fun updateOrCreate(objects: List<RealmObject>, deleteIfMissing: Boolean) {
        if (objects.isEmpty()) {
            return
        }
        val classObject = objects[0].javaClass
        realm.beginTransaction()
        val liveObjects = realm.copyToRealmOrUpdate(objects)

        // If true, all objects in DB which are not updated or added will be deleted
        if (deleteIfMissing) {
            val allDbObjects = realm.where(classObject).findAll()
            for (i in allDbObjects.indices) {
                if (!liveObjects.contains(allDbObjects[i])) {
                    allDbObjects.deleteFromRealm(i)
                }
            }
        }
        realm.commitTransaction()
    }

    fun <E : RealmObject> copyFromRealm(realmObject: E?): E? {
        return if (realmObject != null) {
            realm.copyFromRealm(realmObject)
        } else {
            null
        }
    }

    fun delete(item: RealmObject) {
        val classObject = item.javaClass
        realm.beginTransaction()
        val allDbObjects = realm.where(classObject).findAll()
        allDbObjects.deleteAllFromRealm()
        realm.commitTransaction()
    }

    fun deleteComic(item: RealmObject) {
        realm.beginTransaction()
        val result: RealmResults<Comic> =
            realm.where(Comic::class.java).equalTo("num", (item as Comic).num).findAll()
        result.deleteAllFromRealm()
        realm.commitTransaction()
    }

    fun deleteAll(entityClass: Class<T>) {
        realm.beginTransaction()
        realm.delete(entityClass)
        realm.commitTransaction()
    }

    fun initRealmIfNotInitialized() {
        try {
            Realm.getDefaultInstance()
        } catch (e: java.lang.Exception) {
            Realm.init(App.appContext)
        }
    }
}
