package com.crazycoderz.zunokids.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.crazycoderz.zunokids.ui.auth.LoginScreen
import com.crazycoderz.zunokids.ui.auth.SignupScreen
import com.crazycoderz.zunokids.ui.alphabet.AlphabetScreen
import com.crazycoderz.zunokids.ui.colors.ColorsScreen
import com.crazycoderz.zunokids.ui.fruits.FruitsScreen
import com.crazycoderz.zunokids.ui.games.MatchingGameScreen
import com.crazycoderz.zunokids.ui.games.QuizScreen
import com.crazycoderz.zunokids.ui.home.HomeScreen
import com.crazycoderz.zunokids.ui.dashboard.DashboardScreen
import com.crazycoderz.zunokids.ui.games.GamesMenuScreen
import com.crazycoderz.zunokids.ui.games.newgames.*
import com.crazycoderz.zunokids.ui.games.viewmodel.*
import com.crazycoderz.zunokids.util.SoundManager
import com.crazycoderz.zunokids.data.TtsManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.crazycoderz.zunokids.ui.numbers.NumbersScreen
import com.crazycoderz.zunokids.ui.objects.ObjectsScreen
import com.crazycoderz.zunokids.ui.parent.ParentDashboardScreen
import com.crazycoderz.zunokids.ui.splash.SplashScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate(Screen.Signup.route)
                }
            )
        }

        composable(Screen.Signup.route) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToLearn = { navController.navigate(Screen.Learn.route) },
                onNavigateToGames = { navController.navigate(Screen.GamesMenu.route) }
            )
        }

        composable(Screen.Learn.route) {
            HomeScreen(
                onNavigate = { route -> navController.navigate(route) },
                onNavigateToParent = { navController.navigate(Screen.Parent.route) }
            )
        }

        composable(Screen.GamesMenu.route) {
            GamesMenuScreen(
                onBack = { navController.popBackStack() },
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.Parent.route) {
            ParentDashboardScreen(
                onBack = { navController.popBackStack() },
                onLogout = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Numbers.route) {
            NumbersScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Alphabet.route) {
            AlphabetScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Fruits.route) {
            FruitsScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Colors.route) {
            ColorsScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Objects.route) {
            ObjectsScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Quiz.route) {
            QuizScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.MatchCards.route) {
            MatchingGameScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.DragMatch.route) {
            DragMatchGame(onBack = { navController.popBackStack() })
        }

        composable(Screen.ShapeMatch.route) {
            ShapeMatchGame(onBack = { navController.popBackStack() })
        }

        composable(Screen.CategoryGame.route) {
            CategoryGameScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.MissingLetter.route) {
            MissingLetterGame(onBack = { navController.popBackStack() })
        }
    }
}
