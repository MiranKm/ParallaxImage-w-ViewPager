package project.miran.com.parallaxphoto

import android.os.Bundle


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), ViewPager.PageTransformer, ViewPager.OnPageChangeListener {


    private lateinit var mPager: ViewPager
    private lateinit var pagerAdapter: ScreenSlidePagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPager = findViewById(R.id.viewpager)

        mPager.addOnPageChangeListener(this)
        mPager.setPageTransformer(false, this)

        pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)

        val black = BlackFragment()
        val white = WhiteFragment()



        pagerAdapter.addFragments("Black", black)
        pagerAdapter.addFragments("White", white)
        mPager.adapter = pagerAdapter
    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            mPager.currentItem = mPager.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        val fragments = arrayListOf<Fragment>()
        val titles = arrayListOf<String>()

        override fun getCount(): Int = fragments.size

        override fun getItem(position: Int): Fragment = fragments.get(position)

        fun addFragments(pTitle: String, pFragment: Fragment) {
            fragments.add(pFragment)
            titles.add(pTitle)
        }
    }

    override fun transformPage(p0: View, p1: Float) {
        val pageWidth = p0.width
        when {
            p1 < -1 -> view.alpha = 0f
            p1 <= 1 -> { }

            else -> view.alpha = 0f
        }
    }

    override fun onPageScrollStateChanged(p0: Int) {
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
        val x = ((mPager.width * p0 + p2) * computeFactor())
        scrollView.scrollTo(x.toInt() + image.width / 3, 0)
    }

    override fun onPageSelected(p0: Int) {
    }

    private fun computeFactor(): Float {
        return (image.width / 2 - mPager.width) / (mPager.width *
                pagerAdapter.count - 1).toFloat()
    }

}
