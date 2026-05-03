package com.crazycoderz.zunokids.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Dashboard : Screen("dashboard") // The new split screen
    
    object Learn : Screen("learn") // The module grid
    object GamesMenu : Screen("games_menu") // The game list
    
    // Module routes
    object Numbers : Screen("numbers")
    object Alphabet : Screen("alphabet")
    object Fruits : Screen("fruits")
    object Colors : Screen("colors")
    object Objects : Screen("objects")
    
    object Parent : Screen("parent")
    object DeleteAccount : Screen("delete_account")
    
    // New Games
    object Quiz : Screen("quiz")
    object MatchCards : Screen("match_cards") // Existing simple match
    object DragMatch : Screen("drag_match")
    object ShapeMatch : Screen("shape_match")
    object CategoryGame : Screen("category_game")
    object MissingLetter : Screen("missing_letter")
}
