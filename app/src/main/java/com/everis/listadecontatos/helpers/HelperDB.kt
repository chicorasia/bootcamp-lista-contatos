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
        }
        onCreate(db)

    }

    fun buscarContatos(busca: String, ehBuscaPorId: Boolean = false) : List<ContatosVO>{
        val db = readableDatabase ?: return mutableListOf()
        var lista = mutableListOf<ContatosVO>()
        var sql: String? = null
        val args = if (ehBuscaPorId) { arrayOf(busca) } else { arrayOf("%$busca%") }

        if(ehBuscaPorId){
            sql = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        } else {
            sql = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_NOME LIKE ?"
        }

        val cursor = db.rawQuery(sql, args)
        if(cursor == null){
            db.close()
            return mutableListOf()
        }
        while(cursor.moveToNext()) {
            var contatoRecuperado = ContatosVO(
                    id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    nome = cursor.getString(cursor.getColumnIndex(COLUMN_NOME)),
                    telefone = cursor.getString(cursor.getColumnIndex(COLUMN_TELEFONE))
            )
            lista.add(contatoRecuperado)
        }
        db.close()
        return lista
    }

    fun salvarContato(contato: ContatosVO) {
        val db = writableDatabase ?: return
        val sql = "INSERT INTO $TABLE_NAME ($COLUMN_NOME, $COLUMN_TELEFONE) " +
                "VALUES (?,?)"
        val array = arrayOf(contato.nome, contato.telefone)
        db.execSQL(sql, array)
        db.close()
    }

    fun deletarContato(id: Int) {
        val db = writableDatabase ?: return
        val sql = "DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val args = arrayOf("$id")
        db.execSQL(sql, args)
        db.close()
    }

}