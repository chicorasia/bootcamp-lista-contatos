package com.everis.listadecontatos.feature.contato

import android.os.Bundle
import android.view.View
import com.everis.listadecontatos.R
import com.everis.listadecontatos.application.ContatosApplication
import com.everis.listadecontatos.bases.BaseActivity
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import kotlinx.android.synthetic.main.activity_contato.*
import kotlinx.android.synthetic.main.activity_contato.toolBar

class ContatoActivity : BaseActivity() {

    private var contatoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)
        setupToolBar(toolBar, "Contato",true)
        setupContato()
        btnSalvarConato.setOnClickListener { onClickSalvarContato() }
    }

    private fun setupContato(){
        contatoId = intent.getIntExtra("index",-1)
        if (contatoId == -1){
            btnExcluirContato.visibility = View.GONE
            return
        }
        val lista = ContatosApplication.instance.helperDB?.buscarContatos(
                "$contatoId", true) ?: return
        var contato = lista.getOrNull(0) ?: return
        etNome.setText(contato.nome)
        etTelefone.setText(contato.telefone)
    }

    private fun onClickSalvarContato(){
        val nome = etNome.text.toString()
        val telefone = etTelefone.text.toString()
        val contato = ContatosVO(
            0,
            nome,
            telefone
        )
        if(contatoId == -1) {
            ContatosApplication.instance.helperDB?.salvarContato(contato)
        }else{
//            ContatoSingleton.lista.set(index,contato)
        }
        finish()
    }

    fun onClickExcluirContato(view: View) {
        if(contatoId > -1){
            ContatosApplication.instance.helperDB?.deletarContato(contatoId)
            finish()
        }
    }
}
