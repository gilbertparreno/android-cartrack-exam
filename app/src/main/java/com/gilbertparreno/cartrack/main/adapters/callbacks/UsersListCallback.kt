package com.gilbertparreno.cartrack.main.adapters.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.gilbertparreno.cartrack.core.extensions.allTrue
import com.gilbertparreno.cartrack.main.entities.User

class UsersListCallback(
    private val oldList: List<User>,
    private val newList: List<User>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return allTrue(
            oldList[oldItemPosition].name == newList[newItemPosition].name,
            oldList[oldItemPosition].email == newList[newItemPosition].email,
            oldList[oldItemPosition].address == newList[newItemPosition].address,
            oldList[oldItemPosition].company == newList[newItemPosition].company
        )
    }
}