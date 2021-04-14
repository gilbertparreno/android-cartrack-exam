package com.gilbertparreno.cartrack.userDetails.fragments

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.gilbertparreno.cartrack.CartrackApplication
import com.gilbertparreno.cartrack.R
import com.gilbertparreno.cartrack.core.base.BaseFragment
import com.gilbertparreno.cartrack.core.extensions.runDelayed
import com.gilbertparreno.cartrack.core.extensions.showErrorSnackbar
import com.gilbertparreno.cartrack.main.entities.User
import com.gilbertparreno.cartrack.userDetails.viewModels.UserDetailsViewModel
import com.gilbertparreno.cartrack.userDetails.views.UserDetailsView
import com.gilbertparreno.cartrack.userDetails.views.UserDetailsViewDelegate
import kotlinx.android.synthetic.main.view_user_details.view.*

class UserDetailsFragment : BaseFragment<UserDetailsViewModel, UserDetailsView>(),
    UserDetailsViewDelegate {

    private lateinit var user: User

    override fun inject() {
        CartrackApplication.appComponent.inject(this)
    }

    override fun onCreateView(context: Context, savedInstanceState: Bundle?) =
        UserDetailsView(context).also {
            it.delegate = this
            (arguments?.getSerializable("user_details") as? User)?.let {
                user = it
            } ?: run {
                it.showErrorSnackbar(getString(R.string.generic_error))
                runDelayed(1200) {
                    parentFragmentManager.popBackStack()
                }
            }

            it.mapView.onCreate(savedInstanceState)
            lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                fun onResume() {
                    contentView.mapView.onResume()
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    contentView.mapView.onDestroy()
                    lifecycle.removeObserver(this)
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                fun onPause() {
                    contentView.mapView.onPause()
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_START)
                fun onStart() {
                    contentView.mapView.onStart()
                }
            })
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentView.mapView.onSaveInstanceState(outState)
    }

    override fun observerChanges() {

    }

    override fun onViewCreated(contentView: UserDetailsView, savedInstanceState: Bundle?) {
        if (!this::user.isInitialized) return
        contentView.setUserDetails(user)
    }

    // UserDetailsViewDelegate

    override fun onViewBackPressed() {

    }
}