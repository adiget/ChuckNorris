package com.annada.android.sample.chucknorris.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.annada.android.sample.chucknorris.R
import com.annada.android.sample.chucknorris.databinding.QuoteFragmentBinding
import com.google.android.material.snackbar.Snackbar

class QuoteFragment: Fragment() {

    companion object {
        fun newInstance() = QuoteFragment()
    }

    private lateinit var viewModel: QuoteListViewModel
    private lateinit var binding: QuoteFragmentBinding
    private var errorSnackbar: Snackbar? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity))
            .get(QuoteListViewModel::class.java)

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        binding = DataBindingUtil.inflate(inflater, R.layout.quote_fragment, container, false)

        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.quotesList.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(
            binding.quotesList.context,
            linearLayoutManager.orientation
        )
        binding.quotesList.addItemDecoration(dividerItemDecoration)

        binding.viewModel = viewModel


        return binding.root
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

}
