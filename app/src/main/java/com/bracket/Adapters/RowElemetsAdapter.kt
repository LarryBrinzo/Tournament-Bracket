package com.bracket.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bracket.Models.Annotations
import com.bracket.Models.Items
import com.bracket.R
import com.bumptech.glide.Glide

class RowElemetsAdapter(private val items: List<Items>, private val annotations: List<Annotations>, private val cardWidth: Int, private val Width: Int, private val context: Context?) :
    RecyclerView.Adapter<RowElemetsAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.row_element_card, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, @SuppressLint("RecyclerView") position: Int) {

        val totalWidth= Width / itemCount
        holder.maincard.minWidth=totalWidth

        if(items.isEmpty()) {
            holder.emptyCard.visibility = View.VISIBLE
        }

        else if(items[position].style == "round"){
            holder.roundCard.visibility = View.VISIBLE

            holder.roundCard.layoutParams.width=cardWidth

            holder.team1Img.layoutParams.width=cardWidth/5
            holder.team2Img.layoutParams.width=cardWidth/5

            holder.team1.text=items[position].leftPrimaryScore
            holder.team2.text=items[position].rightPrimaryScore

            if(items[position].leftTeamID == items[position].winnerTeamID){
                holder.leftTeam.background = context?.let { ContextCompat.getDrawable(it, R.drawable.left_rounded_corner) }

                val teamPrimaryColor= Color.parseColor("#4f4e4e")

                val bgDrawable: LayerDrawable = holder.leftTeam.background as LayerDrawable
                val lrCorner: GradientDrawable = bgDrawable.findDrawableByLayerId(R.id.lr_corner) as GradientDrawable
                lrCorner.setColor(teamPrimaryColor)
                context?.let { pxFromDp(it, 1f).toInt() }?.let { lrCorner.setStroke(it,manipulateColor(teamPrimaryColor,0.8f)) }

                holder.team1.setTextColor(Color.parseColor("#ffffff"))
            }

            else{
                holder.rightTeam.background = context?.let { ContextCompat.getDrawable(it, R.drawable.right_rounded_corner) }

                val teamPrimaryColor= Color.parseColor("#4f4e4e")

                val bgDrawable: LayerDrawable = holder.rightTeam.background as LayerDrawable
                val rrCorner: GradientDrawable = bgDrawable.findDrawableByLayerId(R.id.rr_corner) as GradientDrawable
                rrCorner.setColor(teamPrimaryColor)
                context?.let { pxFromDp(it, 1f).toInt() }?.let { rrCorner.setStroke(it,manipulateColor(teamPrimaryColor,0.8f)) }

                holder.team2.setTextColor(Color.parseColor("#ffffff"))
            }

            for(a in annotations.indices){

                if(annotations[a].elementID == items[position].elementID){

                    if(annotations[a].edge == "top"){

                        holder.annotationtop.visibility = View.VISIBLE

                        if(annotations[a].alignment == "left")
                            holder.annotationtop1.text=annotations[a].text
                        else
                            holder.annotationtop2.text=annotations[a].text
                    }

                    else{

                        holder.annotationbottom.visibility = View.VISIBLE

                        if(annotations[a].alignment == "left")
                            holder.annotationbottom1.text=annotations[a].text
                        else
                            holder.annotationbottom2.text=annotations[a].text
                    }

                }

            }
    }

        else if(items[position].style == "stage"){
            holder.stageCard.visibility = View.VISIBLE

            holder.stageCard.layoutParams.width=cardWidth

            holder.matchName.text=items[position].name
        }

        else if(items[position].style == "final"){
            holder.finalCard.visibility = View.VISIBLE

            holder.finalCard.layoutParams.width=cardWidth

            if (context != null) {
                Glide.with(context).load(items[position].trophyRemoteImageURL).into(holder.finalimg)
            }

        }

    }

    override fun getItemCount(): Int {
        var arr = 0
        try {
            arr = if (items.isEmpty()) {
                1
            } else {
                items.size
            }
        }
        catch (ignored: Exception) { }

        return arr
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var roundCard: LinearLayout
        var stageCard: LinearLayout
        var finalCard: LinearLayout
        var emptyCard: LinearLayout
        var leftTeam: LinearLayout
        var rightTeam: LinearLayout
        var team1Img: ImageView
        var team2Img: ImageView
        var maincard: ConstraintLayout
        var matchName: TextView
        var team1: TextView
        var team2: TextView
        var annotationtop: LinearLayout
        var annotationbottom: LinearLayout
        var annotationtop1: TextView
        var annotationtop2: TextView
        var annotationbottom1: TextView
        var annotationbottom2: TextView
        var finalimg: ImageView

        init {
            roundCard = itemView.findViewById(R.id.roundcard)
            stageCard = itemView.findViewById(R.id.stagecard)
            emptyCard = itemView.findViewById(R.id.emptycard)
            maincard = itemView.findViewById(R.id.maincard)
            leftTeam = itemView.findViewById(R.id.leftteam)
            rightTeam = itemView.findViewById(R.id.rightteam)
            finalCard = itemView.findViewById(R.id.finalcard)
            matchName = itemView.findViewById(R.id.matchname)
            team1Img = itemView.findViewById(R.id.team1img)
            team2Img = itemView.findViewById(R.id.team2img)
            team1 = itemView.findViewById(R.id.team1)
            team2 = itemView.findViewById(R.id.team2)
            finalimg = itemView.findViewById(R.id.finalimg)
            annotationtop = itemView.findViewById(R.id.annotationtop)
            annotationbottom = itemView.findViewById(R.id.annotationbottom)
            annotationtop1 = itemView.findViewById(R.id.annotationtop1)
            annotationtop2 = itemView.findViewById(R.id.annotationtop2)
            annotationbottom1 = itemView.findViewById(R.id.annotationbottom1)
            annotationbottom2 = itemView.findViewById(R.id.annotationbottom2)
        }
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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

