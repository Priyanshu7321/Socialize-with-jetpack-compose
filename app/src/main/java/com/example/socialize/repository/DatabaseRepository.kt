package com.example.socialize.repository

import android.util.Log
import androidx.core.view.OneShotPreDrawListener.add
import com.couchbase.lite.DataSource
import com.couchbase.lite.Database
import com.couchbase.lite.Document
import com.couchbase.lite.Meta
import com.couchbase.lite.MutableArray
import com.couchbase.lite.MutableDictionary
import com.couchbase.lite.MutableDocument
import com.couchbase.lite.QueryBuilder
import com.couchbase.lite.SelectResult
import com.example.socialize.entity.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(private val database: Database) {

    fun insertData(user: User) {
        val document = MutableDocument().apply {
            setString("id", user.id)
            setString("username", user.username)
            setString("name", user.name)
            setString("email", user.email)
            setString("password", user.password)
            setString("age", user.age)
            setString("MobileNum",user.mobileNum);
        }
        database.save(document)
        Log.d("Couchbase", "Document inserted: ${document.id}")
    }


    fun getData(id: String): Document? {
        return database.getDocument(id)
    }

    fun updateData(id: String, updates: Map<String, Any>) {
        val document = database.getDocument(id) ?: return
        val mutableDoc = document.toMutable()

        for ((key, value) in updates) {
            mutableDoc.setString(key, value.toString())
        }
        database.getCollection(id)?.save(mutableDoc)
        Log.d("Couchbase", "Updated document: $id with values: $updates")
    }


    fun deleteData(id: String) {
        val document = database.getDocument(id) ?: return
        database.delete(document)
        Log.d("Couchbase", "Deleted document: $id")
    }

    fun queryData(): MutableList<Map<String, Any?>> {
        val query = QueryBuilder.select(SelectResult.expression(Meta.id), SelectResult.all())
            .from(DataSource.database(database))

        val result = query.execute()
        val documentsList = mutableListOf<Map<String, Any?>>()

        for (row in result) {
            val documentId = row.getString("id") ?: continue
            val data = row.getDictionary("my_database")?.toMap() ?: continue
            val documentMap = mutableMapOf<String, Any?>("id" to documentId) + data
            documentsList.add(documentMap)
        }
        return documentsList
    }

}
