package com.main.limitless

import androidx.recyclerview.widget.RecyclerView
import com.main.limitless.MapsData.RoutesAdapter
import com.google.android.gms.maps.model.LatLng
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.main.limitless.databinding.BottomSheetRoutesBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class RouteBottomSheetFragment(private val routes: List<List<LatLng>>) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetRoutesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetRoutesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.routesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.routesRecyclerView.adapter = RoutesAdapter(routes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


