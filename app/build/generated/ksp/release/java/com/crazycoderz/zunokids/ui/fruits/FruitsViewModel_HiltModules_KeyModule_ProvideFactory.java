package com.crazycoderz.zunokids.ui.fruits;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.internal.lifecycle.HiltViewModelMap.KeySet")
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
public final class FruitsViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<Boolean> {
  @Override
  public Boolean get() {
    return provide();
  }

  public static FruitsViewModel_HiltModules_KeyModule_ProvideFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static boolean provide() {
    return FruitsViewModel_HiltModules.KeyModule.provide();
  }

  private static final class InstanceHolder {
    private static final FruitsViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new FruitsViewModel_HiltModules_KeyModule_ProvideFactory();
  }
}
