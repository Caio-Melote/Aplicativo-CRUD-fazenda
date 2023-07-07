package com.example.prova02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class Activity_Create : AppCompatActivity() {

    lateinit var et_nome: EditText
    lateinit var et_cod: EditText
    lateinit var et_valor: EditText
    lateinit var et_quantidade: EditText
    lateinit var bt_enviar: Button

    fun limpa_tela() {
        et_nome.text.clear()
        et_cod.text.clear()
        et_valor.text.clear()
        et_quantidade.text.clear()
    }

    fun AddBanco() {
        val NomeFazenda = et_nome.text.toString()
        val CodFazenda = et_cod.text.toString()
        val qtt = et_quantidade.text.toString()
        val vl = et_valor.text.toString()

        if (NomeFazenda.isEmpty() || CodFazenda.isEmpty() || qtt.isEmpty() || vl.isEmpty()) {
            Toast.makeText(this, "Não deixe campos em branco !!", Toast.LENGTH_LONG).show()
            return
        } else {
            val Quantidade = qtt.toInt()
            val Valor = vl.toDouble()

            val db = FirebaseFirestore.getInstance()
            val productRef = db.collection("Fazenda").document(CodFazenda)

            val fazenda = hashMapOf(
                "NomeFazenda" to NomeFazenda,
                "CodFazenda" to CodFazenda,
                "Quantidade" to Quantidade,
                "Valor" to Valor
            )
            productRef.set(fazenda)
                .addOnSuccessListener {
                    Toast.makeText(this, "Fazenda Adicionada!!", Toast.LENGTH_LONG).show()
                    limpa_tela()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Não foi possível CRIAR a fazenda!!", Toast.LENGTH_LONG).show()
                    Log.e("AddBanco", "DEU RUIM", e)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        et_nome = findViewById(R.id.et_t1_nome)
        et_cod = findViewById(R.id.et_t1_cod)
        et_valor = findViewById(R.id.et_t1_valor)
        et_quantidade = findViewById(R.id.et_t1_quantidade)
        bt_enviar = findViewById(R.id.bt_t1_inserir)

        bt_enviar.setOnClickListener {
            AddBanco()
        }
    }
}