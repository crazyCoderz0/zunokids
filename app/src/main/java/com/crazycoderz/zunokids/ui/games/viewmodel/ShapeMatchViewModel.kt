package com.crazycoderz.zunokids.ui.games.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crazycoderz.zunokids.data.UserPreferencesRepo
import com.crazycoderz.zunokids.util.SoundManager
import com.crazycoderz.zunokids.ui.games.newgames.ShapeType
import com.crazycoderz.zunokids.ui.games.newgames.ShapeTarget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShapeMatchViewModel @Inject constructor(
    private val prefsRepo: UserPreferencesRepo,
    private val soundManager: SoundManager
) : ViewModel() {
    val shapes = listOf(
        ShapeTarget(ShapeType.CIRCLE, Color(0xFFFF6B6B)),
        ShapeTarget(ShapeType.SQUARE, Color(0xFF4ECDC4)),
        ShapeTarget(ShapeType.TRIANGLE, Color(0xFFFFE66D))
    )

    var matchedCount = mutableIntStateOf(0)
        private set

    val matchedStates = mutableStateMapOf<ShapeType, Boolean>()

    fun onMatch(type: ShapeType) {
        if (matchedStates[type] != true) {
            matchedStates[type] = true
            matchedCount.intValue++
            soundManager.play(SoundManager.SoundType.SUCCESS)
            viewModelScope.launch {
                prefsRepo.addStars(10)
            }
        }
    }

    fun restartGame() {
        matchedCount.intValue = 0
        matchedStates.clear()
    }
}
