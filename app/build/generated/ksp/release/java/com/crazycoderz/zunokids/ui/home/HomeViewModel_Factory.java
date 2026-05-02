package com.crazycoderz.zunokids.ui.home;

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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<UserPreferencesRepo> prefsRepoProvider;

  public HomeViewModel_Factory(Provider<UserPreferencesRepo> prefsRepoProvider) {
    this.prefsRepoProvider = prefsRepoProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(prefsRepoProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<UserPreferencesRepo> prefsRepoProvider) {
    return new HomeViewModel_Factory(prefsRepoProvider);
  }

  public static HomeViewModel newInstance(UserPreferencesRepo prefsRepo) {
    return new HomeViewModel(prefsRepo);
  }
}
