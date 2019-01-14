package br.redcode.dataform.lib.ui

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.domain.handlers.HandlerCapturaImagem
import br.redcode.dataform.lib.domain.handlers.HandlerInputPopup
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.FormularioDePerguntas

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIAgregadorPerguntas(val context: Context, val formularioDePerguntas: FormularioDePerguntas, val handlerCapturaImagem: HandlerCapturaImagem, val handlerInputPopup: HandlerInputPopup) {

    companion object {
        const val DEFAULT_COLOR = "#ffffff"
    }

    private val perguntasUI = ArrayList<UIPerguntaGeneric>()

    fun gerarPerguntasUI() {
        if (formularioDePerguntas.perguntas.isNotEmpty()) {
            perguntasUI.clear()

            val configuracoes = formularioDePerguntas.configuracoes

            for (pergunta in formularioDePerguntas.perguntas) {
                val uiPergunta: UIPerguntaGeneric? = when {
                    pergunta.isPerguntaTextoInformativo() -> UIPerguntaTextoInformativo(context, pergunta, configuracoes)
                    pergunta.isPerguntaTextual() -> UIPerguntaTextual(context, pergunta, configuracoes)
                    pergunta.isPerguntaObjetivaLista() -> UIPerguntaObjetivaLista(context, pergunta, configuracoes)
                    pergunta.isPerguntaListaItemRemovivel() -> UIPerguntaListaItemRemovivel(context, pergunta, configuracoes, handlerInputPopup)
                    pergunta.isPerguntaObjetivaSpinner() -> UIPerguntaObjetivaSpinner(context, pergunta, configuracoes)
                    pergunta.isPerguntaMultiplaEscolha() -> UIPerguntaMultiplaEscolha(context, pergunta, configuracoes)
                    pergunta.isPerguntaImagemCameraOuGaleria() -> UIPerguntaImagem(context, pergunta, configuracoes, handlerCapturaImagem, UIPerguntaImagem.Tipo.CAMERA_OU_GALERIA)
                    pergunta.isPerguntaImagemSomenteCamera() -> UIPerguntaImagem(context, pergunta, configuracoes, handlerCapturaImagem, UIPerguntaImagem.Tipo.CAMERA)
                    pergunta.isPerguntaImagemSomenteGaleria() -> UIPerguntaImagem(context, pergunta, configuracoes, handlerCapturaImagem, UIPerguntaImagem.Tipo.GALERIA)
                    pergunta.isPerguntaPercentual() -> UIPerguntaPercentual(context, pergunta, configuracoes)
                    else -> null
                }

                uiPergunta?.let { perguntasUI.add(it) }
            }
        }
    }

    fun getView(): View {
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.VERTICAL

        // Alterar cor de fundo do formulário
        val hexColor = formularioDePerguntas.configuracoes.corBackgroundFormulario ?: DEFAULT_COLOR
        val color = Color.parseColor(hexColor)
        linearLayout.setBackgroundColor(color)

        gerarPerguntasUI()
        for (ui in perguntasUI) {
            linearLayout.addView(ui.inicializar())
        }

        return linearLayout
    }

    fun isPerguntasPreenchidasCorretamente(): Boolean {
        var quantidadePerguntasPreenchidasCorretamente = 0

        for (ui in perguntasUI) {
            val obrigatoria = ui.isRequired()
            val isPreenchidoCorretamente = if (obrigatoria) ui.isFilledCorrect() else true

            ui.showMessageForErrorFill(isPreenchidoCorretamente)
            quantidadePerguntasPreenchidasCorretamente += if (isPreenchidoCorretamente) 1 else 0
        }

        return quantidadePerguntasPreenchidasCorretamente == formularioDePerguntas.perguntas.size
    }

    fun obterRespostas() {
        for (ui in perguntasUI) {
            (ui as Questionable).getAnswer().idPergunta = ui.pergunta.id
        }
    }

}