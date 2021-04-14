package com.gilbertparreno.cartrack.userDetails.views

import android.content.Context
import com.gilbertparreno.cartrack.R
import com.gilbertparreno.cartrack.core.base.BaseFragmentView
import com.gilbertparreno.cartrack.core.extensions.runDelayed
import com.gilbertparreno.cartrack.main.entities.User
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.item_user.view.address
import kotlinx.android.synthetic.main.item_user.view.email
import kotlinx.android.synthetic.main.item_user.view.name
import kotlinx.android.synthetic.main.item_user.view.username
import kotlinx.android.synthetic.main.item_user.view.website
import kotlinx.android.synthetic.main.view_app_bar.view.*
import kotlinx.android.synthetic.main.view_user_details.view.*

interface UserDetailsViewDelegate {
    fun onViewBackPressed()
}

class UserDetailsView(context: Context) : BaseFragmentView(context) {

    var delegate: UserDetailsViewDelegate? = null

    private var user: User? = null
    private lateinit var googleMap: GoogleMap

    init {
        inflate(context, R.layout.view_user_details, this)
        mapView.getMapAsync { googleMap ->
            this.googleMap = googleMap
            googleMap.uiSettings.isZoomControlsEnabled = true
            showUserMarker()
        }
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_back)
        toolbar.setNavigationOnClickListener {
            delegate?.onViewBackPressed()
        }
    }

    fun setUserDetails(user: User) {
        this.user = user
        name.text = user.name
        username.text = user.username
        email.text = user.email
        address.text = user.address
        website.text = user.website
        phone.text = user.phone
    }

    private fun showUserMarker() {
        user?.let { user ->
            runDelayed(300) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(user.latLng, 8f))
                MarkerOptions().apply {
                    position(user.latLng)
                    title(user.company)
                    snippet(user.address)
                    googleMap.addMarker(this)
                }
            }
        }
    }
}