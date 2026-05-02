package com.crazycoderz.zunokids.ui.parent;

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
public final class ParentViewModel_Factory implements Factory<ParentViewModel> {
  private final Provider<UserPreferencesRepo> prefsRepoProvider;

  public ParentViewModel_Factory(Provider<UserPreferencesRepo> prefsRepoProvider) {
    this.prefsRepoProvider = prefsRepoProvider;
  }

  @Override
  public ParentViewModel get() {
    return newInstance(prefsRepoProvider.get());
  }

  public static ParentViewModel_Factory create(Provider<UserPreferencesRepo> prefsRepoProvider) {
    return new ParentViewModel_Factory(prefsRepoProvider);
  }

  public static ParentViewModel newInstance(UserPreferencesRepo prefsRepo) {
    return new ParentViewModel(prefsRepo);
  }
}
