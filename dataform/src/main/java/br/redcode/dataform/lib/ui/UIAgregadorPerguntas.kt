package br.redcode.dataform.lib.ui

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import br.redcode.dataform.lib.domain.HandlerCapturaImagem
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.FormularioDePerguntas

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIAgregadorPerguntas(val context: Context, val formularioDePerguntas: FormularioDePerguntas, val handlerCapturaImagem: HandlerCapturaImagem) {

    private val perguntasUI = ArrayList<UIPerguntaGeneric>()

    fun gerarPerguntasUI() {
        if (formularioDePerguntas.perguntas.isNotEmpty()) {
            perguntasUI.clear()

            val configuracoes = formularioDePerguntas.configuracoes

            for (pergunta in formularioDePerguntas.perguntas) {
                var uiPergunta: UIPerguntaGeneric? = null
                when {
                    pergunta.isPerguntaTextual() -> {
                        uiPergunta = UIPerguntaTextual(context, pergunta, configuracoes)
                    }
                    pergunta.isPerguntaObjetivaLista() -> {
                        uiPergunta = UIPerguntaObjetivaLista(context, pergunta, configuracoes)
                    }
                    pergunta.isPerguntaObjetivaSpinner() -> {
                        uiPergunta = UIPerguntaObjetivaSpinner(context, pergunta, configuracoes)
                    }
                    pergunta.isPerguntaMultiplaEscolha() -> {
                        uiPergunta = UIPerguntaMultiplaEscolha(context, pergunta, configuracoes)
                    }
                    pergunta.isPerguntaImagemCameraOuGaleria() -> {
                        uiPergunta = UIPerguntaImagem(context, pergunta, configuracoes, handlerCapturaImagem, UIPerguntaImagem.Tipo.CAMERA_OU_GALERIA)
                    }
                    pergunta.isPerguntaImagemSomenteCamera() -> {
                        uiPergunta = UIPerguntaImagem(context, pergunta, configuracoes, handlerCapturaImagem, UIPerguntaImagem.Tipo.CAMERA)
                    }
                    pergunta.isPerguntaImagemSomenteGaleria() -> {
                        uiPergunta = UIPerguntaImagem(context, pergunta, configuracoes, handlerCapturaImagem, UIPerguntaImagem.Tipo.GALERIA)
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
        var quantidadePerguntasPreenchidasCorretamente = 0

        for (ui in perguntasUI) {
            val isPreenchidoCorretamente = ui.isPreenchidoCorretamente()
            ui.exibirMensagemErroPreenchimento(isPreenchidoCorretamente)
            quantidadePerguntasPreenchidasCorretamente += if (isPreenchidoCorretamente) 1 else 0
        }

        return quantidadePerguntasPreenchidasCorretamente == formularioDePerguntas.perguntas.size
    }

    fun obterRespostas() {
        for (ui in perguntasUI) {
            (ui as Perguntavel).getResposta().idPergunta = ui.pergunta.id
        }
    }

}