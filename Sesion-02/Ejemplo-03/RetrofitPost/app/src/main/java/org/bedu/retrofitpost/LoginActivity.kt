package org.bedu.retrofitpost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bedu.retrofitpost.api.Api
import org.bedu.retrofitpost.databinding.ActivityLoginBinding
import org.bedu.retrofitpost.model.LoginResponse

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.buttonAccept.setOnClickListener{
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Valores vacíos",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = Api.loginService.login(email, password)

                    withContext(Dispatchers.Main) {
                        if (result.isSuccessful) {
                            onSuccess(result.body())
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Tu correo o contraseña es incorrecto",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (cause: Throwable) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Error de la API",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }

    private fun onSuccess(response: LoginResponse?) {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}
