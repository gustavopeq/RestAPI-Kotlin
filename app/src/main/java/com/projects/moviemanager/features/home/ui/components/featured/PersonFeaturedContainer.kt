package com.projects.moviemanager.features.home.ui.components.featured

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.NetworkImage
import com.projects.moviemanager.common.ui.theme.MainBarGreyColor
import com.projects.moviemanager.common.ui.util.UiConstants.BROWSE_CARD_DEFAULT_ELEVATION
import com.projects.moviemanager.common.ui.util.UiConstants.CARD_ROUND_CORNER
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.ui.util.UiConstants.PERSON_FEATURED_IMAGE_WIDTH
import com.projects.moviemanager.common.ui.util.UiConstants.PERSON_FEATURED_TITLE_MAX_LINES
import com.projects.moviemanager.common.ui.util.UiConstants.POSTER_ASPECT_RATIO_MULTIPLY
import com.projects.moviemanager.common.ui.util.UiConstants.SMALL_PADDING
import com.projects.moviemanager.domain.models.person.PersonDetails
import com.projects.moviemanager.util.Constants.BASE_ORIGINAL_IMAGE_URL

@Composable
fun PersonFeaturedInfo(
    trendingPerson: PersonDetails?,
    goToDetails: (Int, MediaType) -> Unit
) {
    trendingPerson?.let {
        val fullImagePath = BASE_ORIGINAL_IMAGE_URL + trendingPerson.posterPath
        val imageWidth = PERSON_FEATURED_IMAGE_WIDTH.dp
        val imageHeight = imageWidth * POSTER_ASPECT_RATIO_MULTIPLY

        Box(
            modifier = Modifier.padding(horizontal = DEFAULT_MARGIN.dp)
        ) {
            Card(
                modifier = Modifier
                    .clickable(
                        onClick = { goToDetails(trendingPerson.id, trendingPerson.mediaType) }
                    )
                    .height(imageHeight),
                colors = CardDefaults.cardColors(
                    containerColor = MainBarGreyColor
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = BROWSE_CARD_DEFAULT_ELEVATION.dp
                )
            ) {
                Row {
                    PersonFeaturedInfo(trendingPerson)
                    NetworkImage(
                        modifier = Modifier.clip(
                            RoundedCornerShape(
                                topEnd = CARD_ROUND_CORNER.dp,
                                bottomEnd = CARD_ROUND_CORNER.dp
                            )
                        ),
                        imageUrl = fullImagePath,
                        widthDp = imageWidth,
                        heightDp = imageHeight
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.PersonFeaturedInfo(trendingPerson: PersonDetails) {
    Column(
        modifier = Modifier
            .padding(DEFAULT_PADDING.dp)
            .weight(1f)
    ) {
        HomeCardTitle(
            title = trendingPerson.title,
            maxLines = PERSON_FEATURED_TITLE_MAX_LINES
        )
        Spacer(modifier = Modifier.weight(1f))

        CardHeader(
            headerRes = R.string.person_featured_card_role_header
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING.dp))
        Text(
            text = trendingPerson.knownForDepartment.orEmpty(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))

        CardHeader(
            headerRes = R.string.person_featured_card_known_for_header
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING.dp))
        trendingPerson.knownFor.forEach {
            Text(
                text = it.title ?: it.name ?: "",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun HomeCardTitle(
    title: String,
    maxLines: Int = Int.MAX_VALUE
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            overflow = TextOverflow.Ellipsis,
            maxLines = maxLines
        )
    }
}

@Composable
private fun CardHeader(
    @StringRes headerRes: Int
) {
    Text(
        text = stringResource(id = headerRes),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.surface
    )
}
