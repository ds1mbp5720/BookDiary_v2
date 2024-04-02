package com.example.presentation.bookdetail

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.BookModel
import com.example.domain.model.MyBookModel
import com.example.domain.model.WishBookModel
import com.example.mylibrary.R
import com.example.presentation.components.BasicButton
import com.example.presentation.components.BasicUpButton
import com.example.presentation.components.BookCoverImage
import com.example.presentation.components.BookDiaryDivider
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.components.GlideCard
import com.example.presentation.components.RatingBar
import com.example.presentation.theme.BookDiaryTheme
import com.example.presentation.util.addCommaWon
import java.lang.Integer.max
import java.lang.Integer.min

private val bottomBarHeight = 56.dp
private val titleHeight = 128.dp
private val gradientScroll = 230.dp // 스크롤을 위한 간격
private val imageOverlap = 115.dp // 상단 정보와 Top 간격 ( scroll 시 사라지는 부분)
private val minTitleOffset = 56.dp
private val minImageOffset = 42.dp // 이미지 최소 크기일때 padding Top
private val maxTitleOffset = imageOverlap + minTitleOffset + gradientScroll
private val expandedImageSize = 300.dp // 첫 이미지 사이즈(최대)
private val collapsedImageSize = 100.dp // 이미지 축소 사이즈
private val horizontalPadding = Modifier.padding(horizontal = 24.dp)

