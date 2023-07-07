package com.example.prova02

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore


class Activity_Update : AppCompatActivity() {

    lateinit var nome: EditText
    lateinit var cod: EditText
    lateinit var valor: EditText
    lateinit var quantidade: EditText
    lateinit var bt_atualizar: Button

    fun limpa_tela() {
        nome.text.clear()
        cod.text.clear()
        valor.text.clear()
        quantidade.text.clear()
    }

    @SuppressLint("SuspiciousIndentation")
    fun AttBanco() {
        val NomeFazenda = nome.text.toString()
        val CodFazenda = cod.text.toString()
        val qtt = quantidade.text.toString()
        val vl = valor.text.toString()


        if (NomeFazenda.isEmpty() || CodFazenda.isEmpty() || qtt.isEmpty() || vl.isEmpty()) {
            Toast.makeText(this, "Não deixe campos em branco !!", Toast.LENGTH_LONG).show()
            return
        }else {
        val Quantidade = qtt.toInt()
        val Valor = vl.toDouble()

        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("Fazenda").document(CodFazenda)
            collectionRef.update(
            "NomeFazenda", NomeFazenda,
            "Quantidade", Quantidade,
            "Valor", Valor
        ).addOnSuccessListener {
            Toast.makeText(this, "Fazenda Atualizada!!", Toast.LENGTH_LONG).show()
            limpa_tela()
            finish()
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Não foi possível ATUALIZAR a fazenda!!", Toast.LENGTH_LONG).show()
            Log.e("AttBanco", "DEU RUIM", e)
        }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        nome = findViewById(R.id.et_t2_nome)
        cod = findViewById(R.id.et_t2_cod)
        valor = findViewById(R.id.et_t2_valor)
        quantidade = findViewById(R.id.et_t2_quantidade)
        bt_atualizar = findViewById(R.id.bt_t2_att)

        bt_atualizar.setOnClickListener {
            AttBanco()
        }
    }
}