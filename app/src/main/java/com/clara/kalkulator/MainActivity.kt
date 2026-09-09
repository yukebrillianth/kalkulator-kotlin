package com.clara.kalkulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clara.kalkulator.ui.theme.ColorSparkBlue
import com.clara.kalkulator.ui.theme.KalkulatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalkulatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    innerPadding -> LayarKalkulator(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LayarKalkulator(modifier: Modifier = Modifier) {

    var tampilan by remember { mutableStateOf("0") }
    var angkaPertama by remember { mutableStateOf(0.0) }
    var operator by remember { mutableStateOf("") }
    var mulaiAngkaBaru by remember { mutableStateOf(true) }

    fun tekanAngka(angka: String) {
        tampilan = if (mulaiAngkaBaru || tampilan == "0") {
            angka
        } else {
            tampilan + angka
        }
        mulaiAngkaBaru = false
    }

    fun tekanOperator(op: String) {
        angkaPertama = tampilan.toDoubleOrNull() ?: 0.0
        operator = op
        mulaiAngkaBaru = true
    }

    fun tekanSamaDengan() {
        val angkaKedua = tampilan.toDoubleOrNull() ?: 0.0

        val hasil = when (operator) {
            "+" -> angkaPertama + angkaKedua
            "-" -> angkaPertama - angkaKedua
            "x" -> angkaPertama * angkaKedua
            ":" -> if (angkaKedua == 0.0) Double.NaN else angkaPertama / angkaKedua
            else -> angkaKedua
        }

        tampilan = formatAngka(hasil)
        operator = ""
        mulaiAngkaBaru = true
    }

    fun tekanClear() {
        tampilan = "0"
        angkaPertama = 0.0
        operator = ""
        mulaiAngkaBaru = true
    }

//    Container
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Kalkulator OK",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            textDecoration = TextDecoration.Underline
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = tampilan,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                color = ColorSparkBlue
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Tombol("7", Modifier.weight(1f)) { tekanAngka("7") }
            Tombol("8", Modifier.weight(1f)) { tekanAngka("8") }
            Tombol("9", Modifier.weight(1f)) { tekanAngka("9") }
            Tombol(":", Modifier.weight(1f)) { tekanOperator(":") }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Tombol("4", Modifier.weight(1f)) { tekanAngka("4") }
            Tombol("5", Modifier.weight(1f)) { tekanAngka("5") }
            Tombol("6", Modifier.weight(1f)) { tekanAngka("6") }
            Tombol("x", Modifier.weight(1f)) { tekanOperator("x") }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Tombol("1", Modifier.weight(1f)) { tekanAngka("1") }
            Tombol("2", Modifier.weight(1f)) { tekanAngka("2") }
            Tombol("3", Modifier.weight(1f)) { tekanAngka("3") }
            Tombol("-", Modifier.weight(1f)) { tekanOperator("-") }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Tombol("C", Modifier.weight(1f)) { tekanClear() }
            Tombol("0", Modifier.weight(1f)) { tekanAngka("0") }
            Tombol("=", Modifier.weight(1f)) { tekanSamaDengan() }
            Tombol("+", Modifier.weight(1f)) { tekanOperator("+") }
        }
    }
}

@Composable
fun Tombol(teks: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(vertical = 4.dp)
            .height(64.dp)
    ) {
        Text(text = teks, fontSize = 20.sp)
    }
}

fun formatAngka(nilai: Double): String {
    if (nilai.isNaN()) {
        return "Error"
    }
    return if (nilai == nilai.toLong().toDouble()) {
        nilai.toLong().toString()
    } else {
        nilai.toString()
    }
}

@Preview(showBackground = true)
@Composable
fun LayarKalkulatorPreview() {
    KalkulatorTheme {
        LayarKalkulator()
    }
}
