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

        val buttons = listOf(
            Pair(R.id.button1, "https://youtu.be/enmsXOKyhi4?si=ruXEZJSwt-dM8Vfx"),  // Jett
            Pair(R.id.button2, "https://youtu.be/ohs4xDdumbc?si=mlGAyEnLSgwSOuWr"),  // Neon
            Pair(R.id.button3, "https://youtu.be/AMaEwrfJt0o?si=Up13Q5BIzAKg5VRg"),  // Reyna
            Pair(R.id.button4, "https://youtu.be/8q26gGOgpKA?si=d8APbWQ9b4QWqGOc"),  // Fade
            Pair(R.id.button5, "https://youtu.be/RSzBwuCrP-o?si=KNmLplOsFZgEr7xB"),  // Kay/O
            Pair(R.id.button6, "https://youtu.be/ECbC7RfsKRU?si=bHBmCVbJg1p9dKLv"),  // Gekko
            Pair(R.id.button7, "https://youtu.be/wK0RPVsrsS8?si=XbNVB_DKvfIdzgsI"),  // Sage
            Pair(R.id.button8, "https://youtu.be/vFuAYnPyEGY?si=PCbOWreDRebS-jUN"),  // Killjoy
            Pair(R.id.button9, "https://youtu.be/3YqPTocB3NM?si=9LMP4VEWHCGO7rnV"),  // Cypher
            Pair(R.id.button10, "https://youtu.be/NPSzKX8dYZo?si=XeMjXubxn9QjM8RT"), // Harbor
            Pair(R.id.button11, "https://youtu.be/UxX_xe0PExA?si=8mWFlkM_5u8plnqZ"), // Viper
            Pair(R.id.button12, "https://youtu.be/V2f4jaw0PfY?si=JDs-jx8Awx-v1SKM")  // Astra
        )

        buttons.forEach { (buttonId, url) ->
            view.findViewById<Button>(buttonId).setOnClickListener {
                openExternalLink(url)
            }
        }

        return view
    }
}
