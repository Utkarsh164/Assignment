package com.example.jetpackcomposeshoppingapp


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.jetpackcomposeshoppingapp.api.Product
import com.example.weatherapp.api.NetworkResponse
import com.valentinilk.shimmer.shimmer

@Composable
fun ProductPage(modifier: Modifier = Modifier, viewModel: ProductViewModel, navController: NavHostController) {
    val productResult by viewModel.productResult.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate("smartphoneList") },
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.Start)
        ) {
            Text(text = "Go to Smartphone Page")
        }
        when (val result = productResult) {
            is NetworkResponse.Error -> {
                Spacer(modifier = Modifier.height(200.dp))
                Text(text = result.message, fontSize = 20.sp, color = Color.Red)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.fetchProducts() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text(text = "Retry", color = Color.White)
                }
            }
            NetworkResponse.Loading -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(7) {
                        ShimmerProductCard()
                    }
                }
            }
            is NetworkResponse.Success -> {
                val products = result.data.products
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(products) { product ->
                        ProductCard(product, navController)
                    }
                }
            }
            null -> {}
        }
    }
}


@Composable
fun ShimmerProductCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .shimmer()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .shimmer()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(20.dp)
                        .shimmer()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(15.dp)
                        .shimmer()
                )
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable {
                navController.navigate("productDetail/${product.id}")
            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
            )
            Column {
                Text(
                    text = product.title,
                    fontSize = 18.sp
                )
                Text(
                    text = product.description,
                    maxLines = 2,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "$${product.price}",
                    color = Color(0xFF4CAF50),
                    fontSize = 16.sp
                )
            }
        }
    }
}
