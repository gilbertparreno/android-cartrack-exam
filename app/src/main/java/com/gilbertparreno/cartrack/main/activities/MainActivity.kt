package com.gilbertparreno.cartrack.main.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gilbertparreno.cartrack.R
import com.gilbertparreno.cartrack.core.base.BaseFragment
import com.gilbertparreno.cartrack.core.extensions.addFragment
import com.gilbertparreno.cartrack.core.extensions.getFragmentTag
import com.gilbertparreno.cartrack.core.extensions.getLastFragmentTag
import com.gilbertparreno.cartrack.main.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.apply {
                beginTransaction()
                    .addFragment(
                        containerId = R.id.mainContainer,
                        fragmentClass = MainFragment::class.java,
                        addToBackStack = false
                    ).commit()
            }
        }
    }

    override fun onBackPressed() {
        val rootFragmentManager = supportFragmentManager
            .findFragmentByTag(getFragmentTag(MainFragment::class.java))
            ?.childFragmentManager
            ?: run {
                super.onBackPressed()
                return
            }
        if (rootFragmentManager.backStackEntryCount > 0) {
            var childFragment = rootFragmentManager.findFragmentByTag(
                rootFragmentManager.getLastFragmentTag()
            ) as? BaseFragment<*, *>
            while (childFragment?.onBackPressed() == false) {
                childFragment = childFragment.childFragmentManager
                    .findFragmentByTag(
                        childFragment.childFragmentManager.getLastFragmentTag()
                    ) as? BaseFragment<*, *>
            }
        } else {
            super.onBackPressed()
        }
    }
}