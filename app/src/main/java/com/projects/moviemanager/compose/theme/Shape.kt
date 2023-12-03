package com.projects.moviemanager.compose.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.compose.common.ui.util.UiConstants.CARD_ROUND_CORNER

val RoundCornerShapes = Shapes(
    // Extra small = round on bottom
    extraSmall = RoundedCornerShape(
        bottomStart = CARD_ROUND_CORNER.dp,
        bottomEnd = CARD_ROUND_CORNER.dp
    ),
    // Small = round on top
    small = RoundedCornerShape(topStart = CARD_ROUND_CORNER.dp, topEnd = CARD_ROUND_CORNER.dp),
    // Medium = completely round
    medium = RoundedCornerShape(6.dp),
    large = RoundedCornerShape(8.dp)
)