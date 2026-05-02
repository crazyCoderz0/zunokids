package com.crazycoderz.zunokids.di;

import android.content.Context;
import com.crazycoderz.zunokids.util.SoundManager;
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
public final class AppModule_Companion_ProvideSoundManagerFactory implements Factory<SoundManager> {
  private final Provider<Context> contextProvider;

  public AppModule_Companion_ProvideSoundManagerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SoundManager get() {
    return provideSoundManager(contextProvider.get());
  }

  public static AppModule_Companion_ProvideSoundManagerFactory create(
      Provider<Context> contextProvider) {
    return new AppModule_Companion_ProvideSoundManagerFactory(contextProvider);
  }

  public static SoundManager provideSoundManager(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.Companion.provideSoundManager(context));
  }
}
