package com.crazycoderz.zunokids.ui.objects;

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
public final class ObjectsViewModel_Factory implements Factory<ObjectsViewModel> {
  private final Provider<UserPreferencesRepo> prefsRepoProvider;

  public ObjectsViewModel_Factory(Provider<UserPreferencesRepo> prefsRepoProvider) {
    this.prefsRepoProvider = prefsRepoProvider;
  }

  @Override
  public ObjectsViewModel get() {
    return newInstance(prefsRepoProvider.get());
  }

  public static ObjectsViewModel_Factory create(Provider<UserPreferencesRepo> prefsRepoProvider) {
    return new ObjectsViewModel_Factory(prefsRepoProvider);
  }

  public static ObjectsViewModel newInstance(UserPreferencesRepo prefsRepo) {
    return new ObjectsViewModel(prefsRepo);
  }
}
