package com.example.buscadorpeliculas

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buscadorpeliculas.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MovieAdapter
    var movieList: List<Movie> = emptyList()
    //lista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = MovieAdapter(movieList, { position ->
            val movie = movieList[position]
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("MOVIE_ID", movie.imdbID)
            startActivity(intent)
        })

       binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        searchMoviesByText("Batman")
        supportActionBar?.title= "Movie List"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#A4F7AC44")))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun searchMoviesByText(query: String) {
        lifecycleScope.launch {
            try {
                val service = ApiService.getInstance()
                val searchResponse = service.searchMovies(query)

                if (searchResponse.Response == "True") {
                    movieList = searchResponse.Search
                    adapter.updateItems(movieList)
                } else {
                    adapter.updateItems(emptyList())
                    Toast.makeText(this@MainActivity, "No se encontraron películas", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Error en la búsqueda", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main,menu)

        val menuItem = menu.findItem(R.id.menu_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchMoviesByText(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false            }
        })
        return true
    }
}