package br.redcode.dataform.lib.model

import br.redcode.dataform.lib.utils.Constantes
import java.io.Serializable

/**
 * Created by pedrofsn on 31/10/2017.
 */
data class Pergunta(
        val id: Int,
        val descricao: String,
        val formato: String,
        var resposta: Resposta? = Resposta(),
        val informacao: String? = Constantes.STRING_VAZIA,
        val obrigatoria: Boolean = true,

        var limite: Limite? = null,
        var alternativas: ArrayList<Alternativa>? = null,
        var textoInformativo: String? = null
) : Serializable {

    fun getDescricaoComObrigatoriedade(): String {
        return if (obrigatoria) descricao + " *" else descricao
    }

    fun getLimiteMaximo(): Int {
        return limite?.maximo ?: 1
    }

    fun getLimiteMinimo(): Int {
        return limite?.minimo ?: 0
    }

    fun isPerguntaTextoInformativo(): Boolean {
        return Constantes.TIPO_PERGUNTA_TEXTO_INFORMATIVO == formato
    }

    fun isPerguntaTextual(): Boolean {
        return Constantes.TIPO_PERGUNTA_TEXTUAL == formato
    }

    fun isPerguntaObjetivaLista(): Boolean {
        return Constantes.TIPO_PERGUNTA_OBJETIVA_LISTA == formato
    }

    fun isPerguntaListaItemRemovivel(): Boolean {
        return Constantes.TIPO_PERGUNTA_LISTA_ITEM_REMOVIVEL == formato
    }

    fun isPerguntaObjetivaSpinner(): Boolean {
        return Constantes.TIPO_PERGUNTA_OBJETIVA_SPINNER == formato
    }

    fun isPerguntaPercentual(): Boolean {
        return Constantes.TIPO_PERGUNTA_PERCENTUAL == formato
    }

    fun isPerguntaMultiplaEscolha(): Boolean {
        return validarLimites(Constantes.TIPO_PERGUNTA_MULTIPLA_ESCOLHA == formato)
    }

    fun isPerguntaImagemSomenteCamera(): Boolean {
        return validarLimites(Constantes.TIPO_PERGUNTA_IMAGEM_SOMENTE_CAMERA == formato)
    }

    fun isPerguntaImagemSomenteGaleria(): Boolean {
        return validarLimites(Constantes.TIPO_PERGUNTA_IMAGEM_SOMENTE_GALERIA == formato)
    }

    fun isPerguntaImagemCameraOuGaleria(): Boolean {
        return validarLimites(Constantes.TIPO_PERGUNTA_IMAGEM_CAMERA_OU_GALERIA == formato)
    }

    private fun validarLimites(resultado: Boolean): Boolean {
        if (resultado) quebrarAppSeEstiverSemLimite()
        return resultado
    }

    private fun quebrarAppSeEstiverSemLimite() {
        if (limite == null) {
            throw RuntimeException("Falta especificar os limites no JSON da pergunta ${id}")
        }
    }
}