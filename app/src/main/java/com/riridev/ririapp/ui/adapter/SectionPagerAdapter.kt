package com.riridev.ririapp.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.riridev.ririapp.ui.history.DoneFragment
import com.riridev.ririapp.ui.history.ProcessFragment
import com.riridev.ririapp.ui.history.VerificationFragment

class SectionPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = VerificationFragment()
            1 -> fragment = ProcessFragment()
            2 -> fragment = DoneFragment()
        }
        return fragment as Fragment
    }

}