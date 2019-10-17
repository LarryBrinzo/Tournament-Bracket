package com.bracket.Views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.View
import com.bracket.Models.Rows
import android.graphics.CornerPathEffect
import java.lang.Math.abs

@SuppressLint("ViewConstructor")
class ConnectionsView(context: Context, fromID: String, toID: String, private val rows: List<Rows>,
                      annotationcount: Int, private val cardWidth: Int, private val inc: Int,
                      winningTeamTextHeight: Int, height: Int) : View(context) {

    private var mPaint: Paint = Paint()
    private var mPath: Path
    private var screenHeight = 0
    private var screenWidth = 0
    private var starty: Int = 0
    private val fromRowAndItemNumber = returnRowAndItemNumber(fromID)
    private val toRowAndItemNumber = returnRowAndItemNumber(toID)

    init {

        mPaint.isAntiAlias = true

        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.parseColor("#CECCCC")
        mPaint.strokeWidth = 4f
        val radius = 35.0f
        val corEffect = CornerPathEffect(radius)
        mPaint.pathEffect = corEffect

        if(rows[fromRowAndItemNumber!!.first - 1].items[0][fromRowAndItemNumber.second - 1].winnerTeamID ==
            rows[toRowAndItemNumber!!.first - 1].items[0][toRowAndItemNumber.second - 1].leftTeamID ||
            rows[fromRowAndItemNumber.first - 1].items[0][fromRowAndItemNumber.second - 1].winnerTeamID ==
            rows[toRowAndItemNumber.first - 1].items[0][toRowAndItemNumber.second - 1].rightTeamID){

            val teamPrimaryColor= Color.parseColor("#4f4e4e")

            mPaint.color = manipulateColor(teamPrimaryColor,0.8f)

        }

        val displayMetrics = DisplayMetrics()

        (getContext() as Activity).windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)

        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels

        starty = (screenHeight - (pxFromDp(context, 75f).toInt() * rows.size) -
                (pxFromDp(context, 20f).toInt() * annotationcount)) / 2

        if(height > screenHeight)
            starty = pxFromDp(context, 10f).toInt() + winningTeamTextHeight

        mPath = Path()

        val fromReach = findReach(fromID)
        val toReach =  findReach(toID)

        if( abs ((toReach!!.second - toReach.first) - (fromReach!!.second - fromReach.first) ) >= cardWidth-10 ){
            if(rows[fromRowAndItemNumber.first-1].items[0].size==rows[toRowAndItemNumber.first-1].items[0].size)
                straightConnection()
            else
                normalConnectionDraw()
        }

        else
            slantConnection()

    }


    private fun normalConnectionDraw(){

        if (rows[toRowAndItemNumber!!.first - 1].items[0].size >
            rows[fromRowAndItemNumber!!.first - 1].items[0].size
        ) {
            fromLowerToHigherRowItems()
        } else {
            fromHigherToLowerRowItems()
        }
    }

    private fun fromLowerToHigherRowItems() {

        var fromElementSize = 60
        var toElementSize = 60

        if (rows[fromRowAndItemNumber!!.first - 1].items[0].isNotEmpty())
            fromElementSize = screenWidth / rows[fromRowAndItemNumber.first - 1].items[0].size

        if (rows[toRowAndItemNumber!!.first - 1].items[0].isNotEmpty())
            toElementSize = screenWidth / rows[toRowAndItemNumber.first - 1].items[0].size

        var fromx = fromElementSize * (fromRowAndItemNumber.second - 1) + (fromElementSize - cardWidth) / 2

        if (toRowAndItemNumber.second % 2 == 0)
            fromx += cardWidth

        var tox: Int

        tox = if (toRowAndItemNumber.second % 2 == 0)
            toElementSize * toRowAndItemNumber.second - toElementSize / 2
        else
            toElementSize * (toRowAndItemNumber.second - 1) + toElementSize / 2

        if (rows[toRowAndItemNumber.first - 1].items[0].size > 2) {

            if (toRowAndItemNumber.second % 2 == 0)
                tox += cardWidth / 14
            else
                tox -= cardWidth / 14
        }

        if (toRowAndItemNumber.second % 2 == 0)
            tox += inc
        else
            tox -= inc

        val toy = (starty.toFloat() + pxFromDp(context, 75f) * (toRowAndItemNumber.first - 1) +
                pxFromDp(context, 35f)).toInt()

        val diffy = (pxFromDp(context, 75f) * (toRowAndItemNumber.first - fromRowAndItemNumber.first - 1) + pxFromDp(
            context,
            45f
        )).toInt()

        val p1 = Point(fromx, toy - diffy)
        val p2 = Point(tox, toy - diffy)
        val p3 = Point(tox, toy)

        mPath.moveTo(p1.x.toFloat(), p1.y.toFloat())
        mPath.lineTo(p2.x.toFloat(), p2.y.toFloat())
        mPath.lineTo(p3.x.toFloat(), p3.y.toFloat())
        mPath.lineTo(p3.x.toFloat(), p3.y.toFloat())
    }

    private fun fromHigherToLowerRowItems() {

        var fromElementSize = 60
        var toElementSize = 60

        if(rows[fromRowAndItemNumber!!.first - 1].items[0].isNotEmpty())
            fromElementSize = screenWidth / rows[fromRowAndItemNumber.first-1].items[0].size

        if(rows[toRowAndItemNumber!!.first - 1].items[0].isNotEmpty())
            toElementSize = screenWidth / rows[toRowAndItemNumber.first-1].items[0].size

        val fromy = starty + pxFromDp(context, 75f).toInt() * fromRowAndItemNumber.first +
                pxFromDp(context, 20f).toInt()

        var fromx: Int

        fromx = if(fromRowAndItemNumber.second%2==0)
            (fromElementSize*fromRowAndItemNumber.second) - (fromElementSize/2)
        else
            (fromElementSize*(fromRowAndItemNumber.second-1)) + (fromElementSize/2)

        if(rows[fromRowAndItemNumber.first - 1].items[0].size > 2){

            if(fromRowAndItemNumber.second%2==0)
                fromx +=cardWidth/14
            else
                fromx -=cardWidth/14
        }

        var tox = (toElementSize*(toRowAndItemNumber.second-1)) + ((toElementSize - cardWidth)/2)

        if(fromRowAndItemNumber.second%2==0)
            tox+=cardWidth

        if(fromRowAndItemNumber.second%2==0)
            fromx += inc
        else fromx -= inc

        val diffy = pxFromDp(context, 15f).toInt() *
                (toRowAndItemNumber.first-fromRowAndItemNumber.first)+
                pxFromDp(context, 60f).toInt() * ((toRowAndItemNumber.first-fromRowAndItemNumber.first)-1) +
                pxFromDp(context, 30f).toInt()

        val p1 = Point(fromx,fromy)
        val p2 = Point(fromx,fromy + diffy)
        val p3 = Point(tox,fromy + diffy)

        mPath.moveTo(p1.x.toFloat(), p1.y.toFloat())
        mPath.lineTo(p2.x.toFloat(), p2.y.toFloat())
        mPath.lineTo(p3.x.toFloat(), p3.y.toFloat())
        mPath.lineTo(p3.x.toFloat(), p3.y.toFloat())
    }


    private fun straightConnection(){

        val fromy = starty + pxFromDp(context, 75f).toInt() * fromRowAndItemNumber!!.first +
                pxFromDp(context, 20f).toInt()

        val toy = fromy + (pxFromDp(context, 75f) *
                (abs(toRowAndItemNumber!!.first - fromRowAndItemNumber.first) - 1)).toInt() +
                pxFromDp(context, 15f).toInt()

        var fromElementSize = 60

        if(rows[fromRowAndItemNumber.first - 1].items[0].isNotEmpty())
            fromElementSize = screenWidth / rows[fromRowAndItemNumber.first-1].items[0].size

        val fromx = (fromElementSize*fromRowAndItemNumber.second) - (fromElementSize/2)

        val p1 = Point(fromx,fromy)
        val p2 = Point(fromx,toy)

        mPath.moveTo(p1.x.toFloat(), p1.y.toFloat())
        mPath.lineTo(p2.x.toFloat(), p2.y.toFloat())
        mPath.lineTo(p2.x.toFloat(), p2.y.toFloat())
    }


    private fun slantConnection(){

        val fromy = (starty.toFloat() + pxFromDp(context, 75f) * fromRowAndItemNumber!!.first +
                pxFromDp(context, 20f)).toInt()

        val toy = (fromy.toFloat() + (pxFromDp(context, 75f) *
                (abs(toRowAndItemNumber!!.first - fromRowAndItemNumber.first) - 1)).toInt().toFloat() +
                pxFromDp(context, 15f)).toInt()

        var fromElementSize = 60
        var toElementSize = 60

        if(rows[fromRowAndItemNumber.first - 1].items[0].isNotEmpty())
            fromElementSize = screenWidth / rows[fromRowAndItemNumber.first-1].items[0].size

        if(rows[toRowAndItemNumber.first - 1].items[0].isNotEmpty())
            toElementSize = screenWidth / rows[toRowAndItemNumber.first-1].items[0].size

        val fromx = (fromElementSize*fromRowAndItemNumber.second) - (fromElementSize/2)

        val tox = (toElementSize*toRowAndItemNumber.second) - (toElementSize/2)

        val p1 = Point(fromx,fromy)
        val p2 = Point(fromx,fromy + 40)
        val p3 = Point(tox,toy - 40)
        val p4 = Point(tox,toy)

        mPath.moveTo(p1.x.toFloat(), p1.y.toFloat())
        mPath.lineTo(p2.x.toFloat(), p2.y.toFloat())
        mPath.lineTo(p3.x.toFloat(), p3.y.toFloat())
        mPath.lineTo(p4.x.toFloat(), p4.y.toFloat())
        mPath.lineTo(p4.x.toFloat(), p4.y.toFloat())

    }


    private fun findReach(elementID: String): Pair<Int, Int>?{

        val rowAndItemNumber = returnRowAndItemNumber(elementID)

        var elementSize = 60

        if(rows[rowAndItemNumber!!.first - 1].items[0].isNotEmpty())
            elementSize = screenWidth / rows[rowAndItemNumber.first-1].items[0].size

        val startx = elementSize*(rowAndItemNumber.second-1)
        val endx = startx+elementSize

        return Pair(startx,endx)

    }


    private fun returnRowAndItemNumber(elementID: String) : Pair<Int, Int>?{

        var rowAndItem: Pair<Int, Int> ?= null

        for(r in rows.indices){
            for(i in rows[r].items[0].indices){

                if(rows[r].items[0][i].elementID == elementID){
                    rowAndItem = Pair(r+1,i+1)
                    return rowAndItem
                }
            }
        }
        return rowAndItem
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        parent.requestDisallowInterceptTouchEvent(true)
        canvas.drawPath(mPath, mPaint)
    }

    private fun manipulateColor(color: Int, factor: Float): Int{

        val a = Color.alpha(color)
        val r = Math.round(Color.red(color) * factor)
        val g = Math.round(Color.green(color) * factor)
        val b = Math.round(Color.blue(color) * factor)

        return Color.argb(
            a,
            Math.min(r, 255),
            Math.min(g, 255),
            Math.min(b, 255)
        )
    }

    private fun pxFromDp(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

}
