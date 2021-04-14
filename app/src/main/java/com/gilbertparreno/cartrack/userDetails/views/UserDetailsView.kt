package com.gilbertparreno.cartrack.userDetails.views

import android.content.Context
import com.gilbertparreno.cartrack.R
import com.gilbertparreno.cartrack.core.base.BaseFragmentView
import com.gilbertparreno.cartrack.main.entities.User
import kotlinx.android.synthetic.main.item_user.view.address
import kotlinx.android.synthetic.main.item_user.view.email
import kotlinx.android.synthetic.main.item_user.view.name
import kotlinx.android.synthetic.main.item_user.view.username
import kotlinx.android.synthetic.main.item_user.view.website
import kotlinx.android.synthetic.main.view_user_details.view.*
import timber.log.Timber

interface UserDetailsViewDelegate {
    fun onViewBackPressed()
}

class UserDetailsView(context: Context) : BaseFragmentView(context) {

    var delegate: UserDetailsViewDelegate? = null

    init {
        inflate(context, R.layout.view_user_details, this)
        mapView.getMapAsync {
            Timber.d("")
        }
    }

    fun setUserDetails(user: User) {
        name.text = user.name
        username.text = user.username
        email.text = user.email
        address.text = user.address
        website.text = user.website
        phone.text = user.phone
    }
}