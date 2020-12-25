package ru.mikhailskiy.intensiv.ui.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.koin.core.component.KoinApiExtension
import ru.mikhailskiy.intensiv.ui.watchlist.WatchlistFragment

class ProfileAdapter(fragment: Fragment, private val itemsCount: Int) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    @KoinApiExtension
    override fun createFragment(position: Int): Fragment {
        return WatchlistFragment.newInstance()
    }
}