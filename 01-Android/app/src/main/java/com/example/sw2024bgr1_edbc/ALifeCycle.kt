package com.example.sw2024bgr1_edbc

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class ALifeCycle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alife_cycle)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showSnackBar("onCreate")

    }

    override fun onResume() {
        super.onResume()
        showSnackBar("onResume")
    }

    override fun onStop() {
        super.onStop()
        showSnackBar("onStop")
    }

    override fun onRestart() {
        super.onRestart()
        showSnackBar("onRestart")
    }

    override fun onPause() {
        super.onPause()
        showSnackBar("onPause")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run{
            putString("textSavedVariable", globalText)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val loadText: String? = savedInstanceState.getString("textSavedVariable")
        if (loadText != null){
//            globalText = loadText
            showSnackBar(loadText)
        }
    }

    var globalText = ""
    fun showSnackBar(text:String){
        globalText += text
        var snack = Snackbar.make(
            findViewById(R.id.btn_lifeCycle),
            globalText,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }


}