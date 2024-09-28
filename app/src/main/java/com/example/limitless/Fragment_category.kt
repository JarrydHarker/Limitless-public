package com.example.limitless

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.limitless.Exercise.Add_Exercise

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_category.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_category : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_category, container, false)

        val cat1: CardView = view.findViewById(R.id.cat1)
        val cat2: CardView = view.findViewById(R.id.cat2)
        val cat3: CardView = view.findViewById(R.id.cat3)
        val cat4: CardView = view.findViewById(R.id.cat4)
        val cat5: CardView = view.findViewById(R.id.cat5)
        val cat6: CardView = view.findViewById(R.id.cat6)
        val cat7: CardView = view.findViewById(R.id.cat7)


        cat1.setOnClickListener{
            val intent = Intent(requireActivity(), Map_Activity::class.java)
            startActivity(intent)
        }
        cat2.setOnClickListener{
            val intent = Intent(requireActivity(), Add_Exercise::class.java)
            startActivity(intent)
        }
        cat3.setOnClickListener{
            val intent = Intent(requireActivity(), Add_Exercise::class.java)
            startActivity(intent)
        }
        cat4.setOnClickListener{
            val intent = Intent(requireActivity(), Add_Exercise::class.java)
            startActivity(intent)
        }
        cat5.setOnClickListener{
            val intent = Intent(requireActivity(), Add_Exercise::class.java)
            startActivity(intent)
        }
        cat6.setOnClickListener {
            val intent = Intent(requireActivity(), Add_Exercise::class.java)
            startActivity(intent)
        }
        cat7.setOnClickListener{
            val intent = Intent(requireActivity(), Add_Exercise::class.java)
            startActivity(intent)
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment_category.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_category().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}