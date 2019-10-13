package com.bracket.Models

import android.content.Context
import com.bracket.Contracts.BracketInterface
import com.google.gson.Gson
import java.io.InputStream

class BracketActivityModel(private val context: Context) : BracketInterface.Model{

//    val finalsGrid: FinalsGridEndpoint = FinalsGridApi.getFinalsGrid()!!.create(
//        FinalsGridEndpoint::class.java)
//
//    val call: Call<GridDataClass> = finalsGrid.getFinalGrid()
//
//    val response = call.execute()
//    val finalGridsValues: GridDataClass? = response.body()

    private val f = readJSONFromAsset()
    private val gson: Gson = Gson()
    private val finalGridsValues: GridDataClass = gson.fromJson(f, GridDataClass::class.java)

    override fun getWinningTeam(): Int {
        val rowSize= finalGridsValues.c.finalsGrids[0].rows.size
        return finalGridsValues.c.finalsGrids[0].rows[rowSize-1].items[0][0].winnerTeamID
    }

    override fun getRows(): List<Rows> {
        return finalGridsValues.c.finalsGrids[0].rows
    }

    override fun getAnnotations(): List<Annotations> {
        return finalGridsValues.c.finalsGrids[0].annotations
    }

    override fun getConnections(): List<Connections> {
        return finalGridsValues.c.finalsGrids[0].connections
    }

    private fun readJSONFromAsset(): String? {
        val json: String?
        try {
            val  inputStream: InputStream = context.assets.open("bracket.json")
            json = inputStream.bufferedReader().use{it.readText()}
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }

}