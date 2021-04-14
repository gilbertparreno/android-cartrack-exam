package com.gilbertparreno.cartrack.core.extensions

import androidx.fragment.app.FragmentManager

fun FragmentManager.getLastFragmentTag() : String? {
    return fragments.lastOrNull()?.tag
}