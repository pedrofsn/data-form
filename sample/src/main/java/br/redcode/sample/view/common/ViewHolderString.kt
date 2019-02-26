package br.redcode.sample.view.common

import br.com.redcode.base.mvvm.domain.adapter.BaseViewHolderMVVM
import br.redcode.sample.databinding.ViewholderStringBinding

/**
 * Created by pedrofsn on 26/02/2019.
 */
open class ViewHolderString(binding: ViewholderStringBinding) : BaseViewHolderMVVM<String, ViewholderStringBinding>(binding) {

    override fun bind(data: String) {
        binding.data = data
    }

}