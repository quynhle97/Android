package com.example.week4

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var position = -1
    private var posColor = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Main Screen")

        val btnBackground = findViewById<Button>(R.id.btnBackground)
        val btnTitle = findViewById<Button>(R.id.btnTitle)
        val imgBackground = findViewById<ImageView>(R.id.imgBackground)
        val txvTitle = findViewById<TextView>(R.id.txvTitle)

        btnBackground.setOnClickListener {
            changeBackground()
        }

        btnTitle.setOnClickListener {
            changeTitleColor()
        }
    }

    private fun changeBackground() {
        val intent = Intent(this, BackgroundActivity::class.java)
        intent.putExtra("background", position)
        startActivityForResult(intent, REQUEST_BACKGROUND)
    }

    private fun changeTitleColor() {
        val intent = Intent(this, TitleActivity::class.java)
        intent.putExtra("title", txvTitle.text)
        intent.putExtra("color", posColor)
        startActivityForResult(intent, REQUEST_TITLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_BACKGROUND && resultCode == Activity.RESULT_OK) {
            val imgBackground = findViewById<ImageView>(R.id.imgBackground)
            var image = R.mipmap.ic_launcher
            val indexImage = data?.getIntExtra("indexImage", 0)
            position = indexImage!!

            when (indexImage) {
                0 -> image = R.mipmap.intel
                1 -> image = R.mipmap.renesas
                2 -> image = R.mipmap.savarti
                3 -> image = R.mipmap.esilicon
                4 -> image = R.mipmap.khxhnv
                5 -> image = R.mipmap.hcmus
                6 -> image = R.mipmap.hcmut
                7 -> image = R.mipmap.hcmuel
                8 -> image = R.mipmap.hcmuit
                else -> image = R.mipmap.ic_launcher_round
            }
            imgBackground.setImageResource(image)
        }
        if (requestCode == REQUEST_TITLE && resultCode == Activity.RESULT_OK) {
            val titleName = data?.getStringExtra("titleName")
            val colorSelected = data?.getIntExtra("colorSelected", 0)
            posColor = colorSelected!!
            txvTitle.text = titleName
            var color = -1
            when (colorSelected) {
                0 -> color = Color.RED
                1 -> color = Color.BLACK
                2 -> color = Color.BLUE
                3 -> color = Color.CYAN
                4 -> color = Color.MAGENTA
                5 -> color = Color.DKGRAY
                6 -> color = Color.YELLOW
                7 -> color = Color.GRAY
                8 -> color = Color.GREEN
                else -> color = Color.BLACK
            }
            txvTitle.setTextColor(color)
        }
    }

    companion object {
        const val REQUEST_BACKGROUND = 1501
        const val REQUEST_TITLE = 1509
    }
}
