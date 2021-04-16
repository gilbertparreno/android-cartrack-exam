package com.gilbertparreno.cartrack.core.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.gilbertparreno.cartrack.core.extensions.getLastFragmentTag

abstract class BaseActivity(
    @LayoutRes contentLayoutId: Int
) : AppCompatActivity(contentLayoutId) {

    open lateinit var rootFragmentTag: String

    override fun onBackPressed() {
        val rootFragmentManager = supportFragmentManager
            .findFragmentByTag(rootFragmentTag)
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