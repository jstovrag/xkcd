package com.xk.cd.main

import androidx.lifecycle.Observer
import com.xk.cd.base.BaseViewModelTest
import com.xk.cd.base.TestUtil
import com.xk.cd.common.constants.AppConstants
import com.xk.cd.data.comic.ComicRepository
import com.xk.cd.data.models.comic.Comic
import com.xk.cd.data.store.RealmCache
import com.xk.cd.scheduling.SchedulingProvider
import com.xk.cd.ui.base.NavigationCommand
import com.xk.cd.ui.main.MainFragmentDirections
import com.xk.cd.ui.main.MainFragmentViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class MainFragmentViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: MainFragmentViewModel

    private val comicRepository = mockk<ComicRepository>(relaxed = true)
    private val realmCacheComic = mockk<RealmCache<Comic>>(relaxed = true)
    private val schedulingProvider = mockk<SchedulingProvider>(relaxed = true)
    private val comicMockData = TestUtil.createComic()
    private val comicObserver = mockk<Observer<Comic>>(relaxed = true)

    @Before
    override fun setup() {
        super.setup()
        viewModel = MainFragmentViewModel(comicRepository, realmCacheComic, schedulingProvider)
        commonSetup(viewModel)
    }

    @Test
    fun `get comic success`() {
        // given
        coEvery { comicRepository.getLastComic() } returns comicMockData
        coEvery { comicRepository.getComic(1) } returns comicMockData

        // when
        viewModel.currentComic.observeForever(comicObserver)
        viewModel.isLoading.observeForever(loadingObserver)
        viewModel.error.observeForever(errorObserver)
        viewModel.navFirstClick()
        viewModel.getComic(1)

        // then
        verify(exactly = 2) { comicObserver.onChanged(comicMockData) }
        verify(exactly = 2) { loadingObserver.onChanged(true) }
        verify(exactly = 0) { errorObserver.onChanged(any()) }
    }

    @Test
    fun `get last comic success`() {
        // given
        coEvery { comicRepository.getLastComic() } returns comicMockData

        // when
        viewModel.currentComic.observeForever(comicObserver)
        viewModel.isLoading.observeForever(loadingObserver)
        viewModel.error.observeForever(errorObserver)
        viewModel.navLastClick()

        // then
        verify(exactly = 1) { comicObserver.onChanged(comicMockData) }
        verify(exactly = 1) { loadingObserver.onChanged(true) }
        verify(exactly = 0) { errorObserver.onChanged(any()) }
    }

    @Test
    fun `navigate to favorite comics fragment`() {
        // given
        viewModel.navigationCommands.observeForever(navigationObserver)

        // when
        viewModel.favoriteComicsClick()

        // then
        verify(exactly = 1) {
            navigationObserver.onChanged(
                NavigationCommand.To(
                    MainFragmentDirections.actionMainToFavoriteComics()
                )
            )
        }
    }

    @Test
    fun `open newsletter`() {
        // given
        val openLinkObserver = mockk<Observer<String>>(relaxed = true)

        // when
        viewModel.openLink.observeForever(openLinkObserver)
        viewModel.newsLetterClick()

        // then
        verify(exactly = 1) { openLinkObserver.onChanged(AppConstants.NEWSLETTER_URL) }
    }
}
