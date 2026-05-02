package com.crazycoderz.zunokids.ui.games.viewmodel;

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
public final class DragMatchViewModel_Factory implements Factory<DragMatchViewModel> {
  private final Provider<UserPreferencesRepo> prefsRepoProvider;

  private final Provider<SoundManager> soundManagerProvider;

  public DragMatchViewModel_Factory(Provider<UserPreferencesRepo> prefsRepoProvider,
      Provider<SoundManager> soundManagerProvider) {
    this.prefsRepoProvider = prefsRepoProvider;
    this.soundManagerProvider = soundManagerProvider;
  }

  @Override
  public DragMatchViewModel get() {
    return newInstance(prefsRepoProvider.get(), soundManagerProvider.get());
  }

  public static DragMatchViewModel_Factory create(Provider<UserPreferencesRepo> prefsRepoProvider,
      Provider<SoundManager> soundManagerProvider) {
    return new DragMatchViewModel_Factory(prefsRepoProvider, soundManagerProvider);
  }

  public static DragMatchViewModel newInstance(UserPreferencesRepo prefsRepo,
      SoundManager soundManager) {
    return new DragMatchViewModel(prefsRepo, soundManager);
  }
}
