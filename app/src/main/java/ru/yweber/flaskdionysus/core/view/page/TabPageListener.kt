package ru.yweber.flaskdionysus.core.view.page

import com.google.android.material.tabs.TabLayout

/**
 * Created on 10.04.2020
 * @author YWeber */

class TabPageListener(val selectPosition: (position: Int) -> Unit) : TabLayout.OnTabSelectedListener {
    override fun onTabReselected(tab: TabLayout.Tab) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        selectPosition(tab.position)
    }
}