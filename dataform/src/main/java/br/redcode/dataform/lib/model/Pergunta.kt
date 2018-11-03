package br.redcode.dataform.lib.model

import br.redcode.dataform.lib.utils.Constantes
import br.redcode.dataform.lib.utils.Constantes.TIPO_PERGUNTA_IMAGEM_CAMERA_OU_GALERIA
import br.redcode.dataform.lib.utils.Constantes.TIPO_PERGUNTA_IMAGEM_SOMENTE_CAMERA
import br.redcode.dataform.lib.utils.Constantes.TIPO_PERGUNTA_IMAGEM_SOMENTE_GALERIA
import br.redcode.dataform.lib.utils.Constantes.TIPO_PERGUNTA_LISTA_ITEM_REMOVIVEL
import br.redcode.dataform.lib.utils.Constantes.TIPO_PERGUNTA_MULTIPLA_ESCOLHA
import br.redcode.dataform.lib.utils.Constantes.TIPO_PERGUNTA_OBJETIVA_LISTA
import br.redcode.dataform.lib.utils.Constantes.TIPO_PERGUNTA_OBJETIVA_SPINNER
import br.redcode.dataform.lib.utils.Constantes.TIPO_PERGUNTA_PERCENTUAL
import br.redcode.dataform.lib.utils.Constantes.TIPO_PERGUNTA_TEXTO_INFORMATIVO
import br.redcode.dataform.lib.utils.Constantes.TIPO_PERGUNTA_TEXTUAL
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
        var textoInformativo: String? = null,
        var configuracaoPergunta: HashMap<String, Boolean>? = null
) : Serializable {

    fun getDescricaoComObrigatoriedade() = if (obrigatoria) "$descricao *" else descricao
    fun getLimiteMaximo() = limite?.maximo ?: 1
    fun getLimiteMinimo(): Int = limite?.minimo ?: 0
    fun isPerguntaTextoInformativo() = TIPO_PERGUNTA_TEXTO_INFORMATIVO == formato
    fun isPerguntaTextual() = TIPO_PERGUNTA_TEXTUAL == formato
    fun isPerguntaObjetivaLista() = TIPO_PERGUNTA_OBJETIVA_LISTA == formato
    fun isPerguntaListaItemRemovivel() = TIPO_PERGUNTA_LISTA_ITEM_REMOVIVEL == formato
    fun isPerguntaObjetivaSpinner() = TIPO_PERGUNTA_OBJETIVA_SPINNER == formato
    fun isPerguntaPercentual() = TIPO_PERGUNTA_PERCENTUAL == formato
    fun isPerguntaMultiplaEscolha() = validarLimites(TIPO_PERGUNTA_MULTIPLA_ESCOLHA == formato)
    fun isPerguntaImagemSomenteCamera() = validarLimites(TIPO_PERGUNTA_IMAGEM_SOMENTE_CAMERA == formato)
    fun isPerguntaImagemSomenteGaleria() = validarLimites(TIPO_PERGUNTA_IMAGEM_SOMENTE_GALERIA == formato)
    fun isPerguntaImagemCameraOuGaleria() = validarLimites(TIPO_PERGUNTA_IMAGEM_CAMERA_OU_GALERIA == formato)

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