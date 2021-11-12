package gustavo.projects.moviemanager.movies.nowplaying

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.epoxy.PopularMoviesPagingEpoxyController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NowPlayingFragment : Fragment() {

    private val epoxyController = PopularMoviesPagingEpoxyController(::onMovieSelected)

    private val viewModel: NowPlayingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                epoxyController.submitData(pagingData)
            }
        }

        view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView).setControllerAndBuildModels(epoxyController)
    }


    private fun onMovieSelected(movieId: Int) {
        val directions =
            NowPlayingFragmentDirections.actionNowPlayingFragmentToMovieDetailsFragment(
                movieId
            )
        findNavController().navigate(directions)
    }


}