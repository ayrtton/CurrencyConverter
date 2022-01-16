package com.example.currencyconverter.data.model

import java.util.*

enum class Currency(val locale: Locale) {
    USD(Locale.US),
    BRL(Locale("pt", "BR")),
    CAD(Locale.CANADA),
    GBP(Locale.UK),
    ARS(Locale("es", "AR")),
    JPY(Locale.JAPAN),
    CNY(Locale.CHINA);

    companion object {
        fun getByName(name: String) = values().find { it.name == name } ?: BRL
    }
}