package com.example.bettertimetoseeyou


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {
    //esta funcion devuelve un true o false , al ser una funcion simple(una sola linea) no hace falta llaves
    private fun esPar(n: Int) = n%2 == 0

    //vector de numeros para probar foreach
    private fun recorreNumeros() {
        var numeros = arrayOf(3,4,5,6,7)

        numeros.forEach { num ->
            if(esPar(num)) {
                println("$num es Par")
            }else{
                println("$num es Impar")
            }
        }
    }

    //funcion compleja (parametro entrada) : parametro retorno
    private fun sumaHasta(n: Int): Int{
        var total = 0 //sin tipo, el valor que le pases
        for(i in 1 .. n){
            total+=i
        }
        return total
    }

    private fun tipoNumero(n : Int): String{
        return when {
            n<0 -> "Negativo"
            n==0 -> "Cero"
            else -> "Positivo"   //en este caso la siguiente opcion seria n>0
        }
    }

    private fun procesaLista(lista: List<Int>, criterio: (Int)-> Boolean) : List<Int> =
        lista.filter(criterio)




    //funcion con for completo clasico
    private fun sumaTotalPares(n: Int): Int{
        var total=0
        var inicio = 0

        if(esPar(n)) {
            inicio == n
        }
        for(i in inicio until n step 2){

        }
        return total
    }

    companion object{
        const val TEXTO1 = "TEXTO1"
        const val TEXTO2 = "TEXTO2"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvTitulo = findViewById<TextView>(R.id.tvTitulo)
        val etEntrada = findViewById<EditText>(R.id.etEntrada)
        val btnProcesar = findViewById<Button>(R.id.btnProcesar)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)
        val btnIrSegunda =findViewById<Button>(R.id.btnIrSegunda)

        tvTitulo.text = "KOTLIN DEMO"

        btnIrSegunda.setOnClickListener{
            val textoEntrada =etEntrada.text.toString().trim()
            val numero = textoEntrada.toIntOrNull()
            val intent = Intent(this , SecondActivity ::class.java).apply {
                putExtra(TEXTO1, textoEntrada)
                if (numero!=null) putExtra(TEXTO2,numero)
            }
            startActivity(intent)
        }



        btnProcesar.setOnClickListener {
            val valor = etEntrada.text.toString().trim()

            if(valor.isEmpty()){
                etEntrada.error= "Escribe algo"
                tvResultado.text= ""
                return@setOnClickListener
            }

            val num = valor.toIntOrNull()

            val salida =
                if(num!=null){
                buildString {
                    appendLine("Entrada = $num")
                    appendLine( "Tipo numero = ${tipoNumero(num)}")
                    appendLine("Suma hasta = ${sumaHasta(num)}")
                    appendLine("Es par = ${esPar(num)}")

                }
                }else{
                buildString {
                    appendLine("Entrada = $valor")
                    appendLine("Longitud = ${valor.length}")
                }
                }

            tvResultado.text = salida

        }

    }
}