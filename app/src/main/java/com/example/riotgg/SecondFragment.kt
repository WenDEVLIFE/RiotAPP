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

import com.example.riotgg.db.*

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

    // List of buttons that open URLs with character titles
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

    // List of save buttons corresponding to the above
    val saveButtonIds = listOf(
        R.id.button1save,
        R.id.button2save,
        R.id.button3save,
        R.id.button4save,
        R.id.button5save,
        R.id.button6save,
        R.id.button7save,
        R.id.button8save,
        R.id.button9save,
        R.id.button10save,
        R.id.button11save,
        R.id.button12save
    )

    // Setup click listeners to open URLs
    openButtons.forEach { (buttonId, url, _) ->
        view.findViewById<Button>(buttonId).setOnClickListener {
            openExternalLink(url)
        }
    }

    // Setup save buttons, update text if already saved
    saveButtonIds.forEachIndexed { index, saveButtonId ->
        val ( _, url, title) = openButtons[index]
        val button = view.findViewById<Button>(saveButtonId)

        // Set initial text depending on saved state
        if (dbHelper.isGuideSaved(url)) {
            button.text = getString(R.string.fragtwo_deletesave_button)
        } else {
            button.text = getString(R.string.fragtwo_save_button)
        }

        button.setOnClickListener {
            if (dbHelper.isGuideSaved(url)) {
                // If already saved, delete it
                val deleted = dbHelper.deleteGuide(url)
                if (deleted) {
                    button.text = getString(R.string.fragtwo_save_button)
                    Toast.makeText(requireContext(), "Guide deleted", Toast.LENGTH_SHORT).show()
                }
            } else {
                val saved = dbHelper.saveGuide(title, url)
                if (saved) {
                    button.text = getString(R.string.fragtwo_deletesave_button)
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
