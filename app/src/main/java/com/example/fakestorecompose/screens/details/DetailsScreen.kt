package com.example.fakestorecompose.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.example.fakestorecompose.R
import com.example.fakestorecompose.database.ProductEntity
import com.example.fakestorecompose.navigation.Routes
import com.example.fakestorecompose.screens.UiState
import com.example.fakestorecompose.ui.theme.FakeStoreComposeTheme

@Composable
fun DetailsScreenContent(
    viewModel: DetailsViewModel = hiltViewModel(),
    navController: NavHostController,
    id: Int,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchProduct(id)
    }

    when (state) {
        is UiState.Loading -> {}
        is UiState.Error -> {}
        is UiState.Success -> {
            DetailsScreen(
                product = (state as UiState.Success<DetailsUiState>).data.product,
                onReturnClicked = {  navController.popBackStack() },
                modifier = modifier,
            )
        }
    }
}

@Composable
fun DetailsScreen(
    product: ProductEntity,
    onReturnClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val pagerState = rememberPagerState(pageCount = { product.images.size })

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {

                IconButton(
                    onClick = onReturnClicked,
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Store"
                    )
                }

                Text(
                    text = stringResource(R.string.details_title),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            HorizontalPager(
                beyondViewportPageCount = product.images.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) { page ->
                SubcomposeAsyncImage(
                    model = product.images[page],
                    contentDescription = "${product.title} image ${page + 1}",
                    error = {
                        Box(
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.no_image),
                                contentDescription = "Unable to load Image.",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(64.dp)
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentScale = ContentScale.Crop
                )
            }

            // Indicadores del carrusel
            if (product.images.size > 1) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(product.images.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .padding(2.dp)
                                .clip(RoundedCornerShape(50))
                                .background(
                                    if (pagerState.currentPage == index)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                )
                        )
                    }
                }
            }

            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "Category: ${product.categoryName}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge
                    .copy(
                        lineBreak = LineBreak.Paragraph.copy(strategy = LineBreak.Strategy.Balanced)
                    ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsPreview() {
    val productEntity = ProductEntity(
        id = 1,
        categoryName = "Electronics",
        description = "A high-quality smartphone with advanced features",
        images = listOf("https://fakestoreapi.com/img/phone.jpg"),
        price = 59999L,
        slug = "smartphone-xyz",
        title = "Smartphone XYZ"
    )

    FakeStoreComposeTheme {
        DetailsScreen(
            product = productEntity,
            onReturnClicked = {}
        )
    }
}