package com.osenov.rickandmorty.util

import androidx.fragment.app.Fragment
import com.osenov.rickandmorty.data.model.Character
import kotlin.properties.ReadOnlyProperty

fun listStringsArgs(key: String): ReadOnlyProperty<Fragment, ArrayList<String>> {
    return ReadOnlyProperty { thisRef, _ ->
        val args = thisRef.requireArguments()
        require(args.containsKey(key)) { "Arguments don't contain key '$key'" }
        requireNotNull(args.getStringArrayList(key))
    }
}

fun characterArgs(key: String): ReadOnlyProperty<Fragment, Character> {
    return ReadOnlyProperty { thisRef, _ ->
        val args = thisRef.requireArguments()
        require(args.containsKey(key)) { "Arguments don't contain key '$key'" }
        requireNotNull(args.getParcelable(key))
    }
}
