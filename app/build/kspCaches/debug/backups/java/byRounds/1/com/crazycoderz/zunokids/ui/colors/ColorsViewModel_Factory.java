package com.crazycoderz.zunokids.ui.colors;

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
public final class ColorsViewModel_Factory implements Factory<ColorsViewModel> {
  private final Provider<UserPreferencesRepo> prefsRepoProvider;

  public ColorsViewModel_Factory(Provider<UserPreferencesRepo> prefsRepoProvider) {
    this.prefsRepoProvider = prefsRepoProvider;
  }

  @Override
  public ColorsViewModel get() {
    return newInstance(prefsRepoProvider.get());
  }

  public static ColorsViewModel_Factory create(Provider<UserPreferencesRepo> prefsRepoProvider) {
    return new ColorsViewModel_Factory(prefsRepoProvider);
  }

  public static ColorsViewModel newInstance(UserPreferencesRepo prefsRepo) {
    return new ColorsViewModel(prefsRepo);
  }
}
