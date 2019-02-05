package mvp.implement.kotlin.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.View
import mvp.implement.kotlin.view.fragment.Step1Fragment
import mvp.implement.kotlin.view.fragment.Step2Fragment
import mvp.implement.kotlin.view.fragment.Step3Fragment
import mvp.implement.kotlin.view.fragment.Step4Fragment










interface StepsListener {

    fun gotToStep1()
    fun gotToStep2()
    fun gotToStep3()
    fun gotToStep4()
    fun save(andNew: Boolean)
}

class StepsPageAdapter (fragmentManager: FragmentManager, listener: StepsListener) : FragmentStatePagerAdapter(fragmentManager){


    val listener: StepsListener = listener

    override fun getItem(position: Int): Fragment {

        var fragment: Fragment =  when(position) {
            0 -> Step1Fragment.newInstance(listener)
            1 -> Step2Fragment.newInstance(listener)
            2 -> Step3Fragment.newInstance(listener)
            3 -> Step4Fragment.newInstance(listener)
            else -> Step1Fragment.newInstance(listener)
        }

        return fragment
    }

    override fun getCount(): Int {
        return 4
    }


    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}