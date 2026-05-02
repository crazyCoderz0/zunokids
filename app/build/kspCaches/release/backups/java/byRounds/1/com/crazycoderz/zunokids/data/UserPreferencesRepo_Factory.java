package com.crazycoderz.zunokids.data;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class UserPreferencesRepo_Factory implements Factory<UserPreferencesRepo> {
  private final Provider<Context> contextProvider;

  public UserPreferencesRepo_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public UserPreferencesRepo get() {
    return newInstance(contextProvider.get());
  }

  public static UserPreferencesRepo_Factory create(Provider<Context> contextProvider) {
    return new UserPreferencesRepo_Factory(contextProvider);
  }

  public static UserPreferencesRepo newInstance(Context context) {
    return new UserPreferencesRepo(context);
  }
}
