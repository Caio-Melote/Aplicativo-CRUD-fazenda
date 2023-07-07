package com.example.prova02

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    lateinit var bt_create: Button
    lateinit var bt_read: Button
    lateinit var bt_update: Button
    lateinit var bt_delete: Button
    lateinit var bt_salvar_txt: Button

    fun txtBrabo() {
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("Fazenda")

        val fileName = "DADOS.txt"
        val stringBuilder = StringBuilder()

        collectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val FazendaCod = document.getString("CodFazenda")
                    val FazendaNome = document.getString("NomeFazenda")
                    val FazendaQuantidade = document.getLong("Quantidade")
                    val FazendaValor = document.getDouble("Valor")

                    stringBuilder.append("Nome: $FazendaNome\n")
                    stringBuilder.append("Código: $FazendaCod\n")
                    stringBuilder.append("Quantidade: $FazendaQuantidade\n")
                    stringBuilder.append("Valor: $FazendaValor\n\n")
                }

                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName)
                try {
                    FileOutputStream(file).use { output ->
                        output.write(stringBuilder.toString().toByteArray())
                    }
                    Toast.makeText(this, "Arquivo DADOS salvo em: ${file.absolutePath}", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Não foi possível salvar!", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro no banco de dados!!", Toast.LENGTH_LONG).show()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = FirebaseFirestore.getInstance()

        bt_create = findViewById(R.id.bt_inserir)
        bt_read = findViewById(R.id.bt_mostrar)
        bt_update = findViewById(R.id.bt_atualizar)
        bt_delete = findViewById(R.id.bt_excluir)
        bt_salvar_txt = findViewById(R.id.bt_txt)

        bt_create.setOnClickListener {
            val intent = Intent(this, Activity_Create::class.java)
            startActivity(intent)
        }

        bt_read.setOnClickListener {
            val intent = Intent(this, Activity_Read::class.java)
            startActivity(intent)
        }

        bt_update.setOnClickListener {
            val intent = Intent(this, Activity_Update::class.java)
            startActivity(intent)
        }

        bt_delete.setOnClickListener {
            val intent = Intent(this, Activity_Delete::class.java)
            startActivity(intent)
        }

        bt_salvar_txt.setOnClickListener{
            txtBrabo()
        }
    }
}