package com.aob.android.miwokvkotlin

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.util.*

class WordAdapter(context: Activity, words: ArrayList<Word>, colourResourceId: Int) : ArrayAdapter<Word>(context, 0, words)  {

    private var mColorResourceId: Int = colourResourceId

    /*val mColorResourceId: Int
        get() = _mColorResourceId*/

    // Here we call "ArrayAdapter" constructor which contains


    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     * list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // Check if the existing view is being reused, otherwise inflate the view
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_custom, parent, false)
        }

        val currentWord: Word? = getItem(position)

        // Find the reference id of miwok translation from list_item_custom

        // Find the reference id of miwok translation from list_item_custom
        val miwokTextView = listItemView!!.findViewById<TextView>(R.id
                .miwok_text_view)
        miwokTextView.text = currentWord?.miwokTranslation

        // Find the reference id of the english translation from
        // list_item_custom
        val defaultTextView = listItemView.findViewById<TextView>(R.id
        .default_text_view)
        defaultTextView.text = currentWord?.defaultTranslation

        // Setting the english_text_view to current item in list from objects
        // in "Word" class using the "getDefaultTranslation()".
        //defaultTextView.setText(currentWord.getDefaultTranslation())

        // Find the reference id of the icon from list_item_custom
        val iconView = listItemView.findViewById<ImageView>(R.id.list_item_icon)

        // Setting the list_item_icon to current item in list from objects
        // in "Word" class using the "getImageResourceId()".
        if (currentWord != null) {
            if (currentWord.hasImage()) {
                iconView.setImageResource(currentWord.imageResourceId)
                iconView.visibility = View.VISIBLE
            } else {
                iconView.visibility = View.GONE
            }
        }

        // Set the theme color for the list item
        val textContainer = listItemView.findViewById<LinearLayout>(R.id.text_container)

        // Find the color that the resource ID maps to
        val color = ContextCompat.getColor(context, mColorResourceId)

        // Set the background color of the text container View
        textContainer.setBackgroundColor(color)

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView
    }

}