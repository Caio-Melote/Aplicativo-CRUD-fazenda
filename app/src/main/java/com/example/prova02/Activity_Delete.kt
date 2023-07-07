package com.example.prova02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class Activity_Delete : AppCompatActivity() {

    lateinit var fazenda_cod: EditText
    lateinit var bt_excluir: Button

    fun limpa_tela() {
        fazenda_cod.text.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        fazenda_cod = findViewById(R.id.et_t3_cod)
        bt_excluir = findViewById(R.id.bt_t3_exc)

        fun DeleteBanco() {

            val CodFazenda = fazenda_cod.text.toString()

            if (CodFazenda.isEmpty()) {
                Toast.makeText(this, "Não deixe nada em branco.", Toast.LENGTH_LONG).show()
                return
            } else {

                val db = FirebaseFirestore.getInstance()
                val collectionRef = db.collection("Fazenda").document(CodFazenda)
                collectionRef.get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            collectionRef.delete()
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Fazenda excluída !", Toast.LENGTH_LONG).show()
                                    limpa_tela()
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Não foi possível excluir a fazenda!", Toast.LENGTH_LONG).show()
                                }
                        } else {
                            Toast.makeText(this, "Fazenda não existente no banco!", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Banco de dados deu errado!", Toast.LENGTH_LONG).show()
                    }
            }
        }

        bt_excluir.setOnClickListener(){
            DeleteBanco()
        }
    }
}