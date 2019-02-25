package br.redcode.sample.extensions

import br.redcode.sample.domain.BaseViewModel

/*
    CREATED BY @PEDROFSN
*/



inline fun BaseViewModel.showProgressbar(crossinline function: () -> Unit) {
    showProgressbar()
    function.invoke()
}