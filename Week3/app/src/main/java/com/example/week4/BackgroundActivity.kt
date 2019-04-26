package com.example.week4

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.image_logo.view.*

class BackgroundActivity : AppCompatActivity() {
    var adapter: LogoAdapter? = null
    var list = ArrayList<Int>()
    var itemPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background)
        setTitle("Background")

        val gridView = findViewById<GridView>(R.id.gvColor)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val imgBackground = findViewById<ImageView>(R.id.colorCurrent)

        val intent = intent.extras
        if (intent != null) {
            val pos = intent.getInt("background")
            itemPosition = pos
            imgBackground.setImageResource(getImageResource(pos))
        }

        setImageLogo()

        adapter = LogoAdapter(this, list)
        gridView.adapter = adapter

        gridView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                itemPosition = position
                imgBackground.setImageResource(getImageResource(position))
            }

        btnSave.setOnClickListener {
            finish()
        }
    }

    private fun setImageLogo() {
        list.add(R.mipmap.intel)
        list.add(R.mipmap.renesas)
        list.add(R.mipmap.savarti)
        list.add(R.mipmap.esilicon)
        list.add(R.mipmap.khxhnv)
        list.add(R.mipmap.hcmus)
        list.add(R.mipmap.hcmut)
        list.add(R.mipmap.hcmuel)
        list.add(R.mipmap.hcmuit)
    }

    private fun getImageResource(pos: Int): Int {
        when (pos) {
            0 -> return R.mipmap.intel
            1 -> return R.mipmap.renesas
            2 -> return R.mipmap.savarti
            3 -> return R.mipmap.esilicon
            4 -> return R.mipmap.khxhnv
            5 -> return R.mipmap.hcmus
            6 -> return R.mipmap.hcmut
            7 -> return R.mipmap.hcmuel
            8 -> return R.mipmap.hcmuit
            else -> return R.mipmap.ic_launcher_round
        }
    }

    override fun finish() {
        val intent = Intent()
        intent.putExtra("indexImage", itemPosition)

        setResult(Activity.RESULT_OK, intent)
        super.finish()
    }

    class LogoAdapter: BaseAdapter {
        var logoList = ArrayList<Int>()
        var context: Context? = null

        constructor(context: Context, logoList: ArrayList<Int>): super() {
            this.context = context
            this.logoList = logoList
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val logo = this.logoList[position]

            var inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var logoView = inflater.inflate(R.layout.image_logo, null)
            logoView.imgLogo.setImageResource(logo)
            return logoView
        }

        override fun getItem(position: Int): Any {
            return logoList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return logoList.size
        }
    }
}