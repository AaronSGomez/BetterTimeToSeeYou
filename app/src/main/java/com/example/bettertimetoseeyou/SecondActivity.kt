package com.example.bettertimetoseeyou

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.selects.SelectInstance

class SecondActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val tvInfo = findViewById<TextView>(R.id.tvInfo)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        val textoRecibido = intent.getStringExtra(MainActivity.TEXTO1) ?:""
        val numeroRecibido= intent.getIntExtra(MainActivity.TEXTO2, Int.MIN_VALUE)

        val resumen = buildString {
            appendLine("Info recibida de la Main Activity")
            appendLine("---------------------------------")
            appendLine("texto recibido : $textoRecibido")
            if (numeroRecibido != Int.MIN_VALUE){
                appendLine("Numero Recibido : $numeroRecibido")
            }else{
                appendLine("No se ha recibido ningun numero")
            }

        }

        tvInfo.text = resumen

        btnVolver.setOnClickListener { finish() }
    }

}