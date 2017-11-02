package br.redcode.sample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.redcode.dataform.lib.domain.ActivityCapturarImagem
import br.redcode.dataform.lib.interfaces.CallbackImagem
import br.redcode.dataform.lib.model.Imagem
import br.redcode.dataform.lib.ui.UIAgregadorPerguntas
import br.redcode.dataform.lib.ui.UIPerguntaImagem
import br.redcode.dataform.lib.utils.Constantes
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

class ActivityMain : ActivityCapturarImagem() {

    private lateinit var agregador: UIAgregadorPerguntas
    private lateinit var minhasPerguntas: MinhasPerguntas

    private val callbackImagem = object : CallbackImagem {
        override var uiPerguntaImagemTemp: UIPerguntaImagem? = null

        override fun capturarImagem(uiPerguntaImagem: UIPerguntaImagem) {
            uiPerguntaImagemTemp = uiPerguntaImagem
            capturarImagemEasyImage()
        }

        override fun onImagensSelecionadas(imagesFiles: List<File>) {
            uiPerguntaImagemTemp?.let {
                imagesFiles.forEach(fun(file: File) { it.adicionarImagem(Imagem(file.absolutePath)) })
            }
        }
    }

    private fun capturarImagemEasyImage() {
        EasyImage.openChooserWithGallery(this, getString(R.string.selecione), Constantes.EASY_IMAGE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializarListeners()
        gerarListaPerguntas()
        afterOnCreate()
    }

    private fun inicializarListeners() {
        button.setOnClickListener {
            Log.e(br.redcode.sample.Constantes.TAG, "Preenchido corretamente: " + agregador.isPerguntasPreenchidasCorretamente())

            agregador.obterRespostas()
            val respostas = agregador.perguntas.toString()
            Log.e(br.redcode.sample.Constantes.TAG, respostas)
            Toast.makeText(this, respostas, Toast.LENGTH_LONG).show()
        }
    }

    fun gerarListaPerguntas() {
        val reader = JSONReader(this)
        val json = reader.getStringFromJson(R.raw.perguntas)

        val gson = Gson()
        minhasPerguntas = gson.fromJson<MinhasPerguntas>(json, MinhasPerguntas::class.java)
    }

    private fun afterOnCreate() {
        agregador = UIAgregadorPerguntas(this, minhasPerguntas.perguntas, callbackImagem)
        linearLayout.addView(agregador.getView())
    }

    override fun onImagesPicked(imagesFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
        super.onImagesPicked(imagesFiles, source, type)
        callbackImagem.onImagensSelecionadas(imagesFiles)
    }
}
