package com.example.currencyconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.R
import com.example.currencyconverter.core.extensions.createDialog
import com.example.currencyconverter.core.extensions.createProgressDialog
import com.example.currencyconverter.data.model.Coin
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
        bindingListeners()

        viewModel.getExchangeValue("USD-BRL")
        viewModel.state.observe(this) {
            when(it) {
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    Log.e("TAG", "onCreate: ${it.value}")
                }
            }
        }
    }

    private fun bindAdapters() {
        val list = Coin.values()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        binding.tvFrom.setAdapter(adapter)
        binding.tvFrom.setText(Coin.BRL.name, false)
        binding.tvTo.setAdapter(adapter)
        binding.tvTo.setText(Coin.USD.name, false)
    }

    private fun bindingListeners() {
        binding.tilValue.editText?.doAfterTextChanged {
            binding.btnConvert.isEnabled = it != null && it.toString().isNotEmpty()
        }

        binding.btnConvert.setOnClickListener {

        }
    }
}