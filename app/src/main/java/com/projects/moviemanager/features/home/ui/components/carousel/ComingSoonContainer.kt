package com.projects.moviemanager.features.home.ui.components.carousel

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.ui.components.card.ImageContentCard
import com.projects.moviemanager.common.ui.util.UiConstants.CAROUSEL_CARDS_WIDTH
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.domain.models.content.GenericContent

@Composable
fun ComingSoonCarousel(
    @StringRes carouselHeaderRes: Int,
    comingSoonList: List<GenericContent>,
    currentScreenWidth: Int,
    goToDetails: (Int, MediaType) -> Unit
) {
    if (comingSoonList.isNotEmpty()) {
        ClassicCarousel(
            carouselHeaderRes = carouselHeaderRes,
            itemList = comingSoonList,
            currentScreenWidth = currentScreenWidth,
            goToDetails = goToDetails
        ) { item, goToDetails ->
            ImageContentCard(
                modifier = Modifier.padding(
                    top = DEFAULT_PADDING.dp,
                    bottom = DEFAULT_PADDING.dp,
                    end = DEFAULT_PADDING.dp
                ),
                item = item,
                adjustedCardSize = CAROUSEL_CARDS_WIDTH.dp,
                goToDetails = goToDetails
            )
        }
        Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
    }
}
