package com.example.kotlinroom_noviadhyacamila_21

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.kotlinroom_noviadhyacamila_21.room.Constants
import com.example.kotlinroom_noviadhyacamila_21.room.Movie
import com.example.kotlinroom_noviadhyacamila_21.room.MovieDb
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.list_movie.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    val db by lazy {MovieDb(this)}
    private var movieid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setupView()
        setupListener()
    }

    private fun setupListener(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        btn_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.movieDao().addMovie(
                    Movie(0, et_title.text.toString(),
                        et_description.text.toString())
                )

                finish()
            }
        }

        btn_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.movieDao().addMovie(
                    Movie(movieid, et_title.text.toString(),
                        et_description.text.toString())
                )

                finish()
            }
        }
    }

    private fun setupView(){
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constants.TYPE_CREATE -> {
                btn_update.visibility = View.GONE

            }
            Constants.TYPE_READ -> {
                btn_save.visibility = View.GONE
                btn_update.visibility = View.GONE
                getMovie()
            }
            Constants.TYPE_UPDATE -> {
                btn_save.visibility = View.GONE
                getMovie()
            }
        }
    }

    private fun getMovie(){
        movieid = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val movies = db.movieDao().getMovie()[0]
            et_title.setText(movies.title)
            et_description.setText(movies.desc)

        }

        }
    }
