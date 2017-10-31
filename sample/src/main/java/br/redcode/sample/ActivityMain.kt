package br.redcode.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.ui.UIPergunta
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pergunta = Pergunta(15, "Teste", 0)

        val uiPergunta = UIPergunta(this, pergunta)
        val view = uiPergunta.inicializar()

        linearLayout.addView(view)

        button.setOnClickListener {
            Log.e("teste", "Preenchido corretamente: " + uiPergunta.isPreenchidoCorretamente())
            Log.e("teste", "Resposta: " + uiPergunta.getResposta())
        }
    }
}
