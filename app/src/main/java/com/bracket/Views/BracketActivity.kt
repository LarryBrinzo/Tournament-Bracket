package com.bracket.Views

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ScrollView
import com.bracket.Adapters.RowsAdapter
import com.bracket.Contracts.BracketInterface
import com.bracket.Models.Annotations
import com.bracket.Models.BracketActivityModel
import com.bracket.Models.Connections
import com.bracket.Models.Rows
import com.bracket.R
import kotlin.math.max

class BracketActivity : AppCompatActivity(), BracketInterface.View {

    private var bracketModel : BracketActivityModel ?= null
    private lateinit var bracketRecycle : RecyclerView
    private lateinit var rowsAdapter: RowsAdapter
    private lateinit var rows: List<Rows>
    private lateinit var annotations: List<Annotations>
    private lateinit var view: NestedScrollView
    private lateinit var layout : ConstraintLayout
    private var cardWidth = 0
    private var width = 0
    private var height = 0
    private var annotationcount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_barcket)

        bracketModel = BracketActivityModel(applicationContext)
        bracketRecycle = findViewById(R.id.bracketrecycle)
        layout = findViewById(R.id.layout)
        view = findViewById(R.id.view)

        bracketRecycle.isNestedScrollingEnabled = false

        initView()
    }

    private fun initView(){
        setRows()
    }

    override fun setRows() {
        rows = bracketModel!!.getRows()

        var maxItem=0

        for(r in rows.indices)
            maxItem = max(maxItem,rows[r].items[0].size)

        for(r in rows.indices){
            if(rows[r].items[0].size == maxItem)
                annotationcount++
        }

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels - pxFromDp(applicationContext, 4f).toInt()
        height = displayMetrics.heightPixels

        cardWidth = width / maxItem
        cardWidth -= pxFromDp(applicationContext, 6f).toInt()

        setAnnotations()
    }

    override fun setAnnotations() {
        annotations = bracketModel!!.getAnnotations()

        adapterSetup()
        setConnections()
    }

    override fun setConnections() {
        val connections: List<Connections> = bracketModel!!.getConnections()

        height=max(height ,(pxFromDp(applicationContext, 75f).toInt() * rows.size) -
                (pxFromDp(applicationContext, 20f).toInt() * annotationcount) + pxFromDp(applicationContext, 40f).toInt())

        for(c in connections.indices){

            val connection = connections[c]

            for (t in connection.toElementIDs.indices){

                val myView = MyView(this,connection.fromElementID,connection.toElementIDs[t],
                    rows,annotationcount,cardWidth,(cardWidth/6)*t)

                layout.addView(myView,width,height)
            }
        }

//        val myView = MyView(this,"211","221",
//            rows,annotationcount,cardWidth,(cardWidth/6)*0)
//
//        val totalHeight = view.getChildAt(0).getHeight()
//
//        layout.addView(myView,width,height)
    }

    private fun adapterSetup(){

        rowsAdapter = RowsAdapter(rows, annotations, cardWidth, width, applicationContext)
        val recycleRow = GridLayoutManager(applicationContext, 1)
        bracketRecycle.layoutManager = recycleRow
        bracketRecycle.itemAnimator = DefaultItemAnimator()
        bracketRecycle.adapter = rowsAdapter

    }

    private fun pxFromDp(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

}