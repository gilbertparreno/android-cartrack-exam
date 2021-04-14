package com.gilbertparreno.cartrack.core.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import java.util.*

fun FragmentTransaction.addFragment(
    @IdRes containerId: Int,
    fragmentClass: Class<out Fragment>,
    bundle: Bundle? = null,
    tag: String? = fragmentClass.simpleName.toLowerCase(Locale.US),
    addToBackStack: Boolean = true
): FragmentTransaction {
    if (addToBackStack) {
        this.addToBackStack(tag)
    }
    return this.add(
        containerId,
        fragmentClass,
        bundle,
        tag
    )
}

fun FragmentTransaction.replaceFragment(
    @IdRes containerId: Int,
    fragmentClass: Class<out Fragment>,
    bundle: Bundle? = null,
    tag: String? = fragmentClass.simpleName.toLowerCase(Locale.US),
    addToBackStack: Boolean = true
): FragmentTransaction {
    if (addToBackStack) {
        this.addToBackStack(tag)
    }
    return this.replace(
        containerId,
        fragmentClass,
        bundle,
        tag
    )
}