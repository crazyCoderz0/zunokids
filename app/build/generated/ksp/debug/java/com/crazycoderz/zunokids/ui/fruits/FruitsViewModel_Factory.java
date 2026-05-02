package com.crazycoderz.zunokids.ui.fruits;

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
public final class FruitsViewModel_Factory implements Factory<FruitsViewModel> {
  private final Provider<UserPreferencesRepo> prefsRepoProvider;

  public FruitsViewModel_Factory(Provider<UserPreferencesRepo> prefsRepoProvider) {
    this.prefsRepoProvider = prefsRepoProvider;
  }

  @Override
  public FruitsViewModel get() {
    return newInstance(prefsRepoProvider.get());
  }

  public static FruitsViewModel_Factory create(Provider<UserPreferencesRepo> prefsRepoProvider) {
    return new FruitsViewModel_Factory(prefsRepoProvider);
  }

  public static FruitsViewModel newInstance(UserPreferencesRepo prefsRepo) {
    return new FruitsViewModel(prefsRepo);
  }
}
