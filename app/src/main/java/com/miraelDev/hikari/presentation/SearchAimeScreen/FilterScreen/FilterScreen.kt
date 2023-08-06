package com.miraelDev.hikari.presentation.SearchAimeScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.miraelDev.hikari.R
import com.miraelDev.hikari.exntensions.pressClickEffect
import com.miraelDev.hikari.presentation.SearchAimeScreen.FilterScreen.FilterViewModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterScreen(
    onBackPressed: () -> Unit
) {


    val viewModel = hiltViewModel<FilterViewModel>()

    val filterGenreList = viewModel.genreListFlow.collectAsState(listOf())

    val filterYearCategory by viewModel.yearCategoryFlow.collectAsState(INITIAL)

    val filterSortByCategory by viewModel.sortByCategoryFlow.collectAsState(INITIAL)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            Toolbar(
                onBackPressed = onBackPressed,
                onClearAllClick = { viewModel.clearAllFilters() }
            )
        }
    ) {
        BackHandler { onBackPressed() }
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(16.dp)
                    .fillMaxSize(),
            ) {

                Text(
                    modifier = Modifier.padding(start = 4.dp, bottom = 16.dp),
                    text = "Год выпуска",
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = FontFamily.SansSerif
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {

                    val filterCategoriesYearList = listOf(
                        "Онгоинг",
                        "2023",
                        "2022",
                        "2021",
                        "2015-2020",
                        "2008-2014",
                        "2000-2007",
                        "до 2000",
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
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {

                    val filterCategoriesSortList = listOf(
                        "Алфавиту",
                        "Рейтингу",
                        "Количеству серий",
                        "Году выхода",
                        "Дате добавления",
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
                    .padding(bottom = 16.dp),
                backgroundColor = MaterialTheme.colors.background,
                onClick = { viewModel.clearAllFilters() }
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colors.primary,
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_bin),
                        contentDescription = "clear"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Очистить",
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
    onClearAllClick: () -> Unit,
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
                IconButton(onClick = { onBackPressed() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .width(8.dp)
                )
                Text(
                    text = "Фильтр",
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
        onClick = { onCategoryClick() },
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
            else MaterialTheme.colors.primary,
            maxLines = 1,
            fontSize = 16.sp
        )
    }
}

private const val YEAR_CATEGORIES_ID = 1
private const val SORT_CATEGORIES_ID = 2
private const val INITIAL = ""