package com.feepro.studenthelper.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.feepro.studenthelper.MainActivity
import com.feepro.studenthelper.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.loginET
import kotlinx.android.synthetic.main.activity_register.passET

class registerActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance();
    private val TAG = "registerActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        singUpBtn.setOnClickListener{
            val email = loginET.text.toString()
            val password = passET.text.toString()
            if(email == "" || password == "")
                Toast.makeText(baseContext,"Поля должны быть заполненны",Toast.LENGTH_SHORT).show()
            else
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }

                        // ...
                    }}
    }
    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser == null) return
        val mainIntent = Intent(applicationContext, MainActivity::class.java)
        mainIntent.putExtra("email",currentUser.email.toString())
        startActivity(mainIntent)
    }
}
