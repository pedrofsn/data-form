package br.redcode.sample.view.common

import br.com.redcode.base.mvvm.domain.adapter.BaseAdapterMVVM
import br.redcode.sample.R
import br.redcode.sample.databinding.ViewholderStringBinding

class AdapterString(override var click: ((String, Int) -> Unit)?) : BaseAdapterMVVM<String, ViewholderStringBinding>() {

    override val layout: Int = R.layout.viewholder_string
    override fun getViewHolder(binding: ViewholderStringBinding) = ViewHolderString(binding)

}