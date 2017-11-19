package br.redcode.sample.model

import br.redcode.dataform.lib.interfaces.DuasLinhas
import java.util.*

/**
 * Created by pedrofsn on 13/11/2017.
 */
open class CustomObject(val latitude: String, val longitude: String, val dataAtual: Date = Date())
    : DuasLinhas(latitude, longitude, dataAtual)