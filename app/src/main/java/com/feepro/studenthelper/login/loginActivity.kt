package com.feepro.studenthelper.login

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.feepro.studenthelper.MainActivity
import com.feepro.studenthelper.R
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class loginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
// ...
// Initialize Firebase Auth
    val TAG = "loginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance();
        val currentUser = auth.currentUser
        updateUI(currentUser)
        setContentView(R.layout.activity_login)
        loginBtn.setOnClickListener {
            val email = loginET.text.toString()
            val password = passET.text.toString()
            //println("\n email: $email \n password: $password")
            if(email == "" || password == "")
                Toast.makeText(baseContext,"Поля должны быть заполненны",Toast.LENGTH_SHORT).show()
            else
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Ошибка входа, проверьте данные.",
                            Toast.LENGTH_SHORT).show()
                            //updateUI(null)
                    }

                } }
        regBtn.setOnClickListener {
            startActivity(Intent(applicationContext,registerActivity::class.java))
        }
        anonBtn.setOnClickListener {auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInAnonymously:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInAnonymously:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // ...
            }
        }
        /*googleBtn.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        }*/ //TODO
        googleBtn.visibility = View.GONE
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser == null) return
        val mainIntent = Intent(applicationContext, MainActivity::class.java)
        mainIntent.putExtra("email",currentUser.email.toString())
        startActivity(mainIntent)
    }

}
