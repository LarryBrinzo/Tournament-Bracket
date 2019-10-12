package com.bracket.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bracket.Models.Annotations
import com.bracket.Models.Rows
import com.bracket.R

class RowsAdapter(private val rows: List<Rows>, private val annotations: List<Annotations>, private val cardWidth: Int, private val Width: Int, private val context: Context?) :
    RecyclerView.Adapter<RowsAdapter.MyHolder>() {

    private lateinit var rowsElementsAdapter: RowElemetsAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.single_row_card, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.rowElemetsRecycle.isNestedScrollingEnabled = false

        rowsElementsAdapter = RowElemetsAdapter(rows[position].items[0], annotations, cardWidth, Width, context)
        val recycerows = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.rowElemetsRecycle.layoutManager = recycerows
        holder.rowElemetsRecycle.adapter = rowsElementsAdapter

    }

    override fun getItemCount(): Int {
        var arr = 0
        try {
            arr = if (rows.isEmpty()) {
                0
            } else {
                rows.size
            }
        }
        catch (ignored: Exception) { }
        return arr
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rowElemetsRecycle: RecyclerView = itemView.findViewById(R.id.rowelementsrecycle)

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}



