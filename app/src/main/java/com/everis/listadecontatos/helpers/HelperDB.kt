package com.everis.listadecontatos.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO

class HelperDB(
        context: Context
) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_ATUAL) {

    companion object {

        private val NOME_BANCO = "contato_db"
        private val VERSAO_ATUAL = 1

    }

    val TABLE_NAME = "contatos"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    val COLUMN_ID = "id"
    val COLUMN_NOME = "nome"
    val COLUMN_TELEFONE = "telefone"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_ID INTEGER NOT NULL," +
            "$COLUMN_NOME TEXT NOT NULL," +
            "$COLUMN_TELEFONE TEXT NOT NULL," +
            "PRIMARY KEY($COLUMN_ID AUTOINCREMENT)" +
            ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(newVersion != oldVersion){
            db?.execSQL(DROP_TABLE)
            onCreate(db)
        }

    }

    fun buscarContatos(busca: String) : MutableList<ContatosVO>{
        val db = readableDatabase ?: return mutableListOf()
        var lista = mutableListOf<ContatosVO>()
        val sql = "SELECT * FROM $TABLE_NAME"
        var cursor = db.rawQuery(sql, null) ?: return mutableListOf()
        while(cursor.moveToNext()) {
            var contatoRecuperado = ContatosVO(
                    id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    nome = cursor.getString(cursor.getColumnIndex(COLUMN_NOME)),
                    telefone = cursor.getString(cursor.getColumnIndex(COLUMN_TELEFONE))
            )
            lista.add(contatoRecuperado)
        }
        return lista


    }
}