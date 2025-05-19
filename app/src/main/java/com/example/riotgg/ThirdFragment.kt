package com.example.riotgg

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.app.AlertDialog
import android.widget.Toast
import com.example.riotgg.db.*
import android.widget.ImageView

class ThirdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_third, container, false)
        val dbHelper = SavedGuideDbHelper(requireContext())
        
        // List of buttons and corresponding URLs
        val buttons = listOf(
    Triple(R.id.button1, "https://youtu.be/ANzhr4QFRdQ?si=f0laFXbi2L1hmVN2", "Garen"),
    Triple(R.id.button2, "https://youtu.be/-nCVD1_dyLc?si=gbJCI6r6y06mwK9k", "Riven"),
    Triple(R.id.button3, "https://youtu.be/SxSsAPZBNJo?si=Ac_m1C6XF-MgPZY4", "Darius"),
    Triple(R.id.button4, "https://youtu.be/example4", "Master Yi"),
    Triple(R.id.button5, "https://youtu.be/example5", "Nunu"),
    Triple(R.id.button6, "https://youtu.be/example6", "Viego"),
    Triple(R.id.button7, "https://youtu.be/example7", "LeBlanc"),
    Triple(R.id.button8, "https://youtu.be/example8", "Katarina"),
    Triple(R.id.button9, "https://youtu.be/example9", "Qiyanna"),
    Triple(R.id.button10, "https://youtu.be/example10", "Jinx"),
    Triple(R.id.button11, "https://youtu.be/example11", "Miss Fortune"),
    Triple(R.id.button12, "https://youtu.be/example12", "Kai'sa"),
    Triple(R.id.button13, "https://youtu.be/example13", "Blitzcrank"),
    Triple(R.id.button14, "https://youtu.be/example14", "Renata Glasc"),
    Triple(R.id.button15, "https://youtu.be/example14", "Yuumi")
)

        
        val saveImageIds = listOf(
    R.id.saveg1,
    R.id.saveg2,
    R.id.saveg3,
    R.id.saveg4,
    R.id.saveg5,
    R.id.saveg6,
    R.id.saveg7,
    R.id.saveg8,
    R.id.saveg9,
    R.id.saveg10,
    R.id.saveg11,
    R.id.saveg12,
    R.id.saveg13,
    R.id.saveg14,
    R.id.saveg15
)

        // Assign click listeners dynamically
        buttons.forEach { (buttonId, url) ->
            view.findViewById<Button>(buttonId).setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }
        
        saveImageIds.forEachIndexed { index, imageViewId ->
        val ( _, url, title ) = buttons[index]
        val imageView = view.findViewById<ImageView>(imageViewId)

        imageView.setImageResource(
            if (dbHelper.isGuideSaved(url)) R.drawable.ic_saved else R.drawable.ic_unsave
        )

        imageView.setOnClickListener {
            if (dbHelper.isGuideSaved(url)) {
                val deleted = dbHelper.deleteGuide(url)
                if (deleted) {
                    imageView.setImageResource(R.drawable.ic_unsave)
                    Toast.makeText(requireContext(), "Guide deleted", Toast.LENGTH_SHORT).show()
                }
            } else {
                val saved = dbHelper.saveGuide(title, url)
                if (saved) {
                    imageView.setImageResource(R.drawable.ic_saved)
                    Toast.makeText(requireContext(), "Guide saved", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Already saved", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

        return view
    }
}
