package com.osenov.rickandmorty.ui.list_characters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.osenov.rickandmorty.R

class CharactersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.characters_main)
    }
}