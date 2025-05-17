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

class SecondFragment : Fragment() {

    private fun openExternalLink(url: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("External Link")
        alertDialogBuilder.setMessage("This link leads to an external app. Continue?")

        alertDialogBuilder.setPositiveButton("Yes") { dialog, _ ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            dialog.dismiss()
        }

        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    val view = inflater.inflate(R.layout.fragment_second, container, false)
    val dbHelper = SavedGuideDbHelper(requireContext())

    // Buttons to open YouTube guides
    val openButtons = listOf(
        Triple(R.id.button1, "https://youtu.be/enmsXOKyhi4?si=ruXEZJSwt-dM8Vfx", "Jett"),  
        Triple(R.id.button2, "https://youtu.be/ohs4xDdumbc?si=mlGAyEnLSgwSOuWr", "Neon"),  
        Triple(R.id.button3, "https://youtu.be/AMaEwrfJt0o?si=Up13Q5BIzAKg5VRg", "Reyna"),  
        Triple(R.id.button4, "https://youtu.be/8q26gGOgpKA?si=d8APbWQ9b4QWqGOc", "Fade"),  
        Triple(R.id.button5, "https://youtu.be/RSzBwuCrP-o?si=KNmLplOsFZgEr7xB", "Kay/O"),  
        Triple(R.id.button6, "https://youtu.be/ECbC7RfsKRU?si=bHBmCVbJg1p9dKLv", "Gekko"),  
        Triple(R.id.button7, "https://youtu.be/wK0RPVsrsS8?si=XbNVB_DKvfIdzgsI", "Sage"),  
        Triple(R.id.button8, "https://youtu.be/vFuAYnPyEGY?si=PCbOWreDRebS-jUN", "Killjoy"),  
        Triple(R.id.button9, "https://youtu.be/3YqPTocB3NM?si=9LMP4VEWHCGO7rnV", "Cypher"),  
        Triple(R.id.button10, "https://youtu.be/NPSzKX8dYZo?si=XeMjXubxn9QjM8RT", "Harbor"), 
        Triple(R.id.button11, "https://youtu.be/UxX_xe0PExA?si=8mWFlkM_5u8plnqZ", "Viper"), 
        Triple(R.id.button12, "https://youtu.be/V2f4jaw0PfY?si=JDs-jx8Awx-v1SKM", "Astra")  
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
    R.id.saveg12
)

    openButtons.forEach { (buttonId, url, _) ->
        view.findViewById<Button>(buttonId).setOnClickListener {
            openExternalLink(url)
        }
    }

    saveImageIds.forEachIndexed { index, imageViewId ->
        val ( _, url, title ) = openButtons[index]
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
