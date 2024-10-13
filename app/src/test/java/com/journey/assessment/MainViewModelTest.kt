import android.content.Context
import android.net.ConnectivityManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.journey.assessment.dao.CommentDao
import com.journey.assessment.model.CommentEntity
import com.journey.assessment.model.CommentModel
import com.journey.assessment.model.PostModel
import com.journey.assessment.repository.PostRepository
import com.journey.assessment.viewmodel.MainViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.*
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel
    private val mockConnectivityManager = mockk<ConnectivityManager>(relaxed = true)

    @Before
    fun setup() {
        mainViewModel = MainViewModel(
            application = mockk(relaxed = true) {
                every { getSystemService(Context.CONNECTIVITY_SERVICE) } returns mockConnectivityManager
            },
            postRepository = mockk(relaxed = true),
            commentDao = mockk(relaxed = true)
        )
    }

    @Test
    fun `test no internet connection handling`() {
        every { mockConnectivityManager.activeNetwork } returns null
        val result = mainViewModel.hasInternetConnection()
        assertFalse(result)
    }

}