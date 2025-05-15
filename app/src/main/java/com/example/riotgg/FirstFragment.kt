package com.example.riotgg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.denzcoskun.imageslider.constants.ScaleTypes

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        // Set up image sliders
        val imageSlider1 = view.findViewById<ImageSlider>(R.id.imageslider1)
        val imageList1 = arrayListOf(
            SlideModel(R.drawable.riot1),
            SlideModel(R.drawable.riot2),
            SlideModel(R.drawable.riot3),
            SlideModel(R.drawable.riot4),
            SlideModel(R.drawable.riot5)
        )
        imageSlider1.setImageList(imageList1, ScaleTypes.FIT)

        val imageSlider2 = view.findViewById<ImageSlider>(R.id.imageslider2)
        val imageList2 = arrayListOf(

            SlideModel(R.drawable.valo1),
            SlideModel(R.drawable.valo2),
            SlideModel(R.drawable.valo3)

        )
        imageSlider2.setImageList(imageList2, ScaleTypes.CENTER_INSIDE)

        val imageSlider3 = view.findViewById<ImageSlider>(R.id.imageslider3)
        val imageList3 = arrayListOf(

            SlideModel(R.drawable.league1),
            SlideModel(R.drawable.league2),
            SlideModel(R.drawable.league3)

        )
        imageSlider3.setImageList(imageList3, ScaleTypes.CENTER_INSIDE)


        val buttonvalo = view.findViewById<Button>(R.id.valoButton)


        buttonvalo.setOnClickListener {

            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, SecondFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val buttonleague = view.findViewById<Button>(R.id.leagueButton)
        buttonleague.setOnClickListener {

            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, ThirdFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }


        return view
    }
}
