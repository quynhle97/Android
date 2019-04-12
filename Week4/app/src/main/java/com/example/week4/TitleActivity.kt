package com.example.week4

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_title_color.*
import kotlinx.android.synthetic.main.textview_color.view.*

class TitleActivity : AppCompatActivity() {
    var adapter: ColorAdapter? = null
    var list = ArrayList<Int>()
    var colorPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title_color)
        setTitle("Title")

        val edtTitle = findViewById<EditText>(R.id.edtTitle)
        val colorCurrent = findViewById<TextView>(R.id.colorCurrent)
        val gvColor = findViewById<GridView>(R.id.gvColor)
        val btnSave = findViewById<Button>(R.id.btnSaveColorTitle)

        val intent = intent.extras
        if (intent != null) {
            val colorPosCreate = intent.getInt("color")
            colorPosition = colorPosCreate
            colorCurrent.setBackgroundColor(getColorRes(colorPosCreate))
            val title : String? = intent.getString("title")
            edtTitle.setText(title)
        }

        setColorTitle()

        adapter = ColorAdapter(this, list)
        gvColor.adapter = adapter

        gvColor.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            colorPosition = position
            edtTitle.setHintTextColor(getColorRes(colorPosition))
            colorCurrent.setBackgroundColor(getColorRes(colorPosition))
        }

        btnSave.setOnClickListener{
            finish()
        }
    }

    override fun finish() {
        val intent = Intent()
        intent.putExtra("titleName", edtTitle.text.toString())
        intent.putExtra("colorSelected", colorPosition)

        setResult(Activity.RESULT_OK, intent)
        super.finish()
    }

    private fun getColorRes(pos: Int): Int {
        when (pos) {
            0 -> return Color.RED
            1 -> return Color.BLACK
            2 -> return Color.BLUE
            3 -> return Color.CYAN
            4 -> return Color.MAGENTA
            5 -> return Color.DKGRAY
            6 -> return Color.YELLOW
            7 -> return Color.GRAY
            8 -> return Color.GREEN
            else -> return Color.BLACK
        }
    }

    private fun setColorTitle() {
        list.add(Color.RED)
        list.add(Color.BLACK)
        list.add(Color.BLUE)
        list.add(Color.CYAN)
        list.add(Color.MAGENTA)
        list.add(Color.DKGRAY)
        list.add(Color.YELLOW)
        list.add(Color.GRAY)
        list.add(Color.GREEN)
    }

    class ColorAdapter: BaseAdapter {
        var colorList = ArrayList<Int>()
        var context: Context? = null

        constructor(context: Context, colorListList: ArrayList<Int>): super() {
            this.context = context
            this.colorList = colorListList
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var color = colorList[position]

            var inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var colorView = inflater.inflate(R.layout.textview_color, null)
            colorView.textViewColor.setBackgroundColor(color)
            return colorView
        }

        override fun getItem(position: Int): Any {
            return colorList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return colorList.size
        }
    }
}
