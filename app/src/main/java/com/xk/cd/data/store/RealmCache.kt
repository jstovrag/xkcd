package com.xk.cd.data.store

import io.realm.Realm
import io.realm.RealmObject

class RealmCache<T : RealmObject> {

//    private val realm by lazy {
//        Realm.getDefaultInstance()
//    }
//
//    fun load(entityClass: Class<T>): List<T> {
//        return realm.where(entityClass).findAll()
//    }
//
//    fun save(realmObject: T) {
//        realm.beginTransaction()
//        realm.copyToRealm(realmObject)
//        realm.commitTransaction()
//    }
//
//    /**
//     * Object must have PrimaryKey
//     */
//    fun updateOrCreate(`object`: T) {
//        realm.beginTransaction()
//        realm.copyToRealmOrUpdate(`object`)
//        realm.commitTransaction()
//    }
//
//    /**
//     * Object must have PrimaryKey.
//     */
//    fun updateOrCreate(objects: List<RealmObject>, deleteIfMissing: Boolean) {
//        if (objects.isEmpty()) {
//            return
//        }
//        val classObject = objects[0].javaClass
//        realm.beginTransaction()
//        val liveObjects = realm.copyToRealmOrUpdate(objects)
//
//        // If true, all objects in DB which are not updated or added will be deleted
//        if (deleteIfMissing) {
//            val allDbObjects = realm.where(classObject).findAll()
//            for (i in allDbObjects.indices) {
//                if (!liveObjects.contains(allDbObjects[i])) {
//                    allDbObjects.deleteFromRealm(i)
//                }
//            }
//        }
//        realm.commitTransaction()
//    }
//
//    fun <E : RealmObject> copyFromRealm(realmObject: E?): E? {
//        return if (realmObject != null) {
//            realm.copyFromRealm(realmObject)
//        } else {
//            null
//        }
//    }
//
//    fun delete(item: RealmObject) {
//        val classObject = item.javaClass
//        realm.beginTransaction()
//        val allDbObjects = realm.where(classObject).findAll()
//        allDbObjects.deleteAllFromRealm()
//        realm.commitTransaction()
//    }
//
//    fun deleteAll(entityClass: Class<T>) {
//        realm.beginTransaction()
//        realm.delete(entityClass)
//        realm.commitTransaction()
//    }
}
