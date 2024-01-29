package com.example.randommeme
import android.content.Intent
import android.content.Intent.createChooser
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    private lateinit var imageurl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }
    private fun loadmeme(){
        findViewById<ProgressBar>(R.id.progressbar).visibility=View.VISIBLE
        val meme=findViewById<ImageView>(R.id.memeimage)
        val url = " https://meme-api.com/gimme"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                imageurl=response.getString("url")

                Glide.with(this).load(imageurl).listener(object :RequestListener<Drawable>{
                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        findViewById<ProgressBar>(R.id.progressbar).visibility=View.GONE
                        return false
                    }
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        findViewById<ProgressBar>(R.id.progressbar).visibility=View.GONE

                        return false
                    }
                }).into(meme)

            },
            { error ->
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
            },
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
    fun Sharememe(view: View){
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plian"
        intent.putExtra(Intent.EXTRA_TEXT, imageurl)
        val choose=createChooser(intent,"share")
        startActivity(choose)

    }

    fun Nextmeme(view: View) {
        loadmeme()
    }
}