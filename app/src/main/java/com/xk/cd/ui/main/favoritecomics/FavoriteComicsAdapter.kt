package com.xk.cd.ui.main.favoritecomics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.xk.cd.R
import com.xk.cd.common.extensions.getLayoutInflater
import com.xk.cd.data.models.comic.Comic
import com.xk.cd.databinding.ItemFavoriteComicBinding


typealias OnComicClickListener = ((comic: Comic) -> Unit)?
typealias OnDeleteComicClickListener = ((comic: Comic) -> Unit)?

class FavoriteComicsAdapter(
    private val onItemClickListener: OnComicClickListener = null,
    private val onDeleteComicClickListener: OnDeleteComicClickListener = null
) : RecyclerView.Adapter<FavoriteComicsVH>() {

    private val comics: MutableList<Comic> by lazy { ArrayList<Comic>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteComicsVH {
        val inflater = parent.context.getLayoutInflater()
        return FavoriteComicsVH.create(
            parent,
            inflater,
            onItemClickListener,
            onDeleteComicClickListener
        )
    }

    override fun getItemCount(): Int = comics.size

    override fun onBindViewHolder(holder: FavoriteComicsVH, position: Int) {
        holder.setIsRecyclable(false)
        holder.bind(comics[position])
    }

    fun setData(items: List<Comic>) {
        comics.clear()
        comics.addAll(items)
        notifyDataSetChanged()
    }
}

class FavoriteComicsVH private constructor(
    private val binding: ItemFavoriteComicBinding,
    private val onItemClickListener: OnComicClickListener,
    private val onDeleteComicClickListener: OnDeleteComicClickListener
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(
            parent: ViewGroup,
            inflater: LayoutInflater,
            onItemClickListener: OnComicClickListener,
            onDeleteComicClickListener: OnDeleteComicClickListener
        ): FavoriteComicsVH {

            val binding: ItemFavoriteComicBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_favorite_comic,
                parent,
                false
            )
            return FavoriteComicsVH(binding, onItemClickListener, onDeleteComicClickListener)
        }
    }

    fun bind(comic: Comic) = with(binding) {
        binding.comic = comic
        imageComic.setImageBitmap(comic.getComicImage())

        containerContent.setOnClickListener {
            onItemClickListener?.invoke(comic)
        }

        buttonDelete.setOnClickListener {
            onDeleteComicClickListener?.invoke(comic)
        }
    }
}
