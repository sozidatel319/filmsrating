package ru.mikhailskiy.intensiv.ui.tvshows

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_tvshow.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.dto.MovieDTO

class TvShowItem (
    private val content: MovieDTO,
    private val onClick: (movie: MovieDTO) -> Unit
    ) : Item() {

        override fun getLayout() = R.layout.item_tvshow

        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.tvShowNameTextView.text = content.title
            //val rating = content.rating
            /*for (x in 1..5){
                val name = "star$x"
            }*/
           /* when(content.rating){

            }*//*.setOnClickListener {
                onClick.invoke(content)
            }*/
        }
    }
