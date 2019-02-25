package br.redcode.sample.domain

import androidx.lifecycle.MutableLiveData
import br.com.redcode.base.utils.Constants

abstract class BaseViewModelWithLiveData<Model> : BaseViewModel() {

    val liveData = MutableLiveData<Model>()
    var id: Long = Constants.INVALID_VALUE.toLong()

    abstract fun load()

}