package com.xk.cd.ui.main

import android.content.Intent
import android.net.Uri
import android.widget.SearchView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.xk.cd.BR
import com.xk.cd.R
import com.xk.cd.databinding.FragmentMainBinding
import com.xk.cd.ui.base.view.BaseBoundFragment

class MainFragment : BaseBoundFragment<MainFragmentViewModel>(), SearchView.OnQueryTextListener {

    lateinit var binding: FragmentMainBinding

    override val layoutRId = R.layout.fragment_main
    override val viewModelNameRId = BR.viewModel
    override val viewModelClass = MainFragmentViewModel::class.java

    override fun bindToViewModel() {
        binding = viewDataBinding as FragmentMainBinding
        pushViewBelowStatusBar(binding.container)

        setObservers()
        setListeners()
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.getComic(query.toInt())
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        return false
    }

    private fun setObservers() = with(viewModel) {
        comicDirectUrl.observe(viewLifecycleOwner) {
            shareComic(it)
        }
        openLink.observe(viewLifecycleOwner) {
            loadCustomTabForSite(it)
        }
        comicAddedToFavoriteList.observe(viewLifecycleOwner) {
            Toast.makeText(
                context,
                getString(R.string.comic_added_to_favorite_list),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setListeners() {
        binding.run {
            searchComicByNum.setOnQueryTextListener(this@MainFragment)
        }
    }

    private fun loadCustomTabForSite(url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

    private fun shareComic(comicDirectUrl: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, comicDirectUrl)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
