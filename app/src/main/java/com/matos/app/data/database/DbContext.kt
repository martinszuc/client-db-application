package com.matos.app.data.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.matos.app.data.entity.Client
import com.matos.app.data.entity.Service
import java.text.SimpleDateFormat
import java.util.*

class DbContext(context: Context,
                factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context,
        DATABASE_NAME, factory,
        DATABASE_VERSION
    ) {

    companion object {
        // Database Information
        private const val DATABASE_NAME = "KOZMETIKA_DB"
        private const val DATABASE_VERSION = 1

        // Table Names
        private const val TABLE_CLIENTS = "clients"
        private const val TABLE_SERVICES = "services"

        // Clients Table - column names
        private const val COL_ID = "id"
        internal const val COL_NAME = "name"
        internal const val COL_PHONE = "phone"
        internal const val COL_EMAIL = "email"

        // Services Table - column names
        internal const val COL_CLIENT_ID = "client_id"
        internal const val COL_DESCRIPTION = "description"
        internal const val COL_DATE = "date"
        internal const val COL_PRICE = "price"
    }

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        try {
            // create clients table
            db.execSQL("CREATE TABLE $TABLE_CLIENTS ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME TEXT NOT NULL, $COL_PHONE TEXT, $COL_EMAIL TEXT)")

            // create services table
            db.execSQL("CREATE TABLE $TABLE_SERVICES ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_CLIENT_ID INTEGER, $COL_DESCRIPTION TEXT, $COL_DATE DATE, $COL_PRICE REAL, FOREIGN KEY($COL_CLIENT_ID) REFERENCES $TABLE_CLIENTS($COL_ID))")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        try {
            // this method is to check if table already exists
            db.execSQL("DROP TABLE IF EXISTS clients")
            db.execSQL("DROP TABLE IF EXISTS services")
            onCreate(db)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // This method is for adding data in our database
    fun addClient(name: String, phone: String?, email: String?): Long {
        try {
            val values = ContentValues().apply {
                put(COL_NAME, name)
                put(COL_PHONE, phone)
                put(COL_EMAIL, email)
            }
            return writableDatabase.insert(TABLE_CLIENTS, null, values)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return -1
    }



    // below method is to get
    // all data from our database
    @SuppressLint("Range")
    fun getClients(): List<Client> {
        val clients = mutableListOf<Client>()
        try {
            // here we are creating a readable
            // variable of our database
            // as we want to read value from it
            val db = this.readableDatabase

            // below code returns a cursor to
            // read data from the database
            val cursor = db.rawQuery("SELECT $COL_ID, $COL_NAME, $COL_PHONE, $COL_EMAIL FROM $TABLE_CLIENTS", null)
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    val client = Client(
                        cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_NAME)),
                        cursor.getString(cursor.getColumnIndex(COL_PHONE)),
                        cursor.getString(cursor.getColumnIndex(COL_EMAIL))

                    )
                    clients.add(client)
                    cursor.moveToNext()
                }
                cursor.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return clients
    }

    //method for retrieving services associated with the client
    @SuppressLint("Range")
    fun getServicesForClient(clientId: Int?): List<Service> {
        val services = mutableListOf<Service>()
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_SERVICES WHERE $COL_CLIENT_ID=?", arrayOf(clientId.toString()))
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                val description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))
                val dateStr = cursor.getString(cursor.getColumnIndex(COL_DATE))
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateStr)
                val price = cursor.getDouble(cursor.getColumnIndex(COL_PRICE))
                services.add(Service(id, clientId, description, date, price))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return services
    }

    // This method is for adding a service to the services table
    fun addService(service: Service) {
        try {
            // Create a ContentValues object to hold the data to be inserted
            val values = ContentValues().apply {
                put(COL_CLIENT_ID, service.clientId)
                put(COL_DESCRIPTION, service.description)
                // Create a date format object that will format the date as a string in the format "yyyy-MM-dd"
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val dateString = dateFormat.format(service.date)
                put(COL_DATE, dateString)
                put(COL_PRICE, service.price)
            }

            // Get a writable database instance
            val db = writableDatabase

            // Insert the data into the table
            db.insert(TABLE_SERVICES, null, values)
        } catch (e: Exception) {
            Log.e(TAG, "Error inserting service: ${e.message}")
        } finally {
            // Close the database
            writableDatabase.close()
        }
    }

    @SuppressLint("Range")
    fun getServices(): List<Service> {
        val services = mutableListOf<Service>()
        try {
            val db = this.readableDatabase
            val servicesCursor = db.rawQuery("SELECT $COL_ID, $COL_CLIENT_ID, $COL_DESCRIPTION, $COL_DATE, $COL_PRICE FROM $TABLE_SERVICES", null)
            if (servicesCursor != null && servicesCursor.count > 0) {
                servicesCursor.moveToFirst()
                while (!servicesCursor.isAfterLast) {
                    val service = Service(
                        servicesCursor.getInt(servicesCursor.getColumnIndex(COL_ID)),
                        servicesCursor.getInt(servicesCursor.getColumnIndex(COL_CLIENT_ID)),
                        servicesCursor.getString(servicesCursor.getColumnIndex(COL_DESCRIPTION)),
                        Date(servicesCursor.getLong(servicesCursor.getColumnIndex(COL_DATE))),
                        servicesCursor.getDouble(servicesCursor.getColumnIndex(COL_PRICE))
                    )
                    services.add(service)
                    servicesCursor.moveToNext()
                }
                servicesCursor.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return services
    }

    @SuppressLint("Range")
    fun searchClients(query: String): List<Client> {
        val clients = mutableListOf<Client>()

        val selectQuery = "SELECT * FROM $TABLE_CLIENTS WHERE $COL_NAME LIKE '%$query%'"
        val cursor = readableDatabase.rawQuery(selectQuery, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(COL_ID))
            val name = cursor.getString(cursor.getColumnIndex(COL_NAME))
            val phone = cursor.getString(cursor.getColumnIndex(COL_PHONE))
            val email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))

            val client = Client(id, name, phone, email)
            clients.add(client)
        }

        cursor.close()

        return clients
    }

    @SuppressLint("Range")
    fun searchServices(query: String): List<Service> {
        val services = mutableListOf<Service>()

        val selectQuery = "SELECT * FROM $TABLE_SERVICES WHERE $COL_DESCRIPTION LIKE '%$query%'"
        val cursor = readableDatabase.rawQuery(selectQuery, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(COL_ID))
            val clientId = cursor.getInt(cursor.getColumnIndex(COL_CLIENT_ID))
            val description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))
            val date = Date(cursor.getLong(cursor.getColumnIndex(COL_DATE)))
            val price = cursor.getDouble(cursor.getColumnIndex(COL_PRICE))

            val service = Service(id, clientId, description, date, price)
            services.add(service)
        }

        cursor.close()

        return services
    }




    fun insertDummyServices() {
        val db = this.writableDatabase
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Add dummy services to the database
        db.execSQL("INSERT INTO $TABLE_SERVICES ($COL_CLIENT_ID, $COL_DESCRIPTION, $COL_DATE, $COL_PRICE) VALUES (1, 'Service 1', '${sdf.format(Date())}', 100.0)")
        db.execSQL("INSERT INTO $TABLE_SERVICES ($COL_CLIENT_ID, $COL_DESCRIPTION, $COL_DATE, $COL_PRICE) VALUES (2, 'Service 2', '${sdf.format(Date())}', 200.0)")
        db.execSQL("INSERT INTO $TABLE_SERVICES ($COL_CLIENT_ID, $COL_DESCRIPTION, $COL_DATE, $COL_PRICE) VALUES (1, 'Service 3', '${sdf.format(Date())}', 150.0)")
        db.execSQL("INSERT INTO $TABLE_SERVICES ($COL_CLIENT_ID, $COL_DESCRIPTION, $COL_DATE, $COL_PRICE) VALUES (2, 'Service 4', '${sdf.format(Date())}', 250.0)")
    }
    fun insertDummyClients() {
        try {
            val db = this.writableDatabase
            val names = arrayOf("John Doe", "Jane Smith", "Bob Johnson", "Mary Wilson", "David Lee")
            val phones = arrayOf("1234567890", "2345678901", "3456789012", "4567890123", "5678901234")
            val emails = arrayOf("john.doe@example.com", "jane.smith@example.com", "bob.johnson@example.com", "mary.wilson@example.com", "david.lee@example.com")
            for (i in 0 until 5) {
                val values = ContentValues()
                values.put(COL_NAME, names[i])
                values.put(COL_PHONE, phones[i])
                values.put(COL_EMAIL, emails[i])
                db.insert(TABLE_CLIENTS, null, values)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error inserting dummy clients: ${e.message}")
        } finally {
            // Close the database
            writableDatabase.close()
        }
    }
}