package com.textsdev.postsfetcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.textsdev.postsfetcher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityMainBinding.inflate(layoutInflater)
        setContentView(inflate.root)

    }
}