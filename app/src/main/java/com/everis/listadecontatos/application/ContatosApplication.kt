package com.everis.listadecontatos.application

import android.app.Application
import com.everis.listadecontatos.helpers.HelperDB

class ContatosApplication : Application() {

    var helperDB: HelperDB? = null
        private set
    var instance: ContatosApplication? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(this)
    }

}