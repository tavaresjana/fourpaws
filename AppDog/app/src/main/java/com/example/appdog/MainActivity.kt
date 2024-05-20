package com.example.appdog
import android.content.Intent
import android.view.View
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appdog.databinding.ActivityMainBinding
import com.example.appdog.view.Home
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Define o listener do btn de login
        binding.btLogin.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val senha = binding.editSenha.text.toString()

            when {
                nome.isEmpty() -> {
                    mensagem(it,"Por favor, insira seu nome." )
                } senha.isEmpty() ->{
                    mensagem(it, "Por favor, insira sua senha")
                }senha.length <=5 -> {
                    mensagem(it, "A senha deve ter no mínimo 6 caracteres.")
                }else -> {
                    navegarParaHome(nome)
                }
            }
        }
    }

    // Método para exibir uma msg usando o Snackbar
    private fun mensagem(view: View, mensagem:String){
        val snackbar = Snackbar.make(view,mensagem,Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#FF0000"))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    // Método para navegar para a tela home
    private fun navegarParaHome(nome:String){
        val intent = Intent(this, Home::class.java)
        intent.putExtra("nome",nome)
        startActivity(intent)

    }
}
