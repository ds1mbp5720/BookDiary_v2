package com.example.presentation.graph

import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.core.os.ConfigurationCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mylibrary.R
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.home.Home
import com.example.presentation.profile.Profile
import com.example.presentation.record.Record
import com.example.presentation.search.Search
import java.util.Locale

private val TextIconSpacing = 2.dp
private val BottomNavHeight = 56.dp
private val BottomNavLabelTransformOrigin = TransformOrigin(0f, 0.5f)
private val BottomNavIndicatorShape = RoundedCornerShape(percent = 50)
private val BottomNavigationItemPadding = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
enum class MainSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.str_home, Icons.Outlined.Home ,"home/home"),
    SEARCH(R.string.str_search, Icons.Outlined.Search ,"home/search"),
    RECORD(R.string.str_record, Icons.Outlined.Create ,"home/record"),
    PROFILE(R.string.str_profile, Icons.Outlined.AccountCircle ,"home/profile")
}
fun NavGraphBuilder.addMainGraph(
    onBookSelected: (Long, NavBackStackEntry) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    composable(MainSections.HOME.route){ from ->
        Home(onBookClick = { id -> onBookSelected(id, from) }, onNavigateToRoute = onNavigateToRoute, modifier =  modifier)
    }
    composable(MainSections.SEARCH.route){ from ->
        Search(onBookClick = { id -> onBookSelected(id, from) }, onNavigateToRoute = onNavigateToRoute, modifier =  modifier)
    }
    composable(MainSections.RECORD.route){ from ->
        Record(onBookClick = { id -> onBookSelected(id, from) }, onNavigateToRoute = onNavigateToRoute, modifier =  modifier)
    }
    composable(MainSections.PROFILE.route){ from ->
        Profile(onBookClick = { id -> onBookSelected(id, from) }, onNavigateToRoute = onNavigateToRoute, modifier =  modifier)
    }
}

@Composable
fun BookDiaryBottomBar(
    tabs: Array<MainSections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
    color: Color = Color.White,
    contentColor : Color = Color.Gray
    //todo 색상 추가
) {
    val routes = remember { tabs.map { it.route } }
    val currentSection = tabs.first { it.route == currentRoute }

    BookDiarySurface(
        color = color,
        contentColor = contentColor
    ){
        val springSpec = SpringSpec<Float>( stiffness = 800f, dampingRatio = 0.8f)
        BookDiaryBottomNavLayout(
            selectedIndex = currentSection.ordinal,
            itemCount = routes.size,
            animSpec = springSpec,
            indicator = { BookDiaryBottomNavIndicator() },
            modifier = Modifier.navigationBarsPadding()
        ) {
            val configuration = LocalConfiguration.current
            val currentLocale: Locale =
                ConfigurationCompat.getLocales(configuration).get(0) ?: Locale.getDefault()

            tabs.forEach { section ->
                val selected = section == currentSection
                val tint by animateColorAsState(
                    targetValue = if(selected) Color.White else Color.Gray,
                    label = ""
                )
                val text = stringResource(id = section.title).uppercase(currentLocale)

                BookDiaryBottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = section.icon,
                            tint = tint,
                            contentDescription = text
                        )
                    },
                    text = {
                        Text(
                            text = text,
                            color = tint,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1
                        )
                    },
                    selected = selected,
                    onSelected = { navigateToRoute(section.route) },
                    animSpec = springSpec,
                    modifier = BottomNavigationItemPadding
                        .clip(BottomNavIndicatorShape)
                )
            }
        }
    }
}

@Composable
fun BookDiaryBottomNavigationItem(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    selected: Boolean,
    onSelected: () -> Unit,
    animSpec: AnimationSpec<Float>,
    modifier: Modifier = Modifier
) {
    val animationProgress by animateFloatAsState(targetValue = if (selected) 1f else 0f, animationSpec = animSpec, label = "")
    BookDiaryBottomNavItemLayout(
        icon = icon,
        text = text,
        animateProgress = animationProgress,
        modifier = modifier
            .selectable(selected = selected, onClick = onSelected)
            .wrapContentSize()
    )
}

