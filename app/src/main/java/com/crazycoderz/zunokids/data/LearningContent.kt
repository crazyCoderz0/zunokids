package com.crazycoderz.zunokids.data

import androidx.compose.ui.graphics.Color

// ─── Data Models ──────────────────────────────────────────────────────────────

data class NumberItem(
    val number: Int,
    val word: String,
    val emoji: String,
    val color: Color
)

data class AlphabetItem(
    val letter: Char,
    val word: String,
    val emoji: String,
    val color: Color
)

data class FruitItem(
    val nameKey: String,   // key for strings.xml lookup
    val emoji: String,
    val color: Color
)

data class ColorItem(
    val nameKey: String,
    val color: Color,
    val textColor: Color = Color.White
)

data class ObjectItem(
    val nameKey: String,
    val emoji: String,
    val color: Color
)

data class AppLanguage(
    val code: String,
    val flag: String,
    val nativeName: String,
    val ttsLocale: String
)

// ─── Supported Languages ──────────────────────────────────────────────────────

object SupportedLanguages {
    val list = listOf(
        AppLanguage("en", "🇺🇸", "English", "en-US"),
        AppLanguage("es", "🇪🇸", "Español", "es-ES"),
        AppLanguage("zh", "🇨🇳", "中文", "zh-CN"),
        AppLanguage("hi", "🇮🇳", "हिन्दी", "hi-IN"),
        AppLanguage("fr", "🇫🇷", "Français", "fr-FR")
    )
}

// ─── Numbers ──────────────────────────────────────────────────────────────────

object NumbersContent {
    val items = listOf(
        NumberItem(1,  "One",   "⭐", Color(0xFFFF6B6B)),
        NumberItem(2,  "Two",   "🍎", Color(0xFFFF9F43)),
        NumberItem(3,  "Three", "🌟", Color(0xFFFFE66D)),
        NumberItem(4,  "Four",  "🦋", Color(0xFF1DD1A1)),
        NumberItem(5,  "Five",  "🌈", Color(0xFF4ECDC4)),
        NumberItem(6,  "Six",   "🌸", Color(0xFFA8DADC)),
        NumberItem(7,  "Seven", "🦄", Color(0xFF6C5CE7)),
        NumberItem(8,  "Eight", "🎈", Color(0xFFE84393)),
        NumberItem(9,  "Nine",  "🌙", Color(0xFF00B894)),
        NumberItem(10, "Ten",   "🎉", Color(0xFFE17055))
    )
}

// ─── Alphabet ─────────────────────────────────────────────────────────────────

object AlphabetContent {
    val items = listOf(
        AlphabetItem('A', "Apple",      "🍎", Color(0xFFFF6B6B)),
        AlphabetItem('B', "Ball",       "⚽", Color(0xFFFF9F43)),
        AlphabetItem('C', "Cat",        "🐱", Color(0xFFFFE66D)),
        AlphabetItem('D', "Dog",        "🐶", Color(0xFF1DD1A1)),
        AlphabetItem('E', "Elephant",   "🐘", Color(0xFF4ECDC4)),
        AlphabetItem('F', "Fish",       "🐟", Color(0xFF74B9FF)),
        AlphabetItem('G', "Grapes",     "🍇", Color(0xFF6C5CE7)),
        AlphabetItem('H', "House",      "🏠", Color(0xFFE84393)),
        AlphabetItem('I', "Ice Cream",  "🍦", Color(0xFFA8DADC)),
        AlphabetItem('J', "Jellyfish",  "🪼", Color(0xFFE17055)),
        AlphabetItem('K', "Kite",       "🪁", Color(0xFF00B894)),
        AlphabetItem('L', "Lion",       "🦁", Color(0xFFFF6B6B)),
        AlphabetItem('M', "Monkey",     "🐵", Color(0xFFFF9F43)),
        AlphabetItem('N', "Nest",       "🪺", Color(0xFFFFE66D)),
        AlphabetItem('O', "Octopus",    "🐙", Color(0xFFE84393)),
        AlphabetItem('P', "Penguin",    "🐧", Color(0xFF4ECDC4)),
        AlphabetItem('Q', "Queen",      "👑", Color(0xFF6C5CE7)),
        AlphabetItem('R', "Rainbow",    "🌈", Color(0xFFFF6B6B)),
        AlphabetItem('S', "Star",       "⭐", Color(0xFFFFE66D)),
        AlphabetItem('T', "Tiger",      "🐯", Color(0xFFFF9F43)),
        AlphabetItem('U', "Umbrella",   "☂️", Color(0xFF74B9FF)),
        AlphabetItem('V', "Violin",     "🎻", Color(0xFF1DD1A1)),
        AlphabetItem('W', "Whale",      "🐳", Color(0xFF4ECDC4)),
        AlphabetItem('X', "Xylophone",  "🎵", Color(0xFFE17055)),
        AlphabetItem('Y', "Yak",        "🦬", Color(0xFF00B894)),
        AlphabetItem('Z', "Zebra",      "🦓", Color(0xFF6C5CE7))
    )
}

