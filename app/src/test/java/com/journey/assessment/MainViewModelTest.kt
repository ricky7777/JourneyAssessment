import android.content.Context
import android.net.ConnectivityManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.journey.assessment.viewmodel.MainViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertFalse
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