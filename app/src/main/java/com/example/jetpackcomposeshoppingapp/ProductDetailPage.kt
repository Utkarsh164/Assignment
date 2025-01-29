
package com.example.jetpackcomposeshoppingapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.navigation.NavHostController
import com.example.jetpackcomposeshoppingapp.api.Product
import com.example.jetpackcomposeshoppingapp.api.Review
import com.example.weatherapp.api.NetworkResponse


@Composable
fun ProductDetailPage(
    modifier: Modifier = Modifier,
    productId: Int,
    viewModel: ProductViewModel,
    navController: NavHostController
) {
    val productResult by viewModel.productResult.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (val result = productResult) {
            is NetworkResponse.Error -> {
                Text(
                    text = result.message,
                    color = Color.Red,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is NetworkResponse.Success -> {
                val product = result.data.products.find { it.id == productId }
                product?.let {
                    AsyncImage(
                        model = it.thumbnail,
                        contentDescription = it.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = it.title,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = it.description,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = "Price: $${it.price}",
                        fontSize = 18.sp,
                        color = Color(0xFF4CAF50)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ReviewsSection(it.reviews)
                } ?: Text(
                    text = "Product not found.",
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }
            null -> {}
        }
    }
}

@Composable
fun ReviewsSection(reviews: List<Review>?) {
    reviews?.let {
        Text(
            text = "Reviews",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn {
            items(it) { review ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = review.reviewerName,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = review.comment,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Rating: ${review.rating}",
                            fontSize = 14.sp,
                            color = Color(0xFFFFC107)
                        )
                    }
                }
            }
        }
    } ?: Text(
        text = "No reviews available.",
        color = Color.Gray,
        fontSize = 16.sp
    )
}
