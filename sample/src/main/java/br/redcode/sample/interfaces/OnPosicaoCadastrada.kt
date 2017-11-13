package br.redcode.sample.interfaces

import java.io.Serializable

interface OnPosicaoCadastrada : Serializable {
    fun onPosicaoCadastrada(latitude: String, longitude: String)
}