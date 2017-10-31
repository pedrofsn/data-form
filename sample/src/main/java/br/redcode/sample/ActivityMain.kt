package br.redcode.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.ui.UIAgregadorPerguntas
import br.redcode.dataform.lib.utils.Constantes
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AppCompatActivity() {

    private lateinit var agregador: UIAgregadorPerguntas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pergunta1 = Pergunta(1, "Qual é o seu nome?", Constantes.TIPO_PERGUNTA_TEXTUAL)
        val pergunta2 = Pergunta(2, "Qual é o seu sobrenome?", Constantes.TIPO_PERGUNTA_TEXTUAL)

        val listaPerguntas = ArrayList<Pergunta>()
        listaPerguntas.add(pergunta1)
        listaPerguntas.add(pergunta2)

        agregador = UIAgregadorPerguntas(this, listaPerguntas)
        linearLayout.addView(agregador.getView())

        button.setOnClickListener {
            Log.e("teste", "Preenchido corretamente: " + agregador.isPerguntasPreenchidasCorretamente())

            agregador.obterRespostas()
            Log.e("teste", agregador.perguntas.toString())
        }
    }
}
