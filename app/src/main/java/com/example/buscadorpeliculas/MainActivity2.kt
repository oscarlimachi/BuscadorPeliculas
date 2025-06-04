package com.example.buscadorpeliculas

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buscadorpeliculas.databinding.ActivityMain2Binding
import com.example.buscadorpeliculas.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity2 : AppCompatActivity() {
    companion object{
        const val MOVIE_ID = "MOVIE_ID"
    }
    lateinit var binding: ActivityMain2Binding
    lateinit var movie: Movie
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Barra menu
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#A4F7AC44")))


        val id = intent.getStringExtra(MOVIE_ID)!!
        getMovieById(id)
    }

    private fun getMovieById(id: String) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = ApiService.getInstance()
                movie = service.getMovieId(id)
                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun loadData() {
        Picasso.get().load(movie.Poster).into(binding.imageDetail)
        binding.titleTextView.text=movie.Title
        binding.yearTextView.text=movie.Year
        binding.sinopsisTextView.text=movie.Plot
        binding.duracionTextView.text=movie.Runtime
        binding.directorTextView.text=movie.Director
        binding.generoTextView.text=movie.Genre
        binding.paisTextView.text=movie.Country



    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}