package com.gilbertparreno.cartrack.authentication.activities

import android.os.Bundle
import com.gilbertparreno.cartrack.R
import com.gilbertparreno.cartrack.authentication.fragments.LoginFragment
import com.gilbertparreno.cartrack.core.base.BaseActivity
import com.gilbertparreno.cartrack.core.extensions.addFragment
import com.gilbertparreno.cartrack.core.extensions.getFragmentTag

class AuthenticationActivity : BaseActivity(R.layout.activity_authentication) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.apply {
                beginTransaction()
                    .addFragment(
                        containerId = R.id.loginContainer,
                        fragmentClass = LoginFragment::class.java,
                        addToBackStack = false
                    ).commit()
            }
            rootFragmentTag = getFragmentTag(LoginFragment::class.java)
        }
    }
}