package com.eviko.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eviko.app.ui.screens.auth.LoginScreen
import com.eviko.app.ui.screens.auth.RegisterScreen
import com.eviko.app.ui.screens.catalog.CatalogScreen
import com.eviko.app.ui.screens.cart.CartScreen
import com.eviko.app.ui.screens.profile.ProfileScreen
import com.eviko.app.ui.screens.splash.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Catalog : Screen("catalog")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        
        composable(Screen.Catalog.route) {
            CatalogScreen(navController)
        }
        
        composable(Screen.Cart.route) {
            CartScreen(navController)
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
    }
} 