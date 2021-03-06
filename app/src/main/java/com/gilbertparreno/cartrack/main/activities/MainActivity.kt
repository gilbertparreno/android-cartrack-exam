package com.gilbertparreno.cartrack.main.activities

import android.os.Bundle
import com.gilbertparreno.cartrack.R
import com.gilbertparreno.cartrack.core.base.BaseActivity
import com.gilbertparreno.cartrack.core.extensions.addFragment
import com.gilbertparreno.cartrack.core.extensions.getFragmentTag
import com.gilbertparreno.cartrack.main.fragments.MainFragment

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.apply {
                beginTransaction()
                    .addFragment(
                        containerId = R.id.mainContainer,
                        fragmentClass = MainFragment::class.java,
                        addToBackStack = false
                    ).commit()
            }
            rootFragmentTag = getFragmentTag(MainFragment::class.java)
        }
    }
}