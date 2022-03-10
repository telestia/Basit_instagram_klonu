package com.harunsubasi.kotlininstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FederatedAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.harunsubasi.kotlininstagram.databinding.ActivityMainBinding
import com.harunsubasi.kotlininstagram.databinding.ActivityUploadBinding

private lateinit var binding: ActivityMainBinding
private lateinit var auth : FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
        val currentUser = auth.currentUser

        if(currentUser != null){
            val intent = Intent(this,FeedActivity::class.java)
            startActivity(intent)
        }
    }
    fun signInClicked(view : View){

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()
        if(email.equals("")  || password.equals("")){

            Toast.makeText(MainActivity@this,"Enter email and password!",Toast.LENGTH_SHORT).show()
        }
        else {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                val intent = Intent(MainActivity@this,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(MainActivity@this,it.localizedMessage,Toast.LENGTH_SHORT).show()
            }

        }
    }
    fun signUpClicked(view : View){

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()){
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                //başarılı olunca cagırılacak
                val intent = Intent(MainActivity@this,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(MainActivity@this,it.localizedMessage,Toast.LENGTH_SHORT).show()
            }

            //sunucudan gelicek cevabı düşünmemiz lazım

        }
        else{
            Toast.makeText(this,"Enter email and password!",Toast.LENGTH_LONG).show()
        }
    }
}