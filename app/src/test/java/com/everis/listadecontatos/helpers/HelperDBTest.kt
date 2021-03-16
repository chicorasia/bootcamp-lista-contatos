package com.everis.listadecontatos.helpers

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HelperDBTest{

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val helperDB: TesteHelperDB = TesteHelperDB(context = context)

    @Before
    fun setup(){
        helperDB.limpaDatabase()
        println("Limpei a database")
    }

    @Test
    fun deve_RetornarUmaListaComDoisItens_AposAdicionarDoisContatosNaDb(){

        helperDB.salvarContato(ContatosVO(nome = "Han Solo", telefone = "8888-8888"))
        helperDB.salvarContato(ContatosVO(nome = "Luke Skywalker", telefone = "9999-9999"))

        assertEquals(2, helperDB.buscarContatos("").size)

    }

    @Test
    fun deve_RetornarUmaListaVazia_AposLimparADatabase(){

        helperDB.limpaDatabase()
        assertEquals(0, helperDB.buscarContatos("").size)

    }

    @After
    fun tearDown(){
        helperDB.limpaDatabase()
        println("Limpei a database")
    }

}