package com.crazycoderz.zunokids.ui.games.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crazycoderz.zunokids.data.UserPreferencesRepo
import com.crazycoderz.zunokids.util.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DragItem(val id: Int, val label: String, val color: Color)

@HiltViewModel
class DragMatchViewModel @Inject constructor(
    private val prefsRepo: UserPreferencesRepo,
    private val soundManager: SoundManager
) : ViewModel() {
    val items = listOf(
        DragItem(1, "🍎", Color(0xFFFF6B6B)),
        DragItem(2, "🍌", Color(0xFFF9D423)),
        DragItem(3, "🍇", Color(0xFFA29BFE)),
        DragItem(4, "🍉", Color(0xFF26DE81)),
        DragItem(5, "🍍", Color(0xFFFD9644))
    )

    var score = mutableIntStateOf(0)
        private set

    val matchedItems = mutableStateMapOf<Int, Boolean>()

    fun onMatch(id: Int) {
        if (matchedItems[id] != true) {
            matchedItems[id] = true
            score.intValue += 10
            soundManager.play(SoundManager.SoundType.SUCCESS)
            viewModelScope.launch {
                prefsRepo.addStars(10)
            }
        }
    }
    
    fun resetGame() {
        score.intValue = 0
        matchedItems.clear()
    }
}
