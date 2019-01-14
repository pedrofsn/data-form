package br.redcode.dataform.lib.ui

import android.content.Context
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta
import br.redcode.dataform.lib.utils.Constantes

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaPercentual(contextActivity: Context, pergunta: Pergunta, configuracao: ConfiguracaoFormulario) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_percentual, pergunta, configuracao), Questionable {

    private lateinit var seekBar: SeekBar
    private lateinit var textView: TextView

    override fun initView(view: View) {
        super.initView(view)
        seekBar = view.findViewById<SeekBar>(R.id.seekBar)
        textView = view.findViewById<TextView>(R.id.textView)
    }

    override fun populateView() {
        super.populateView()
        seekBar.setTag("ui_pergunta_" + pergunta.id + "_seekbar")
        textView.setTag("ui_pergunta_" + pergunta.id + "_textview")

        atualizarTexto(0)
        pergunta.resposta?.resposta?.let {

            try {
                val percentual = it.toInt()
                atualizarTexto(percentual)
                seekBar.setProgress(percentual)
            } catch (e: Exception) {

            }

        }

        seekBar.isEnabled = configuracao.editavel

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                atualizarTexto(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
    }

    private fun atualizarTexto(progress: Int) {
        textView.text = progress.toString().plus("%")
    }

    override fun getAnswer(): Resposta {
        val resposta = Resposta(resposta = seekBar.progress.toString())
        if (pergunta.resposta != null) resposta.tag = pergunta.resposta?.tag
        pergunta.resposta = resposta
        return resposta
    }

    override fun isFilledCorrect(): Boolean {
        return true
    }

    override fun getMessageErrorFill(): String {
        return Constantes.STRING_VAZIA
    }

}