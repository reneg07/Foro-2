package com.example.controldegastos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtRegister: TextView
    private lateinit var txtMessage: TextView

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var btnGoogle: Button

    private val RC_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtRegister = findViewById(R.id.txtRegister)
        txtMessage = findViewById(R.id.txtMessage)
        btnGoogle = findViewById(R.id.btnGoogle)

        btnLogin.setOnClickListener {
            loginUser()
        }

        txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnGoogle.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun loginUser() {
        val email = edtEmail.text.toString().trim()
        val password = edtPassword.text.toString().trim()

        if (!validateFields(email, password)) return

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                txtMessage.text = "Correo o contraseña incorrectos"
            }
    }

    private fun registerUser() {
        val email = edtEmail.text.toString().trim()
        val password = edtPassword.text.toString().trim()

        if (!validateFields(email, password)) return

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                txtMessage.text = "Usuario registrado correctamente"
            }
            .addOnFailureListener { error ->
                txtMessage.text = error.message ?: "Error al registrar usuario"
            }
    }

    private fun validateFields(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            txtMessage.text = "Ingrese su correo electrónico"
            return false
        }

        if (!email.contains("@")) {
            txtMessage.text = "Ingrese un correo válido"
            return false
        }

        if (password.isEmpty()) {
            txtMessage.text = "Ingrese su contraseña"
            return false
        }

        if (password.length < 6) {
            txtMessage.text = "La contraseña debe tener al menos 6 caracteres"
            return false
        }

        return true
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.result
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                auth.signInWithCredential(credential)
                    .addOnSuccessListener {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al iniciar sesión con Google", Toast.LENGTH_SHORT).show()
                    }

            } catch (e: Exception) {
                Toast.makeText(this, "No se pudo completar el inicio con Google", Toast.LENGTH_SHORT).show()
            }
        }
    }
}