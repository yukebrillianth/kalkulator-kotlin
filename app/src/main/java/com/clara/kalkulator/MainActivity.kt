package com.clara.kalkulator

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var tvEkspresi: TextView
    private lateinit var tvHasil: TextView

    private var tampilan = "0"
    private var angkaPertama = 0.0
    private var operator = ""
    private var mulaiAngkaBaru = true
    private var ekspresi = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvEkspresi = findViewById(R.id.tvEkspresi)
        tvHasil = findViewById(R.id.tvHasil)
        updateTampilan()
    }

    // Satu handler untuk SEMUA tombol via android:onClick di XML
    fun onButtonClick(view: View) {
        val tag = view.tag?.toString() ?: return

        when (tag) {
            "0","1","2","3","4","5","6","7","8","9" -> tekanAngka(tag)
            "." -> tekanTitik()
            "+","-","x",":" -> tekanOperator(tag)
            "=" -> tekanSamaDengan()
            "C" -> tekanClear()
            "+/-" -> tekanUbahTanda()
            "%" -> tekanPersen()
        }
        updateTampilan()
    }

    private fun tekanAngka(angka: String) {
        tampilan = if (mulaiAngkaBaru || tampilan == "0") angka else tampilan + angka
        mulaiAngkaBaru = false
        updateEkspresiBerjalan()
    }

    private fun tekanTitik() {
        if (mulaiAngkaBaru) {
            tampilan = "0."
            mulaiAngkaBaru = false
        } else if (!tampilan.contains(".")) {
            tampilan += "."
        }
        updateEkspresiBerjalan()
    }

    private fun tekanOperator(op: String) {
        angkaPertama = tampilan.toDoubleOrNull() ?: 0.0
        operator = op
        mulaiAngkaBaru = true
        ekspresi = "${formatAngka(angkaPertama)} $operator"
    }

    private fun tekanSamaDengan() {
        val angkaKedua = tampilan.toDoubleOrNull() ?: 0.0
        val hasil = when (operator) {
            "+" -> angkaPertama + angkaKedua
            "-" -> angkaPertama - angkaKedua
            "x" -> angkaPertama * angkaKedua
            ":" -> if (angkaKedua == 0.0) Double.NaN else angkaPertama / angkaKedua
            else -> angkaKedua
        }
        ekspresi = if (operator.isNotEmpty())
            "${formatAngka(angkaPertama)} $operator ${formatAngka(angkaKedua)} =" else ""
        tampilan = formatAngka(hasil)
        operator = ""
        angkaPertama = hasil
        mulaiAngkaBaru = true
    }

    private fun tekanClear() {
        tampilan = "0"
        angkaPertama = 0.0
        operator = ""
        ekspresi = ""
        mulaiAngkaBaru = true
    }

    private fun tekanUbahTanda() {
        val nilai = tampilan.toDoubleOrNull() ?: 0.0
        tampilan = formatAngka(nilai * -1)
    }

    private fun tekanPersen() {
        val nilai = tampilan.toDoubleOrNull() ?: 0.0
        tampilan = formatAngka(nilai / 100)
    }

    private fun updateEkspresiBerjalan() {
        ekspresi = if (operator.isNotEmpty()) "${formatAngka(angkaPertama)} $operator" else ""
    }

    private fun updateTampilan() {
        tvHasil.text = tampilan
        tvEkspresi.text = ekspresi
    }

    private fun formatAngka(nilai: Double): String {
        if (nilai.isNaN()) return "Error"
        return if (nilai == nilai.toLong().toDouble()) {
            nilai.toLong().toString()
        } else {
            nilai.toString()
        }
    }
}