@Composable
private fun BookDiaryBottomNavItemLayout(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    @FloatRange(from = 0.0, to = 1.0) animateProgress: Float,
    modifier: Modifier = Modifier
){
    Layout(
        modifier = modifier,
        content = {
            Box(
               modifier = Modifier
                   .layoutId("icon")
                   .padding(horizontal = TextIconSpacing),
                content = icon
            )
            val scale = lerp(0.6f, 1f, animateProgress)
            Box(
                modifier = Modifier
                    .layoutId("text")
                    .padding(horizontal = TextIconSpacing)
                    .graphicsLayer {
                        alpha = animateProgress
                        scaleX = scale
                        scaleY = scale
                        transformOrigin = BottomNavLabelTransformOrigin
                    },
                content = text
            )
        }
    ){measurables, constraints ->
        val iconPlacealbe = measurables.first { it.layoutId == "icon" }.measure(constraints)
        val textPlacealbe = measurables.first { it.layoutId == "text" }.measure(constraints)

        placeTextAndIcon(
            textPlaceable = textPlacealbe,
            iconPlaceable = iconPlacealbe,
            width = constraints.maxWidth,
            height = constraints.maxHeight,
            animateProgress = animateProgress
        )
    }
}

private fun MeasureScope.placeTextAndIcon(
    textPlaceable: Placeable,
    iconPlaceable: Placeable,
    width: Int,
    height: Int,
    @FloatRange(from = 0.0, to = 1.0) animateProgress: Float
): MeasureResult{
    val iconY = (height - iconPlaceable.height) / 2
    val textY = (height - iconPlaceable.height) / 2

    val textWidth = textPlaceable.width * animateProgress
    val iconX = (width - textWidth - iconPlaceable.width) / 2
    val textX = iconX + iconPlaceable.width

    return layout(width, height) {
        iconPlaceable.placeRelative(iconX.toInt(), iconY)
        if (animateProgress != 0f) {
            textPlaceable.placeRelative(textX.toInt(), textY)
        }
    }
}

@Composable
private fun BookDiaryBottomNavLayout(
    selectedIndex : Int,
    itemCount: Int,
    animSpec: AnimationSpec<Float>,
    indicator: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // 선택 구분
    val selectionFractions = remember(itemCount) {
        List(itemCount) { i ->
            Animatable(if (i == selectedIndex) 1f else 0f)
        }
    }
    selectionFractions.forEachIndexed { index, animatable ->
        val target = if (index == selectedIndex) 1f else 0f
        LaunchedEffect(target, animSpec) {
            animatable.animateTo(target, animSpec)
        }
    }

    // 위치별 indicator
    val indicatorIndex = remember { Animatable(0f) }
    val targetIndicatorIndex = selectedIndex.toFloat()
    LaunchedEffect(targetIndicatorIndex) {
        indicatorIndex.animateTo(targetIndicatorIndex, animSpec)
    }

    //
    Layout(
        modifier = modifier.height(BottomNavHeight),
        content = {
            content()
            Box(Modifier.layoutId("indicator"), content = indicator)
        }
    ) {measurables, constraints ->
        check(itemCount == (measurables.size -1))

        val unselectedWidth = constraints.maxWidth / (itemCount +1)
        val selectedWidth = 2 * unselectedWidth
        val indicatorMeasurable = measurables.first { it.layoutId == "indicator" }

        val itemPlaceables = measurables
            .filterNot { it == indicatorMeasurable }
            .mapIndexed {index, measurable ->
                val width = lerp(unselectedWidth, selectedWidth, selectionFractions[index].value)
                measurable.measure(
                    constraints.copy(
                        minWidth = width,
                        maxWidth = width
                    )
                )
            }
        val indicatorPlaceable = indicatorMeasurable.measure(
        constraints.copy(
            minWidth = selectedWidth,
            maxWidth = selectedWidth
        )
        )

        layout(
            width = constraints.maxWidth,
            height = itemPlaceables.maxByOrNull { it.height }?.height ?: 0
        ) {
            val indicatorLeft = indicatorIndex.value * unselectedWidth
            indicatorPlaceable.placeRelative(x = indicatorLeft.toInt(), y = 0)
            var x = 0
            itemPlaceables.forEach{
                it.placeRelative(x = x, y = 0)
                x += it.width
            }
        }
    }
}

@Composable
private fun BookDiaryBottomNavIndicator(
    strokeWidth: Dp = 2.dp,
    color: Color = Color.White,
    shape: Shape = BottomNavIndicatorShape
) {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .then(BottomNavigationItemPadding)
            .border(strokeWidth, color, shape)
    )
}