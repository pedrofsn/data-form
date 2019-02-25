package br.redcode.sample.domain

import androidx.databinding.ViewDataBinding
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.domain.activity.BaseActivityMVVM
import br.redcode.sample.BR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class ActivityMVVM<B : ViewDataBinding, VM : AbstractBaseViewModel> : BaseActivityMVVM<B, VM>(), CoroutineScope {

    override val idBRViewModel: Int = BR.viewModel

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = io()

    fun io() = job + Dispatchers.IO
    fun main() = job + Dispatchers.Main

}