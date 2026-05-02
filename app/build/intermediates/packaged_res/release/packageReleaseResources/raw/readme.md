# res/raw/ — Audio Assets

## chime.mp3
Place a cheerful chime audio file here named `chime.mp3`.

This file is played via `SoundPool` when a learning module is completed.

You can source a royalty-free chime from:
- https://freesound.org (search "success chime" or "kids chime")
- https://pixabay.com/sound-effects/ (search "chime children")
- https://mixkit.co/free-sound-effects/win/

Recommended specs:
- Format: MP3 or OGG
- Duration: 1-2 seconds
- Sample rate: 44100 Hz
- Style: bright, cheerful, child-friendly bell/chime

## SoundPool Usage (already wired in the app)

```kotlin
val soundPool = SoundPool.Builder()
    .setMaxStreams(2)
    .build()
val chimeId = soundPool.load(context, R.raw.chime, 1)
soundPool.play(chimeId, 1f, 1f, 0, 0, 1f)
```
