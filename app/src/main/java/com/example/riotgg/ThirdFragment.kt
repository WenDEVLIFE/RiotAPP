package com.example.riotgg

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class ThirdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_third, container, false)

        // List of buttons and corresponding URLs
        val buttons = listOf(
            Pair(R.id.button1, "https://youtu.be/ANzhr4QFRdQ?si=f0laFXbi2L1hmVN2"),  // League Video 1
            Pair(R.id.button2, "https://youtu.be/-nCVD1_dyLc?si=gbJCI6r6y06mwK9k"),  // League Video 2
            Pair(R.id.button3, "https://youtu.be/SxSsAPZBNJo?si=Ac_m1C6XF-MgPZY4")   // League Video 3
        )

        // Assign click listeners dynamically
        buttons.forEach { (buttonId, url) ->
            view.findViewById<Button>(buttonId).setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }

        return view
    }
}
