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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
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
private val gradientScroll = 180.dp
private val imageOverlap = 115.dp
private val minTitleOffset = 56.dp
private val minImageOffset = 12.dp
private val maxTitleOffset = imageOverlap + minTitleOffset + gradientScroll
private val expandedImageSize = 300.dp
private val collapsedImageSize = 150.dp
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
        val bookDetailState = bookDetailViewModel.bookDetail.collectAsStateWithLifecycle()
        val scroll = rememberScrollState(0)
        Header()
        if (bookDetailInfo != null) {
            val bookDetail = bookDetailInfo.bookList[0]
            Body(book = bookDetail, scroll = scroll)
            Title(
                book = bookDetail,
                url = bookDetail.link ?: "",
                offStoreInfo = {
                    bookDetailViewModel.getOffStoreInfo(bookId.toString())
                    offStoreDialogVisible = true
                }
            ) {
                scroll.value
            }
            Image(imageUrl = bookDetail.cover ?: "") { scroll.value } // todo 이미지 null 경우 기본 이미지 추가하기
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
                    Toast.makeText(context, context.getString(R.string.str_add_record), Toast.LENGTH_SHORT).show()
                },
                insertWishBook = {
                    bookDetailViewModel.insertWishBook(
                        book = WishBookModel(
                            itemId = (bookDetail.itemId ?: "0").toLong(),
                            imageUrl = bookDetail.cover ?: "",
                            title = bookDetail.title ?: "제목 없음"
                        )
                    )
                    Toast.makeText(context, context.getString(R.string.str_add_wish), Toast.LENGTH_SHORT).show()
                }
            )
        }
        BasicUpButton(upPress)

        AnimatedVisibility(
            visible = offStoreDialogVisible,
            enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f),
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
    url: String,
    //bookDetailViewModel: BookDetailViewModel,
    offStoreInfo: () -> Unit,
    scrollProvider: () -> Int,
) {
    //val book = bookDetailViewModel.bookDetail.collectAsStateWithLifecycle().value.data?.bookList
    val maxOffset = with(LocalDensity.current) { maxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { minTitleOffset.toPx() }
    val context = LocalContext.current
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
                        text = book/*?.get(0)?*/.title?.replace("알라딘 상품정보 - ", "") ?: "No Title",
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        color = BookDiaryTheme.colors.textPrimary,
                        modifier = horizontalPadding
                    )
                    Text(
                        text = book/*?.get(0)?*/.author ?: "지은이 미확인",
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
                if (collapseFraction < 0.5f) min(expandedImageSize.roundToPx(), constraints.maxWidth)
                else constraints.maxWidth - min(expandedImageSize.roundToPx(), constraints.maxWidth)
            val titleMinSize = constraints.maxWidth - max(collapsedImageSize.roundToPx(), constraints.minWidth)
            val titleWidth = lerp(titleMaxSize, titleMinSize, collapseFraction)
            val titlePlaceable = measurables[0].measure(Constraints.fixed(titleWidth, 90.dp.toPx().toInt()))
            layout(
                width = constraints.maxWidth,
                height = 56.dp.toPx().toInt()
            ) {
                titlePlaceable.placeRelative(0, 0)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = (book/*?.get(0)?*/.priceSales ?: "0").addCommaWon(),
            style = MaterialTheme.typography.displayMedium,
            color = BookDiaryTheme.colors.textPrimary,
            modifier = horizontalPadding
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row() {
            BasicButton(
                onClick = offStoreInfo,
                modifier = Modifier.weight(1f),
                border = BorderStroke(width = 1.dp, color = Color.Black)
            ) {
                Text(
                    text = stringResource(id = R.string.str_btn_shop_inventory),
                    modifier = Modifier.fillMaxWidth(),
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
                modifier = Modifier.weight(1f),
                border = BorderStroke(width = 1.dp, color = Color.Black)
            ) {
                Text(
                    text = stringResource(id = R.string.str_btn_link),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        BookDiaryDivider()
    }
}

@Composable
private fun Image(
    imageUrl: String,
    //bookDetailViewModel: BookDetailViewModel,
    scrollProvider: () -> Int
) {
    //val book = bookDetailViewModel.bookDetail.collectAsStateWithLifecycle().value.data?.bookList
    val collapseRange = with(LocalDensity.current) { (maxTitleOffset - minTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }
    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = horizontalPadding.then(Modifier.statusBarsPadding())
    ) {
        BookCoverImage(
            //imageUrl = book?.get(0)?.cover ?: "",
            imageUrl = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
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
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

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
    scroll: ScrollState
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
                    Spacer(modifier = Modifier.height(imageOverlap))
                    Spacer(modifier = Modifier.height(titleHeight))

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
                        text = if (book.description != "") book.description ?: "상세정보가 없습니다."
                        else "상세정보가 없습니다.",
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
                    Text(
                        text = stringResource(id = R.string.str_category),
                        style = MaterialTheme.typography.titleLarge,
                        color = BookDiaryTheme.colors.textHelp,
                        modifier = horizontalPadding
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = book.categoryName ?: "세부 분류가 없습니다.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = BookDiaryTheme.colors.textHelp,
                        modifier = horizontalPadding
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.str_publisher),
                        style = MaterialTheme.typography.titleLarge,
                        color = BookDiaryTheme.colors.textHelp,
                        modifier = horizontalPadding
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = book.publisher ?: "출판사 정보가 없습니다.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = BookDiaryTheme.colors.textHelp,
                        modifier = horizontalPadding
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.str_bestseller_rank),
                        style = MaterialTheme.typography.titleLarge,
                        color = BookDiaryTheme.colors.textHelp,
                        modifier = horizontalPadding
                    )

                    Text(
                        text = book.subInfo?.bestSellerRank ?: "베스트셀러 순위 정보가 없습니다.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = BookDiaryTheme.colors.textHelp,
                        modifier = horizontalPadding
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.str_rating_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = BookDiaryTheme.colors.textHelp,
                        modifier = horizontalPadding
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        val ratingText = "별 평점 : " + book.subInfo?.ratingInfo?.ratingScore + " / 리뷰 수 : " + book.subInfo?.ratingInfo?.ratingCount
                        Text(
                            text = ratingText,
                            style = MaterialTheme.typography.bodyLarge,
                            color = BookDiaryTheme.colors.textHelp,
                            modifier = horizontalPadding
                        )
                    }
                    RatingBar(
                        modifier = horizontalPadding,
                        context = context,
                        rating = (book.subInfo?.ratingInfo?.ratingScore ?: "0").toFloat(),
                        totalCnt = 10
                    )
                    Spacer(modifier = Modifier.height(6.dp))
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


                    //todo 다른 책 리스트 가져와서 BookCollectoin 사용해서 뿌리기
                }
            }
        }
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
                BasicButton(
                    onClick = insertWishBook,
                    modifier = Modifier.weight(1f),
                    border = BorderStroke(width = 1.dp, color = Color.Black)
                ) {
                    Text(
                        text = stringResource(id = R.string.str_btn_wish),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                BasicButton(
                    onClick = insertMyBook,
                    modifier = Modifier.weight(1f),
                    border = BorderStroke(width = 1.dp, color = Color.Black)
                ) {
                    Text(
                        text = stringResource(id = R.string.str_btn_record),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }

    }
}