package com.aob.android.miwokvkotlin

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CategoryAdapter(context: Context, fm: FragmentManager, behavior: Int) :
        FragmentPagerAdapter(fm, behavior) {

    final val PAGE_COUNT : Int = 4
    private var mContext = context



    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return NumbersFragment()
            1 -> return FamilyFragment()
            2 -> return ColorsFragment()
        }
        return PhrasesFragment()
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return mContext.getString(R.string.category_numbers)
            1 -> return mContext.getString(R.string.category_family)
            2 -> return mContext.getString(R.string.category_colors)
        }
        return mContext.getString(R.string.category_phrases)
    }


}