package com.example.controldegastos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var txtBack: TextView
    private lateinit var txtMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        txtBack = findViewById(R.id.txtBack)
        txtMessage = findViewById(R.id.txtMessage)

        btnRegister.setOnClickListener {
            registerUser()
        }

        txtBack.setOnClickListener {
            finish()
        }
    }

    private fun registerUser() {
        val email = edtEmail.text.toString().trim()
        val password = edtPassword.text.toString().trim()
        val confirmPassword = edtConfirmPassword.text.toString().trim()

        when {
            email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
                txtMessage.text = "Complete todos los campos"
            }

            !email.contains("@") -> {
                txtMessage.text = "Correo inválido"
            }

            password.length < 6 -> {
                txtMessage.text = "La contraseña debe tener al menos 6 caracteres"
            }

            password != confirmPassword -> {
                txtMessage.text = "Las contraseñas no coinciden"
            }

            else -> {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        finish()
                    }
                    .addOnFailureListener { e ->
                        txtMessage.text = e.message
                    }
            }
        }
    }
}