@Composable
fun BookDetail(
    bookId: Long,
    upPress: () -> Unit,
    bookDetailViewModel: BookDetailViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val context = LocalContext.current
        var offStoreDialogVisible by rememberSaveable { mutableStateOf(false) }
        val bookDetailInfo = bookDetailViewModel.bookDetail.collectAsStateWithLifecycle().value
        val offStoreInfo = bookDetailViewModel.offStoreInfo.collectAsStateWithLifecycle().value
        val scroll = rememberScrollState(0)
        Header()
        if (bookDetailInfo != null) {
            val bookDetail = bookDetailInfo.bookList[0]
            Body(
                book = bookDetail,
                url = bookDetail.link ?: "",
                scroll = scroll
            ) {
                bookDetailViewModel.getOffStoreInfo(bookId.toString())
                offStoreDialogVisible = true
            }
            Title(
                book = bookDetail,
            ) {
                scroll.value
            }
            Image(imageUrl = bookDetail.cover ?: "") { scroll.value }
            DetailBottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                insertMyBook = {
                    bookDetailViewModel.insertMyBook(
                        book = MyBookModel(
                            itemId = (bookDetail.itemId ?: "0").toLong(),
                            imageUrl = bookDetail.cover ?: "",
                            title = bookDetail.title ?: "제목 없음",
                            author = bookDetail.author ?: "저자 미확인",
                            link = bookDetail.link,
                            myReview = "테스트 리뷰",
                            period = "테스트 기간"
                        )
                    )
                    Toast.makeText(
                        context,
                        context.getString(R.string.str_add_record),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                insertWishBook = {
                    bookDetailViewModel.insertWishBook(
                        book = WishBookModel(
                            itemId = (bookDetail.itemId ?: "0").toLong(),
                            imageUrl = bookDetail.cover ?: "",
                            title = bookDetail.title ?: "제목 없음"
                        )
                    )
                    Toast.makeText(
                        context,
                        context.getString(R.string.str_add_wish),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
        BasicUpButton(upPress)

        AnimatedVisibility(
            visible = offStoreDialogVisible,
            enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            if (offStoreInfo != null) {
                OffStoreDialog(
                    offStoreInfo = offStoreInfo,
                    onDismiss = { offStoreDialogVisible = false }
                )
            }
        }
    }
}

@Composable
fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(Brush.horizontalGradient(BookDiaryTheme.colors.tornado1))
    )
}

@Composable
private fun Title(
    book: BookModel,
    scrollProvider: () -> Int
) {
    val maxOffset = with(LocalDensity.current) { maxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { minTitleOffset.toPx() }
    val collapseRange = with(LocalDensity.current) { (maxTitleOffset - minTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = titleHeight)
            .statusBarsPadding()
            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(color = BookDiaryTheme.colors.uiBackground)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Layout(
            content = {
                Column {
                    Text(
                        text = book.title?.replace("알라딘 상품정보 - ", "") ?: "No Title",
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        color = BookDiaryTheme.colors.textPrimary,
                        modifier = horizontalPadding
                    )
                    Text(
                        text = book.author ?: "지은이 미확인",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        color = BookDiaryTheme.colors.textPrimary,
                        modifier = horizontalPadding
                    )
                }
            }
        ) { measurables, constraints ->
            val collapseFraction = collapseFractionProvider()
            val titleMaxSize =
                if (collapseFraction < 0.5f) min(
                    expandedImageSize.roundToPx(),
                    constraints.maxWidth
                )
                else constraints.maxWidth - min(expandedImageSize.roundToPx(), constraints.maxWidth)
            val titleMinSize =
                constraints.maxWidth - max(collapsedImageSize.roundToPx(), constraints.minWidth)
            val titleWidth = lerp(titleMaxSize, titleMinSize, collapseFraction)
            val titlePlaceable =
                measurables[0].measure(Constraints.fixed(titleWidth, 90.dp.toPx().toInt()))
            layout(
                width = constraints.maxWidth,
                height = 56.dp.toPx().toInt()
            ) {
                titlePlaceable.placeRelative(0, 0)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = horizontalPadding
        ) {
            Text(
                text = "정가:",
                style = MaterialTheme.typography.labelSmall,
                color = BookDiaryTheme.colors.textPrimary
            )
            Text(
                text = (book.priceStandard ?: "0").addCommaWon(),
                style = MaterialTheme.typography.displaySmall,
                color = BookDiaryTheme.colors.textPrimary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        BookDiaryDivider()
    }
}

@Composable
private fun Image(
    imageUrl: String,
    scrollProvider: () -> Int
) {
    val collapseRange = with(LocalDensity.current) { (maxTitleOffset - minTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }
    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = horizontalPadding.then(Modifier.statusBarsPadding())
    ) {
        BookCoverImage(
            imageUrl = imageUrl,
            contentDescription = "detail_cover",
            modifier = Modifier
                .fillMaxSize()
        )
    }

}

// scroll 하단 이동시 이미지 size 변경
@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val collapseFraction = collapseFractionProvider()

        val imageMaxSize = min(expandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(collapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)// - 80
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth + 140))

        val imageY = lerp(minTitleOffset, minImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2,
            constraints.maxWidth - imageWidth,
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }
}

@Composable
private fun Body(
    book: BookModel,
    url: String,
    scroll: ScrollState,
    offStoreInfo: () -> Unit
) {
    val context = LocalContext.current
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(minTitleOffset)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(modifier = Modifier.height(gradientScroll))
            BookDiarySurface(Modifier.fillMaxWidth()) {
                Column {
                    Spacer(modifier = Modifier.height(imageOverlap + titleHeight + 20.dp))
                    DetailSubInfoRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        page = book.subInfo?.itemPage,
                        stockStatus = book.stockStatus,
                        ratingCnt = book.subInfo?.ratingInfo?.ratingCount
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.str_detail_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = BookDiaryTheme.colors.textHelp,
                        modifier = horizontalPadding
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    var seeMore by remember { mutableStateOf(true) }
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = if (book.description != "") book.description ?: stringResource(id = R.string.str_no_detail)
                        else  stringResource(id = R.string.str_no_detail),
                        color = BookDiaryTheme.colors.textPrimary,
                        maxLines = if (seeMore) 2 else Int.MAX_VALUE,
                        overflow = TextOverflow.Ellipsis,
                        modifier = horizontalPadding
                    )
                    val textButton =
                        if (seeMore) stringResource(id = R.string.str_see_more)
                        else stringResource(id = R.string.str_see_less)
                    Text(
                        text = textButton,
                        style = MaterialTheme.typography.bodyLarge,
                        color = BookDiaryTheme.colors.textLink,
                        modifier = Modifier
                            .heightIn(20.dp)
                            .fillMaxWidth()
                            .padding(top = 15.dp, start = 40.dp)
                            .clickable { seeMore = !seeMore }
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    DetailInfoColumn(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        title = stringResource(id = R.string.str_category),
                        detail = book.categoryName,
                        exceptionDetail = stringResource(id = R.string.str_no_category)
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    DetailInfoColumn(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        title = stringResource(id = R.string.str_publisher),
                        detail = book.subInfo?.bestSellerRank,
                        exceptionDetail = stringResource(id = R.string.str_no_publisher)
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    DetailInfoColumn(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        title = stringResource(id = R.string.str_pub_date),
                        detail = book.pubDate,
                        exceptionDetail = stringResource(id = R.string.str_no_pub_date)
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    DetailInfoColumn(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        title = stringResource(id = R.string.str_bestseller_rank),
                        detail = book.publisher,
                        exceptionDetail = stringResource(id = R.string.str_no_bestseller)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.str_rating_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = BookDiaryTheme.colors.textHelp,
                        modifier = horizontalPadding
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    val ratingText =
                        "별 평점 : " + book.subInfo?.ratingInfo?.ratingScore + " / 리뷰 수 : " + book.subInfo?.ratingInfo?.ratingCount
                    Text(
                        text = ratingText,
                        style = MaterialTheme.typography.bodyLarge,
                        color = BookDiaryTheme.colors.textHelp,
                        modifier = horizontalPadding
                    )
                    RatingBar(
                        modifier = horizontalPadding,
                        context = context,
                        rating = (book.subInfo?.ratingInfo?.ratingScore ?: "0").toFloat(),
                        totalCnt = 10
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = horizontalPadding
                    ) {
                        BasicButton(
                            onClick = offStoreInfo,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.str_btn_shop_inventory),
                                modifier = Modifier.fillMaxWidth(),
                                color = BookDiaryTheme.colors.textSecondary,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        BasicButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.str_btn_link),
                                modifier = Modifier.fillMaxWidth(),
                                color = BookDiaryTheme.colors.textSecondary,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    BookDiaryDivider()
                    Text(
                        text = stringResource(id = R.string.str_cart_review),
                        style = MaterialTheme.typography.titleLarge,
                        color = BookDiaryTheme.colors.textHelp,
                        modifier = horizontalPadding
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(
                        modifier = horizontalPadding
                    ) {
                        book.subInfo?.let {
                            items(it.cardReviewImgList) { url ->
                                GlideCard(
                                    imageUrl = url,
                                    modifier = Modifier
                                        .padding(
                                            start = 4.dp,
                                            end = 4.dp
                                        ),
                                    glideModifier = Modifier
                                        .fillMaxWidth()
                                        .height(360.dp)
                                )
                            }
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .padding(bottom = bottomBarHeight)
                            .navigationBarsPadding()
                            .height(8.dp)
                    )
                }
            }
        }
    }
}

/**
 * 페이지, 재고상태, 리뷰수
 */
@Composable
private fun DetailSubInfoRow(
    modifier: Modifier = Modifier,
    page: String?,
    stockStatus: String?,
    ratingCnt: String?
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                color = BookDiaryTheme.colors.uiBackground,
                shape = RoundedCornerShape(10.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DetailInfoColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            textAlign = TextAlign.Center,
            title = stringResource(id = R.string.str_page),
            detail = page,
            exceptionDetail = stringResource(id = R.string.str_no_page)
        )

        DetailInfoColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            textAlign = TextAlign.Center,
            title = stringResource(id = R.string.str_stock_title),
            detail = if (stockStatus?.isEmpty() == true) stringResource(id = R.string.str_stock) else stockStatus,
            exceptionDetail = stringResource(id = R.string.str_stock)
        )

        DetailInfoColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            textAlign = TextAlign.Center,
            title = stringResource(id = R.string.str_review_cnt),
            detail = ratingCnt,
            exceptionDetail = "0"
        )
    }
}

@Composable
private fun DetailInfoColumn(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    textAlign: TextAlign = TextAlign.Start,
    title: String,
    detail: String?,
    exceptionDetail: String
) {
    Column(
        modifier = modifier
            .padding(bottom = 7.dp),
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = textAlign,
            color = BookDiaryTheme.colors.textHelp,
            modifier = horizontalPadding
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = detail ?: exceptionDetail,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = textAlign,
            color = BookDiaryTheme.colors.textPrimary,
            modifier = horizontalPadding
        )
    }
}

@Composable
private fun DetailBottomBar(
    modifier: Modifier = Modifier,
    insertMyBook: () -> Unit,
    insertWishBook: () -> Unit
) {
    BookDiarySurface(modifier) {
        Column {
            BookDiaryDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .navigationBarsPadding()
                    .then(horizontalPadding)
                    .heightIn(min = 56.dp)
            ) {
                IconButton(
                    onClick = insertWishBook,
                    modifier = Modifier
                        .statusBarsPadding()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = BookDiaryTheme.colors.interactivePrimary
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_bookmark_border_24),
                        tint = BookDiaryTheme.colors.iconInteractive,
                        contentDescription = "insert_wish"
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                BasicButton(
                    onClick = insertMyBook,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.str_btn_record),
                        color = BookDiaryTheme.colors.textSecondary,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }

    }
}