package br.redcode.sample.domain

import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/*
    CREATED BY @PEDROFSN
*/

open class BaseViewModel : AbstractBaseViewModel(), CoroutineScope {

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = io()

    fun io() = job + Dispatchers.IO
    fun main() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}