// ─── Fruits ───────────────────────────────────────────────────────────────────

object FruitsContent {
    val items = listOf(
        FruitItem("fruit_apple",      "🍎", Color(0xFFFF6B6B)),
        FruitItem("fruit_banana",     "🍌", Color(0xFFFFE66D)),
        FruitItem("fruit_mango",      "🥭", Color(0xFFFF9F43)),
        FruitItem("fruit_orange",     "🍊", Color(0xFFE17055)),
        FruitItem("fruit_grape",      "🍇", Color(0xFF6C5CE7)),
        FruitItem("fruit_strawberry", "🍓", Color(0xFFFF6B6B)),
        FruitItem("fruit_watermelon", "🍉", Color(0xFF1DD1A1)),
        FruitItem("fruit_pineapple",  "🍍", Color(0xFFFFE66D)),
        FruitItem("fruit_cherry",     "🍒", Color(0xFFE84393)),
        FruitItem("fruit_lemon",      "🍋", Color(0xFFFFF176))
    )
}

// ─── Colors ───────────────────────────────────────────────────────────────────

object ColorsContent {
    val items = listOf(
        ColorItem("color_red",    Color(0xFFE53935), Color.White),
        ColorItem("color_blue",   Color(0xFF1E88E5), Color.White),
        ColorItem("color_yellow", Color(0xFFFDD835), Color(0xFF333333)),
        ColorItem("color_green",  Color(0xFF43A047), Color.White),
        ColorItem("color_orange", Color(0xFFFB8C00), Color.White),
        ColorItem("color_purple", Color(0xFF8E24AA), Color.White),
        ColorItem("color_pink",   Color(0xFFE91E8C), Color.White),
        ColorItem("color_white",  Color(0xFFF5F5F5), Color(0xFF333333)),
        ColorItem("color_black",  Color(0xFF212121), Color.White),
        ColorItem("color_brown",  Color(0xFF6D4C41), Color.White)
    )
}

// ─── Objects ──────────────────────────────────────────────────────────────────

object ObjectsContent {
    val items = listOf(
        ObjectItem("object_ball",  "🏀", Color(0xFFFF9F43)),
        ObjectItem("object_book",  "📚", Color(0xFF74B9FF)),
        ObjectItem("object_chair", "🪑", Color(0xFF6C5CE7)),
        ObjectItem("object_cup",   "☕", Color(0xFFFF6B6B)),
        ObjectItem("object_door",  "🚪", Color(0xFF1DD1A1)),
        ObjectItem("object_tree",  "🌳", Color(0xFF00B894)),
        ObjectItem("object_car",   "🚗", Color(0xFF4ECDC4)),
        ObjectItem("object_sun",   "☀️", Color(0xFFFFE66D)),
        ObjectItem("object_star",  "⭐", Color(0xFFE84393)),
        ObjectItem("object_house", "🏠", Color(0xFFE17055))
    )
}
