package com.example.limitless.MapsData

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.limitless.R
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import android.graphics.Path


class RoutesAdapter(private val routes: List<List<LatLng>>) : RecyclerView.Adapter<RoutesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val routeImageView: ImageView = view.findViewById(R.id.routeImageView)
        val routeTextView: TextView = view.findViewById(R.id.routeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.route_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val route = routes[position]

        val routeBitmap = generateRouteBitmap(route)
        holder.routeImageView.setImageBitmap(routeBitmap)
        holder.routeTextView.text = "Route ${position + 1}"
    }

    override fun getItemCount() = routes.size

    private fun generateRouteBitmap(route: List<LatLng>): Bitmap {
        val width = 800
        val height = 1800
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.color = Color.BLUE
        paint.strokeWidth = 5f

        val bounds = LatLngBounds.Builder().apply {
            route.forEach { include(it) }
        }.build()

        val latSpan = bounds.northeast.latitude - bounds.southwest.latitude
        val lngSpan = bounds.northeast.longitude - bounds.southwest.longitude

        val xScale = width / lngSpan
        val yScale = height / latSpan

        val zoomOutFactor = 0.5
        val scale = minOf(xScale, yScale) * zoomOutFactor

        val path = Path()
        route.forEachIndexed { index, latLng ->
            val point = latLngToPoint(latLng, bounds, width, height, scale)
            if (index == 0) {
                path.moveTo(point.x.toFloat(), point.y.toFloat())
            } else {
                path.lineTo(point.x.toFloat(), point.y.toFloat())
            }
        }
        canvas.drawPath(path, paint)
        return bitmap
    }

    private fun latLngToPoint(latLng: LatLng, bounds: LatLngBounds, width: Int, height: Int, scale: Double): Point {
        val nw = bounds.northeast
        val se = bounds.southwest
        val x = ((latLng.longitude - se.longitude) * scale).toInt()
        val y = height - ((latLng.latitude - se.latitude) * scale).toInt()
        return Point(x, y)
    }
}

