package org.bedu.okhttpexample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class PlanetAdapter(private val context: Context,
                    private val planetas: ArrayList<Planet>): BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val planetView = inflater.inflate(R.layout.item_planet, parent, false)

        val tvPlanet = planetView.findViewById<View>(R.id.tvPlanet) as TextView
        val tvTerrain = planetView.findViewById<View>(R.id.tvTerrain) as TextView

        val planet = getItem(position) as Planet

        tvPlanet.text = planet.name
        tvTerrain.text = planet.terrain

        return planetView
    }

    override fun getItem(position: Int): Any {
        return planetas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return planetas.size
    }
}