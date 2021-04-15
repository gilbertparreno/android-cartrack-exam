package com.gilbertparreno.cartrack.main.views

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gilbertparreno.cartrack.R
import com.gilbertparreno.cartrack.core.base.BaseFragmentView
import com.gilbertparreno.cartrack.main.adapters.UsersListAdapter
import com.gilbertparreno.cartrack.main.entities.User
import kotlinx.android.synthetic.main.view_main.view.*

interface MainViewDelegate {
    fun onItemClicked(user: User)
    fun onSwipeRefreshed()
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
        swipeRefreshLayout.setOnRefreshListener {
            delegate?.onSwipeRefreshed()
        }
    }

    fun setItems(items: List<User>) {
        adapter.setItems(items)
        if (items.isEmpty()) {
            emptyListTextView.visibility = View.VISIBLE
            usersList.visibility = View.GONE
        } else {
            emptyListTextView.visibility = View.GONE
            usersList.visibility = View.VISIBLE
        }
    }

    fun showSwipeRefreshProgress() {
        if (!swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = true
        }
    }

    fun hideSwipeRefreshProgress() {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
    }
}