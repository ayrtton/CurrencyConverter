package com.example.currencyconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import com.example.currencyconverter.core.extensions.*
import com.example.currencyconverter.data.model.Currency
import com.example.currencyconverter.databinding.ActivityMainBinding
import com.example.currencyconverter.presentation.di.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindAdapters()
        bindListeners()
        bindObserve()
    }

    private fun bindAdapters() {
        val list = Currency.values()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        binding.tvFrom.setAdapter(adapter)
        binding.tvTo.setAdapter(adapter)
        binding.tvFrom.setText(Currency.BRL.name, false)
        binding.tvTo.setText(Currency.USD.name, false)
    }

    private fun bindListeners() {
        binding.tilValue.editText?.doAfterTextChanged {
            binding.btnConvert.isEnabled = it != null && it.toString().isNotEmpty()
        }

        binding.btnConvert.setOnClickListener {
            it.hideSoftKeyboard()

            val search = "${binding.tilFrom.text}-${binding.tilTo.text}"
            viewModel.getExchangeValue(search)
        }
    }

    private fun bindObserve() {
        viewModel.state.observe(this) {
            when(it) {
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                }
                is MainViewModel.State.Success -> success(it)
            }
        }
    }

    private fun success(it: MainViewModel.State.Success) {
        dialog.dismiss()

        val selectedCurrency = binding.tilTo.text
        val currency = Currency.values().find { it.name == selectedCurrency } ?: Currency.BRL
        val result = it.exchange.bid * binding.tilValue.text.toDouble()

        binding.tvResult.text = result.formatCurrency(currency.locale)
    }
}