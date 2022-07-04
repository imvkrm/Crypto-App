package com.vikram.cryptoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vikram.cryptoapp.R
import com.vikram.cryptoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}