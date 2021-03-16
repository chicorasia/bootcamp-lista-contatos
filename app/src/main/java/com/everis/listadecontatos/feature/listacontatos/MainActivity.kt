package com.everis.listadecontatos.feature.listacontatos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.everis.listadecontatos.R
import com.everis.listadecontatos.application.ContatosApplication
import com.everis.listadecontatos.bases.BaseActivity
import com.everis.listadecontatos.feature.contato.ContatoActivity
import com.everis.listadecontatos.feature.listacontatos.adapter.ContatoAdapter
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolBar(toolBar, "Lista de contatos",false)
        setupListView()
        setupOnClicks()
    }

    private fun setupOnClicks(){
        fab.setOnClickListener { onClickAdd() }
        ivBuscar.setOnClickListener { onClickBuscar() }
    }

    private fun setupListView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        onClickBuscar()
    }

    private fun onClickAdd(){
        Intent(this,ContatoActivity::class.java).let {
            startActivity(it)
        }
    }

    private fun onClickItemRecyclerView(index: Int){
        Intent(this,ContatoActivity::class.java).let { intent: Intent ->
            intent.putExtra("index", index)
            startActivity(intent)
        }

    }

    private fun onClickBuscar(){

        etBuscar.text.toString().let {
            var listaFiltrada: List<ContatosVO> = try {
                ContatosApplication
                        .instance.helperDB?.buscarContatos(it) ?: mutableListOf()
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
                return
            }

            recyclerView.apply {
                adapter = ContatoAdapter(context, listaFiltrada) { it ->
                    onClickItemRecyclerView(it)} }
            Toast.makeText(this,"Buscando por $it",Toast.LENGTH_SHORT).show()
        }

    }

}
