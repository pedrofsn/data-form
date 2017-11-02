package br.redcode.dataform.lib.ui

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.interfaces.CallbackImagem
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.Pergunta

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIAgregadorPerguntas(val context: Context, val perguntas: ArrayList<Pergunta>, val callbackImagem: CallbackImagem) {

    private val perguntasUI = ArrayList<UIPerguntaGeneric<Pergunta>>()

    fun gerarPerguntasUI() {
        if (perguntas.isNotEmpty()) {
            perguntasUI.clear()

            for (pergunta in perguntas) {
                var uiPergunta: UIPerguntaGeneric<Pergunta>? = null
                when {
                    pergunta.isPerguntaTextual() -> {
                        uiPergunta = UIPerguntaTextual(context, pergunta)
                    }
                    pergunta.isPerguntaObjetiva() -> {
                        uiPergunta = UIPerguntaObjetiva(context, pergunta)
                    }
                    pergunta.isPerguntaMultiplaEscolha() -> {
                        uiPergunta = UIPerguntaMultiplaEscolha(context, pergunta)
                    }
                    pergunta.isPerguntaImagem() -> {
                        uiPergunta = UIPerguntaImagem(context, pergunta, callbackImagem)
                    }
                }

                uiPergunta?.let { perguntasUI.add(it) }
            }
        }
    }

    fun getView(): View {
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.VERTICAL

        gerarPerguntasUI()
        for (ui in perguntasUI) {
            linearLayout.addView(ui.inicializar())
        }

        return linearLayout
    }

    fun isPerguntasPreenchidasCorretamente(): Boolean {
        val preenchidasCorretamente = perguntasUI.filter { (it as Perguntavel).isPreenchidoCorretamente() }
        return preenchidasCorretamente.size == perguntas.size
    }

    fun obterRespostas() {
        for (ui in perguntasUI) {
            (ui as Perguntavel).getResposta()
        }
    }

}