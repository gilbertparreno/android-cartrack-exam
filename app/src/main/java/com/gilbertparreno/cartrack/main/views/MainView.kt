package com.gilbertparreno.cartrack.main.views

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.gilbertparreno.cartrack.R
import com.gilbertparreno.cartrack.core.base.BaseFragmentView
import com.gilbertparreno.cartrack.main.adapters.UsersListAdapter
import com.gilbertparreno.cartrack.main.entities.User
import kotlinx.android.synthetic.main.view_main.view.*

interface MainViewDelegate {
    fun onItemClicked(user: User)
}

class MainView(context: Context) : BaseFragmentView(context) {

    var delegate: MainViewDelegate? = null

    private val adapter = UsersListAdapter { user ->
        delegate?.onItemClicked(user)
    }

    init {
        inflate(context, R.layout.view_main, this)
        with(usersList) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MainView.adapter
        }
    }

    fun setItems(items: List<User>) {
        adapter.setItems(items)
    }
}