package com.projects.moviemanager.network.models.content.common

import com.projects.moviemanager.common.domain.models.util.MediaType

interface BaseMediaContentResponse {
    val id: Int
    val title: String
    val vote_average: Double
    val poster_path: String?
    val backdrop_path: String?
    val genre_ids: List<Int?>?
    val original_language: String?
    val original_title: String?
    val overview: String?
    val popularity: Double?
    val release_date: String?
    val vote_count: Int?
    val mediaType: MediaType?
    val adult: Boolean?
    val genres: List<ContentGenre?>?
    val homepage: String?
    val production_companies: List<ProductionCompany?>?
    val production_countries: List<ProductionCountry?>?
    val spoken_languages: List<SpokenLanguage?>?
    val runtime: Int?
}
