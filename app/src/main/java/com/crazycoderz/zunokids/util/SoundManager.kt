package com.crazycoderz.zunokids.util

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.crazycoderz.zunokids.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        .build()

    private val sounds = mutableMapOf<SoundType, Int>()

    init {
        // Pre-load common sounds if they exist
        // sounds[SoundType.CLICK] = soundPool.load(context, R.raw.click, 1)
        // sounds[SoundType.SUCCESS] = soundPool.load(context, R.raw.success, 1)
        // sounds[SoundType.CELEBRATION] = soundPool.load(context, R.raw.celebration, 1)
    }

    fun play(type: SoundType) {
        sounds[type]?.let { id ->
            soundPool.play(id, 1f, 1f, 0, 0, 1f)
        }
    }

    enum class SoundType {
        CLICK, SUCCESS, CELEBRATION, ERROR
    }
}
