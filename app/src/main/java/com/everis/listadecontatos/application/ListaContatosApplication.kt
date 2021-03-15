package com.everis.listadecontatos.application

import android.app.Application
import com.everis.listadecontatos.helpers.HelperDB

class ContatosApplication : Application() {

    var helperDB: HelperDB? = null

    override fun onCreate() {
        super.onCreate()
        helperDB = HelperDB(this)
    }

}