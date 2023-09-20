package com.miraelDev.vauma.presentation.searchAimeScreen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.common.collect.ImmutableList
import com.miraelDev.vauma.R
import com.miraelDev.vauma.exntensions.pressClickEffect
import com.miraelDev.vauma.presentation.mainScreen.LocalTheme
import com.miraelDev.vauma.presentation.searchAimeScreen.filterScreen.FilterViewModel
import com.miraelDev.vauma.ui.theme.LightGreen700


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterScreen(
    onBackPressed: () -> Unit,
    viewModel: FilterViewModel = hiltViewModel()
) {

    val filterGenreList = viewModel.genreListFlow.collectAsStateWithLifecycle(listOf())

    val filterYearCategory by viewModel.yearCategoryFlow.collectAsStateWithLifecycle(INITIAL)

    val filterSortByCategory by viewModel.sortByCategoryFlow.collectAsStateWithLifecycle(INITIAL)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            Toolbar(
                onBackPressed = onBackPressed,
            )
        }
    ) {
        BackHandler(onBack = onBackPressed)
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {

                Text(
                    modifier = Modifier.padding(start = 4.dp, bottom = 16.dp),
                    text = stringResource(R.string.year),
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = FontFamily.SansSerif
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {

                    val filterCategoriesYearList = ImmutableList.of(
                        stringResource(R.string.ongoing),
                        "2023",
                        "2022",
                        "2021",
                        "2015-2020",
                        "2008-2014",
                        "2000-2007",
                        stringResource(R.string.before_2000),
                    )

                    filterCategoriesYearList.forEach { category ->
                        CategoryField(category, category == filterYearCategory) {
                            viewModel.selectCategory(
                                YEAR_CATEGORIES_ID,
                                category,
                                category == filterYearCategory
                            )
                        }
                    }
                }

                Text(
                    modifier = Modifier.padding(start = 4.dp, bottom = 16.dp),
                    text = stringResource(R.string.genre),
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = FontFamily.SansSerif
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {

                    filterGenreList.value.forEachIndexed { index, category ->
                        CategoryField(category.name, category.isSelected) {
                            viewModel.selectCategory(
                                index + 4,
                                category.name,
                                category.isSelected
                            )
                        }
                    }
                }

                Text(
                    modifier = Modifier.padding(start = 4.dp, bottom = 16.dp),
                    text = stringResource(R.string.sortBy),
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = FontFamily.SansSerif
                )

                FlowRow(
                    modifier = Modifier.padding(bottom = 48.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {

                    val filterCategoriesSortList = ImmutableList.of(
                        stringResource(R.string.sort_by_name),
                        stringResource(R.string.sort_by_rate),
                        stringResource(R.string.sort_by_episode_count),
                        stringResource(R.string.sort_by_year_released),
                    )

                    filterCategoriesSortList.forEach { category ->
                        CategoryField(category, category == filterSortByCategory) {
                            viewModel.selectCategory(
                                SORT_CATEGORIES_ID,
                                category,
                                category == filterSortByCategory
                            )
                        }
                    }
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 12.dp),
                backgroundColor = MaterialTheme.colors.background,
                onClick = viewModel::clearAllFilters
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colors.primary,
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_bin),
                        contentDescription = stringResource(R.string.clear)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.clear),
                        color = MaterialTheme.colors.primary,
                        fontSize = 14.sp
                    )
                }

            }
        }
    }

}

@Composable
private fun Toolbar(
    onBackPressed: () -> Unit,
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                        contentDescription = stringResource(R.string.back)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .width(8.dp)
                )
                Text(
                    text = stringResource(id = R.string.filter),
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = FontFamily.SansSerif,
                )
            }

        }
    }
}

@Composable
private fun CategoryField(
    text: String,
    selected: Boolean,
    onCategoryClick: () -> Unit
) {

    OutlinedButton(
        modifier = Modifier.pressClickEffect(),
        onClick = onCategoryClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults
            .buttonColors(
                backgroundColor = if (selected) MaterialTheme.colors.primary
                else MaterialTheme.colors.background
            ),
        elevation = ButtonDefaults.elevation(3.dp),
        border = BorderStroke(
            1.dp, color = MaterialTheme.colors.primary.copy(alpha = 0.9f)
        ),
    ) {
        Text(
            text = text,
            color = if (selected) Color.White
            else if (LocalTheme.current) LightGreen700
            else MaterialTheme.colors.primary,
            maxLines = 1,
            fontSize = 16.sp
        )
    }
}

private const val YEAR_CATEGORIES_ID = 1
private const val SORT_CATEGORIES_ID = 2
private const val INITIAL = ""