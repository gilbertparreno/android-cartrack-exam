package com.gilbertparreno.cartrack.main.fragments

import android.content.Context
import android.os.Bundle
import com.gilbertparreno.cartrack.CartrackApplication
import com.gilbertparreno.cartrack.R
import com.gilbertparreno.cartrack.core.base.BaseFragment
import com.gilbertparreno.cartrack.core.extensions.addFragment
import com.gilbertparreno.cartrack.core.extensions.showErrorSnackbar
import com.gilbertparreno.cartrack.core.networking.taskStatus.TaskStatus
import com.gilbertparreno.cartrack.main.entities.User
import com.gilbertparreno.cartrack.main.viewModels.MainViewModel
import com.gilbertparreno.cartrack.main.views.MainView
import com.gilbertparreno.cartrack.main.views.MainViewDelegate
import com.gilbertparreno.cartrack.userDetails.fragments.UserDetailsFragment

class MainFragment : BaseFragment<MainViewModel, MainView>(), MainViewDelegate {

    override fun inject() {
        CartrackApplication.appComponent.inject(this)
    }

    override fun onCreateView(context: Context, savedInstanceState: Bundle?) = MainView(context).also {
        it.delegate = this
    }

    override fun onViewCreated(contentView: MainView, savedInstanceState: Bundle?) {
        viewModel.getUsers()
    }

    override fun observerChanges() {
        viewModel.usersStatus.observe(this) {
            when (it) {
                is TaskStatus.Loading -> {
                    contentView.showSwipeRefreshProgress()
                }
                is TaskStatus.SuccessWithResult -> {
                    contentView.setItems(it.result)
                    contentView.hideSwipeRefreshProgress()
                }
                is TaskStatus.Failure -> {
                    contentView.showErrorSnackbar(it.error.message ?: getString(R.string.unknown_error))
                    contentView.hideSwipeRefreshProgress()
                }
            }
        }
    }

    // MainViewDelegate

    override fun onItemClicked(user: User) {
        childFragmentManager.beginTransaction().addFragment(
            containerId = R.id.mainFragmentContainer,
            fragmentClass = UserDetailsFragment::class.java,
            bundle = Bundle().also { it.putSerializable("user_details", user) },
            addToBackStack = true
        ).commit()
    }

    override fun onSwipeRefreshed() {
        viewModel.getUsers()
    }
}