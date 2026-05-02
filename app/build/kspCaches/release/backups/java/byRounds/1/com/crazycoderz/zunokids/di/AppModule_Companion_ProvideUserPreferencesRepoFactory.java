package com.crazycoderz.zunokids.di;

import android.content.Context;
import com.crazycoderz.zunokids.data.UserPreferencesRepo;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AppModule_Companion_ProvideUserPreferencesRepoFactory implements Factory<UserPreferencesRepo> {
  private final Provider<Context> contextProvider;

  public AppModule_Companion_ProvideUserPreferencesRepoFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public UserPreferencesRepo get() {
    return provideUserPreferencesRepo(contextProvider.get());
  }

  public static AppModule_Companion_ProvideUserPreferencesRepoFactory create(
      Provider<Context> contextProvider) {
    return new AppModule_Companion_ProvideUserPreferencesRepoFactory(contextProvider);
  }

  public static UserPreferencesRepo provideUserPreferencesRepo(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.Companion.provideUserPreferencesRepo(context));
  }
}
