package com.example.prova02

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.Locale

class Activity_Read : AppCompatActivity() {

    lateinit var pesquisa: EditText
    lateinit var bt_busca: Button
    lateinit var bt_tudo: Button

    fun limpa_tela() {
        pesquisa.text.clear()
    }

    fun mostrarMedia() {
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("Fazenda")

        collectionRef.get()
            .addOnSuccessListener { documents ->
                var soma = 0.0
                var contador = 0
                for (document in documents) {
                    val FazendaValor = document.getDouble("Valor")
                    if (FazendaValor != null) {
                        soma += FazendaValor
                        contador++
                    }
                }
                val media = if (contador > 0) soma / contador else 0.0
                Toast.makeText(this, "Média de valores: $media", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Não é possível calcular a média", Toast.LENGTH_LONG).show()
            }
    }

    fun mostrarTudo(){
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)
        val db = FirebaseFirestore.getInstance()
        val productsRef = db.collection("Fazenda")
        productsRef.get()
            .addOnSuccessListener { documents ->
                linearLayout.removeAllViews()
                if (documents.isEmpty) {
                    val noProductsTextView = TextView(this)
                    noProductsTextView.text = "Nenhuma Fazenda"
                    linearLayout.addView(noProductsTextView)
                } else {
                    for (document in documents) {
                        val FazendaCod = document.getString("CodFazenda")
                        val FazendaNome = document.getString("NomeFazenda")
                        val FazendaQuantidade = document.getLong("Quantidade")
                        val FazendaValor = document.getDouble("Valor")

                        val nome = TextView(this)
                        nome.text = "Nome: $FazendaNome"
                        linearLayout.addView(nome)

                        val cod = TextView(this)
                        cod.text = "Código: $FazendaCod"
                        linearLayout.addView(cod)

                        val qtt = TextView(this)
                        qtt.text = "Quantidade: $FazendaQuantidade"
                        linearLayout.addView(qtt)

                        val valor = TextView(this)
                        valor.text = "Valor: $FazendaValor\n\n"
                        linearLayout.addView(valor)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Não é possível mostrar !!", Toast.LENGTH_LONG).show()
            }
    }

    fun mostrarPesquisa() {
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("Fazenda")

        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)

        val buscar = pesquisa.text.toString()
        if (buscar.isNotEmpty()) {
            val query = if (buscar.toIntOrNull() != null) {
                collectionRef.whereEqualTo("CodFazenda", buscar)
            } else {
                collectionRef.whereEqualTo("NomeFazenda", buscar)
            }

            query.get()
                .addOnSuccessListener { documents ->
                    linearLayout.removeAllViews()

                    if (documents.isEmpty) {
                        val noProductsTextView = TextView(this)
                        noProductsTextView.text = "Nenhuma Fazenda encontrada"
                        linearLayout.addView(noProductsTextView)
                    } else {
                        for (document in documents) {
                            val FazendaCod = document.getString("CodFazenda")
                            val FazendaNome = document.getString("NomeFazenda")
                            val FazendaQuantidade = document.getLong("Quantidade")
                            val FazendaValor = document.getDouble("Valor")

                            val nome = TextView(this)
                            nome.text = "Nome: $FazendaNome"
                            linearLayout.addView(nome)

                            val cod = TextView(this)
                            cod.text = "Código: $FazendaCod"
                            linearLayout.addView(cod)

                            val qtt = TextView(this)
                            qtt.text = "Quantidade: $FazendaQuantidade"
                            linearLayout.addView(qtt)

                            val valor = TextView(this)
                            valor.text = "Valor: $FazendaValor\n\n"
                            linearLayout.addView(valor)

                            limpa_tela()
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Não é possível mostrar !!", Toast.LENGTH_LONG).show()
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        bt_busca = findViewById(R.id.bt_t4_pesquisar)
        bt_tudo = findViewById(R.id.bt_t4_tudo)
        pesquisa = findViewById(R.id.et_t4_pesq)

        bt_busca.setOnClickListener {
            mostrarPesquisa()
        }
        bt_tudo.setOnClickListener {
            mostrarTudo()
            mostrarMedia()
        }
    }
}