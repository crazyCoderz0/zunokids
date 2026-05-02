package com.crazycoderz.zunokids.ui.games;

import com.crazycoderz.zunokids.data.TtsManager;
import com.crazycoderz.zunokids.data.UserPreferencesRepo;
import com.crazycoderz.zunokids.util.SoundManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class MatchingGameViewModel_Factory implements Factory<MatchingGameViewModel> {
  private final Provider<UserPreferencesRepo> prefsRepoProvider;

  private final Provider<SoundManager> soundManagerProvider;

  private final Provider<TtsManager> ttsManagerProvider;

  public MatchingGameViewModel_Factory(Provider<UserPreferencesRepo> prefsRepoProvider,
      Provider<SoundManager> soundManagerProvider, Provider<TtsManager> ttsManagerProvider) {
    this.prefsRepoProvider = prefsRepoProvider;
    this.soundManagerProvider = soundManagerProvider;
    this.ttsManagerProvider = ttsManagerProvider;
  }

  @Override
  public MatchingGameViewModel get() {
    return newInstance(prefsRepoProvider.get(), soundManagerProvider.get(), ttsManagerProvider.get());
  }

  public static MatchingGameViewModel_Factory create(
      Provider<UserPreferencesRepo> prefsRepoProvider, Provider<SoundManager> soundManagerProvider,
      Provider<TtsManager> ttsManagerProvider) {
    return new MatchingGameViewModel_Factory(prefsRepoProvider, soundManagerProvider, ttsManagerProvider);
  }

  public static MatchingGameViewModel newInstance(UserPreferencesRepo prefsRepo,
      SoundManager soundManager, TtsManager ttsManager) {
    return new MatchingGameViewModel(prefsRepo, soundManager, ttsManager);
  }
}
