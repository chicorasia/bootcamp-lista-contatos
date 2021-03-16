package com.everis.listadecontatos.helpers

import android.content.Context

class TesteHelperDB(context: Context) : HelperDB(context = context) {

    fun limpaDatabase() {
        val db = writableDatabase
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

}