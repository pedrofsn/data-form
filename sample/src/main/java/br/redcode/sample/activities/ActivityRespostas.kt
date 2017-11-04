package br.redcode.sample.activities

import android.os.Bundle
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.payloads.RespostaPayload
import br.redcode.sample.R
import br.redcode.sample.domain.ActivityGeneric
import br.redcode.sample.model.MinhasPerguntas
import br.redcode.sample.utils.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_respostas.*

/**
 * Created by pedrofsn on 03/11/2017.
 */
class ActivityRespostas(override var ativarBotaoVoltar: Boolean = true) : ActivityGeneric() {

    private var perguntas: MinhasPerguntas? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_respostas)

        perguntas = intent.getSerializableExtra("minhasPerguntas") as MinhasPerguntas

        atualizarRespostas()
        button.setOnClickListener { atualizarRespostas() }
    }

    private fun atualizarRespostas() {
        val dtos = ArrayList<RespostaPayload>()

        perguntas?.let {

            val respostas = it.perguntas.filter { pergunta: Pergunta -> pergunta.resposta?.hasResposta() == true }.map { it.resposta }

            for (r in respostas) {
                r?.let {
                    val (idPergunta, resposta, alternativa, alternativas, imagens) = it
                    var dto: RespostaPayload? = null

                    if (idPergunta != null) {

                        if (resposta != null) {
                            dto = RespostaPayload(idPergunta, resposta)
                        }

                        if (alternativa != null) {
                            dto = RespostaPayload(idPergunta, alternativa.toDTO())
                        }

                        if (alternativas != null) {
                            val alternativasTratadas = alternativas.map { it.toDTO() }
                            dto = RespostaPayload(idPergunta, alternativasTratadas)
                        }

                        if (imagens != null) {
                            dto = RespostaPayload(idPergunta, imagens)
                        }

                        dto?.let { dtos.add(it) }
                    }
                }
            }

            val gson = Gson()
            val json = gson.toJson(dtos)

            textView.text = json
            Utils.log(json)
        }
    }

}