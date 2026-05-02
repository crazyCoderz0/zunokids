package com.crazycoderz.zunokids.ui.alphabet;

import com.crazycoderz.zunokids.data.UserPreferencesRepo;
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
public final class AlphabetViewModel_Factory implements Factory<AlphabetViewModel> {
  private final Provider<UserPreferencesRepo> prefsRepoProvider;

  public AlphabetViewModel_Factory(Provider<UserPreferencesRepo> prefsRepoProvider) {
    this.prefsRepoProvider = prefsRepoProvider;
  }

  @Override
  public AlphabetViewModel get() {
    return newInstance(prefsRepoProvider.get());
  }

  public static AlphabetViewModel_Factory create(Provider<UserPreferencesRepo> prefsRepoProvider) {
    return new AlphabetViewModel_Factory(prefsRepoProvider);
  }

  public static AlphabetViewModel newInstance(UserPreferencesRepo prefsRepo) {
    return new AlphabetViewModel(prefsRepo);
  }
}
