package com.gilbertparreno.cartrack.main.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.gilbertparreno.cartrack.R
import com.gilbertparreno.cartrack.core.base.BaseRecyclerViewAdapter
import com.gilbertparreno.cartrack.core.extensions.inflate
import com.gilbertparreno.cartrack.core.extensions.setDebounceClickListener
import com.gilbertparreno.cartrack.main.adapters.callbacks.UsersListCallback
import com.gilbertparreno.cartrack.main.entities.User
import kotlinx.android.synthetic.main.item_user.view.*

class UsersListAdapter(
    override val items: MutableList<User> = mutableListOf(),
    private val onItemClicked: ((user: User) -> Unit)? = null
) : BaseRecyclerViewAdapter<User>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_user))
    }

    override fun onBindViewHolder(view: View, item: User, position: Int) {
        with(view) {
            name.text = item.name
            username.text = item.username
            email.text = item.email
            address.text = item.address
            website.text = item.website

            setDebounceClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }

    fun setItems(items: List<User>) {
        val callback = DiffUtil.calculateDiff(UsersListCallback(this.items, items))
        this.items.clear()
        this.items.addAll(items)
        callback.dispatchUpdatesTo(this)
    }
}