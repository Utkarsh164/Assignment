package com.example.jetpackcomposeshoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeshoppingapp.ui.theme.JetpackComposeShoppingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        val sviewModel = ViewModelProvider(this)[SmartphoneViewModel::class.java]
        setContent {

            val navController = rememberNavController()
            JetpackComposeShoppingAppTheme {

                Scaffold (modifier = Modifier.fillMaxWidth()) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "productList"
                ) {
                    composable("productList") {
                        ProductPage(modifier = Modifier.padding(innerPadding),viewModel = viewModel, navController = navController)
                    }
                    composable("productDetail/{productId}") { backStackEntry ->
                        val productId = backStackEntry.arguments?.getString("productId")?.toInt() ?: 0
                        ProductDetailPage(modifier = Modifier.padding(innerPadding),productId = productId, viewModel = viewModel, navController = navController)
                    }

                    composable("smartphoneList") {
                        SmartphonePage(modifier = Modifier.padding(innerPadding), viewModel = sviewModel, navController = navController)
                    }
                    composable("smartphoneDetail/{smartphoneId}") { backStackEntry ->
                        val smartphoneId = backStackEntry.arguments?.getString("smartphoneId")?.toInt() ?: 0
                        SmartphoneDetailPage(modifier = Modifier.padding(innerPadding), smartphoneId = smartphoneId, viewModel = sviewModel, navController = navController)
                    }
                }
            }}
        }
    }
}

