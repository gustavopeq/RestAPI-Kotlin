package com.projects.moviemanager.common.ui.components.tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.theme.MainBarGreyColor
import com.projects.moviemanager.common.util.UiConstants.BACKGROUND_INDEX
import com.projects.moviemanager.common.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.util.UiConstants.LARGE_PADDING
import com.projects.moviemanager.common.util.UiConstants.WATCHLIST_ADD_NEW_ICON_SIZE
import com.projects.moviemanager.common.util.removeParentPadding
import com.projects.moviemanager.features.watchlist.ui.components.WatchlistTabItem

@Composable
fun setupGenericTabs(
    tabList: List<TabItem>,
    onTabSelected: (Int) -> Unit = {}
): Triple<List<TabItem>, State<Int>, (Int, Boolean) -> Unit> {
    tabList.forEachIndexed { index, tabItem ->
        tabItem.tabIndex = index
    }

    val selectedTabIndex = rememberSaveable {
        mutableIntStateOf(tabList.firstOrNull()?.tabIndex ?: 0)
    }

    val updateSelectedTab: (Int, Boolean) -> Unit = { index, focusSelectedTab ->
        if (focusSelectedTab) {
            selectedTabIndex.intValue = index
        }
        onTabSelected(index)
    }

    return Triple(tabList, selectedTabIndex, updateSelectedTab)
}

@Composable
fun GenericTabRow(
    selectedTabIndex: Int,
    tabList: List<TabItem>,
    updateSelectedTab: (Int, Boolean) -> Unit,
    onLongClick: (Int, Offset) -> Unit = { _, _ -> }
) {
    ScrollableTabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = selectedTabIndex,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = MaterialTheme.colorScheme.secondary
            )
        },
        divider = { },
        containerColor = Color.Transparent,
        edgePadding = 0.dp
    ) {
        tabList.forEachIndexed { index, mediaTypeTabItem ->
            if (mediaTypeTabItem == WatchlistTabItem.AddNewTab) {
                AddNewTab(
                    tabIndex = index,
                    onClick = {
                        updateSelectedTab(index, false)
                    }
                )
            } else {
                val tabName = mediaTypeTabItem.tabResId?.let {
                    stringResource(id = it)
                } ?: mediaTypeTabItem.tabName

                GenericTab(
                    text = tabName.orEmpty(),
                    isSelected = selectedTabIndex == index,
                    onClick = {
                        updateSelectedTab(index, true)
                    },
                    onLongClick = { offset ->
                        onLongClick(index, offset)
                    }
                )
            }
        }
    }
    Divider(
        color = MainBarGreyColor,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-1).dp)
            .zIndex(BACKGROUND_INDEX)
            .removeParentPadding(DEFAULT_MARGIN.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GenericTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: (Offset) -> Unit
) {
    var tabOffset by remember { mutableStateOf(Offset.Zero) }
    Tab(
        modifier = Modifier
            .padding(horizontal = DEFAULT_PADDING.dp)
            .onGloballyPositioned { coordinates ->
                tabOffset = coordinates.localToRoot(Offset.Zero)
            },
        selected = isSelected,
        onClick = { onClick() }
    ) {
        Text(
            modifier = Modifier.combinedClickable(
                onLongClick = { onLongClick(tabOffset) },
                onClick = { onClick() }
            ),
            text = text.uppercase(),
            color = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.tertiary
            },
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(LARGE_PADDING.dp))
    }
}

@Composable
fun AddNewTab(
    tabIndex: Int,
    onClick: (Int) -> Unit
) {
    Tab(
        modifier = Modifier.padding(horizontal = DEFAULT_PADDING.dp),
        selected = false,
        onClick = { onClick(tabIndex) }
    ) {
        Icon(
            modifier = Modifier.size(WATCHLIST_ADD_NEW_ICON_SIZE.dp),
            painter = painterResource(id = R.drawable.ic_watchlist_add_list),
            contentDescription = stringResource(id = R.string.add_new_tab_description),
            tint = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(LARGE_PADDING.dp))
    }
}
