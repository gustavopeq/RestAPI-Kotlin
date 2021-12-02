package gustavo.projects.moviemanager.domain.models

import gustavo.projects.moviemanager.network.response.GetMovieDetailsByIdResponse


data class MovieDetails(
        val budget: Int?,
        val genres: List<MovieGenre?>?,
        val id: Int?,
        val overview: String?,
        val poster_path: String?,
        val release_date: String?,
        val revenue: Int?,
        val runtime: Int?,
        val title: String?,
        val vote_average: Double?,
        val movieCast: List<MovieCast?>?,
        val movieVideos: List<MovieVideo?>?,
        val productionCountry: List<ProductionCountry?>?
)