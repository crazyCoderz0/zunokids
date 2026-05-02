package com.crazycoderz.zunokids.ui.numbers;

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
public final class NumbersViewModel_Factory implements Factory<NumbersViewModel> {
  private final Provider<UserPreferencesRepo> prefsRepoProvider;

  public NumbersViewModel_Factory(Provider<UserPreferencesRepo> prefsRepoProvider) {
    this.prefsRepoProvider = prefsRepoProvider;
  }

  @Override
  public NumbersViewModel get() {
    return newInstance(prefsRepoProvider.get());
  }

  public static NumbersViewModel_Factory create(Provider<UserPreferencesRepo> prefsRepoProvider) {
    return new NumbersViewModel_Factory(prefsRepoProvider);
  }

  public static NumbersViewModel newInstance(UserPreferencesRepo prefsRepo) {
    return new NumbersViewModel(prefsRepo);
  }
}
