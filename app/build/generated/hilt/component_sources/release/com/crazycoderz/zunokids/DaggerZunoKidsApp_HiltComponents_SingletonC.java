package com.crazycoderz.zunokids;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.crazycoderz.zunokids.data.TtsManager;
import com.crazycoderz.zunokids.data.UserPreferencesRepo;
import com.crazycoderz.zunokids.data.repository.AuthRepositoryImpl;
import com.crazycoderz.zunokids.di.AppModule_Companion_ProvideFirebaseAuthFactory;
import com.crazycoderz.zunokids.di.AppModule_Companion_ProvideSoundManagerFactory;
import com.crazycoderz.zunokids.di.AppModule_Companion_ProvideTtsManagerFactory;
import com.crazycoderz.zunokids.di.AppModule_Companion_ProvideUserPreferencesRepoFactory;
import com.crazycoderz.zunokids.ui.alphabet.AlphabetViewModel;
import com.crazycoderz.zunokids.ui.alphabet.AlphabetViewModel_HiltModules;
import com.crazycoderz.zunokids.ui.auth.AuthViewModel;
import com.crazycoderz.zunokids.ui.auth.AuthViewModel_HiltModules;
import com.crazycoderz.zunokids.ui.colors.ColorsViewModel;
import com.crazycoderz.zunokids.ui.colors.ColorsViewModel_HiltModules;
import com.crazycoderz.zunokids.ui.fruits.FruitsViewModel;
import com.crazycoderz.zunokids.ui.fruits.FruitsViewModel_HiltModules;
import com.crazycoderz.zunokids.ui.games.MatchingGameViewModel;
import com.crazycoderz.zunokids.ui.games.MatchingGameViewModel_HiltModules;
import com.crazycoderz.zunokids.ui.games.QuizViewModel;
import com.crazycoderz.zunokids.ui.games.QuizViewModel_HiltModules;
import com.crazycoderz.zunokids.ui.games.viewmodel.CategoryViewModel;
import com.crazycoderz.zunokids.ui.games.viewmodel.CategoryViewModel_HiltModules;
import com.crazycoderz.zunokids.ui.games.viewmodel.DragMatchViewModel;
import com.crazycoderz.zunokids.ui.games.viewmodel.DragMatchViewModel_HiltModules;
import com.crazycoderz.zunokids.ui.games.viewmodel.MissingLetterViewModel;
import com.crazycoderz.zunokids.ui.games.viewmodel.MissingLetterViewModel_HiltModules;
import com.crazycoderz.zunokids.ui.games.viewmodel.ShapeMatchViewModel;
import com.crazycoderz.zunokids.ui.games.viewmodel.ShapeMatchViewModel_HiltModules;
import com.crazycoderz.zunokids.ui.home.HomeViewModel;
import com.crazycoderz.zunokids.ui.home.HomeViewModel_HiltModules;
import com.crazycoderz.zunokids.ui.numbers.NumbersViewModel;
import com.crazycoderz.zunokids.ui.numbers.NumbersViewModel_HiltModules;
import com.crazycoderz.zunokids.ui.objects.ObjectsViewModel;
import com.crazycoderz.zunokids.ui.objects.ObjectsViewModel_HiltModules;
import com.crazycoderz.zunokids.ui.parent.ParentViewModel;
import com.crazycoderz.zunokids.ui.parent.ParentViewModel_HiltModules;
import com.crazycoderz.zunokids.util.SoundManager;
import com.google.firebase.auth.FirebaseAuth;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerZunoKidsApp_HiltComponents_SingletonC {
  private DaggerZunoKidsApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public ZunoKidsApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements ZunoKidsApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public ZunoKidsApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements ZunoKidsApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public ZunoKidsApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements ZunoKidsApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public ZunoKidsApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements ZunoKidsApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public ZunoKidsApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements ZunoKidsApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public ZunoKidsApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements ZunoKidsApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public ZunoKidsApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements ZunoKidsApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public ZunoKidsApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends ZunoKidsApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends ZunoKidsApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends ZunoKidsApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends ZunoKidsApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(14).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_alphabet_AlphabetViewModel, AlphabetViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_auth_AuthViewModel, AuthViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_games_viewmodel_CategoryViewModel, CategoryViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_colors_ColorsViewModel, ColorsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_games_viewmodel_DragMatchViewModel, DragMatchViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_fruits_FruitsViewModel, FruitsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_home_HomeViewModel, HomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_games_MatchingGameViewModel, MatchingGameViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_games_viewmodel_MissingLetterViewModel, MissingLetterViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_numbers_NumbersViewModel, NumbersViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_objects_ObjectsViewModel, ObjectsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_parent_ParentViewModel, ParentViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_games_QuizViewModel, QuizViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_games_viewmodel_ShapeMatchViewModel, ShapeMatchViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_crazycoderz_zunokids_ui_auth_AuthViewModel = "com.crazycoderz.zunokids.ui.auth.AuthViewModel";

      static String com_crazycoderz_zunokids_ui_colors_ColorsViewModel = "com.crazycoderz.zunokids.ui.colors.ColorsViewModel";

      static String com_crazycoderz_zunokids_ui_fruits_FruitsViewModel = "com.crazycoderz.zunokids.ui.fruits.FruitsViewModel";

      static String com_crazycoderz_zunokids_ui_alphabet_AlphabetViewModel = "com.crazycoderz.zunokids.ui.alphabet.AlphabetViewModel";

      static String com_crazycoderz_zunokids_ui_games_MatchingGameViewModel = "com.crazycoderz.zunokids.ui.games.MatchingGameViewModel";

      static String com_crazycoderz_zunokids_ui_objects_ObjectsViewModel = "com.crazycoderz.zunokids.ui.objects.ObjectsViewModel";

      static String com_crazycoderz_zunokids_ui_parent_ParentViewModel = "com.crazycoderz.zunokids.ui.parent.ParentViewModel";

      static String com_crazycoderz_zunokids_ui_games_QuizViewModel = "com.crazycoderz.zunokids.ui.games.QuizViewModel";

      static String com_crazycoderz_zunokids_ui_games_viewmodel_CategoryViewModel = "com.crazycoderz.zunokids.ui.games.viewmodel.CategoryViewModel";

      static String com_crazycoderz_zunokids_ui_numbers_NumbersViewModel = "com.crazycoderz.zunokids.ui.numbers.NumbersViewModel";

      static String com_crazycoderz_zunokids_ui_home_HomeViewModel = "com.crazycoderz.zunokids.ui.home.HomeViewModel";

      static String com_crazycoderz_zunokids_ui_games_viewmodel_MissingLetterViewModel = "com.crazycoderz.zunokids.ui.games.viewmodel.MissingLetterViewModel";

      static String com_crazycoderz_zunokids_ui_games_viewmodel_DragMatchViewModel = "com.crazycoderz.zunokids.ui.games.viewmodel.DragMatchViewModel";

      static String com_crazycoderz_zunokids_ui_games_viewmodel_ShapeMatchViewModel = "com.crazycoderz.zunokids.ui.games.viewmodel.ShapeMatchViewModel";

      @KeepFieldType
      AuthViewModel com_crazycoderz_zunokids_ui_auth_AuthViewModel2;

      @KeepFieldType
      ColorsViewModel com_crazycoderz_zunokids_ui_colors_ColorsViewModel2;

      @KeepFieldType
      FruitsViewModel com_crazycoderz_zunokids_ui_fruits_FruitsViewModel2;

      @KeepFieldType
      AlphabetViewModel com_crazycoderz_zunokids_ui_alphabet_AlphabetViewModel2;

      @KeepFieldType
      MatchingGameViewModel com_crazycoderz_zunokids_ui_games_MatchingGameViewModel2;

      @KeepFieldType
      ObjectsViewModel com_crazycoderz_zunokids_ui_objects_ObjectsViewModel2;

      @KeepFieldType
      ParentViewModel com_crazycoderz_zunokids_ui_parent_ParentViewModel2;

      @KeepFieldType
      QuizViewModel com_crazycoderz_zunokids_ui_games_QuizViewModel2;

      @KeepFieldType
      CategoryViewModel com_crazycoderz_zunokids_ui_games_viewmodel_CategoryViewModel2;

      @KeepFieldType
      NumbersViewModel com_crazycoderz_zunokids_ui_numbers_NumbersViewModel2;

      @KeepFieldType
      HomeViewModel com_crazycoderz_zunokids_ui_home_HomeViewModel2;

      @KeepFieldType
      MissingLetterViewModel com_crazycoderz_zunokids_ui_games_viewmodel_MissingLetterViewModel2;

      @KeepFieldType
      DragMatchViewModel com_crazycoderz_zunokids_ui_games_viewmodel_DragMatchViewModel2;

      @KeepFieldType
      ShapeMatchViewModel com_crazycoderz_zunokids_ui_games_viewmodel_ShapeMatchViewModel2;
    }
  }

  private static final class ViewModelCImpl extends ZunoKidsApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AlphabetViewModel> alphabetViewModelProvider;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<CategoryViewModel> categoryViewModelProvider;

    private Provider<ColorsViewModel> colorsViewModelProvider;

    private Provider<DragMatchViewModel> dragMatchViewModelProvider;

    private Provider<FruitsViewModel> fruitsViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<MatchingGameViewModel> matchingGameViewModelProvider;

    private Provider<MissingLetterViewModel> missingLetterViewModelProvider;

    private Provider<NumbersViewModel> numbersViewModelProvider;

    private Provider<ObjectsViewModel> objectsViewModelProvider;

    private Provider<ParentViewModel> parentViewModelProvider;

    private Provider<QuizViewModel> quizViewModelProvider;

    private Provider<ShapeMatchViewModel> shapeMatchViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.alphabetViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.categoryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.colorsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.dragMatchViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.fruitsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.matchingGameViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.missingLetterViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.numbersViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.objectsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.parentViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
      this.quizViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 12);
      this.shapeMatchViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 13);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(14).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_alphabet_AlphabetViewModel, ((Provider) alphabetViewModelProvider)).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_auth_AuthViewModel, ((Provider) authViewModelProvider)).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_games_viewmodel_CategoryViewModel, ((Provider) categoryViewModelProvider)).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_colors_ColorsViewModel, ((Provider) colorsViewModelProvider)).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_games_viewmodel_DragMatchViewModel, ((Provider) dragMatchViewModelProvider)).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_fruits_FruitsViewModel, ((Provider) fruitsViewModelProvider)).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_home_HomeViewModel, ((Provider) homeViewModelProvider)).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_games_MatchingGameViewModel, ((Provider) matchingGameViewModelProvider)).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_games_viewmodel_MissingLetterViewModel, ((Provider) missingLetterViewModelProvider)).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_numbers_NumbersViewModel, ((Provider) numbersViewModelProvider)).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_objects_ObjectsViewModel, ((Provider) objectsViewModelProvider)).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_parent_ParentViewModel, ((Provider) parentViewModelProvider)).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_games_QuizViewModel, ((Provider) quizViewModelProvider)).put(LazyClassKeyProvider.com_crazycoderz_zunokids_ui_games_viewmodel_ShapeMatchViewModel, ((Provider) shapeMatchViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_crazycoderz_zunokids_ui_auth_AuthViewModel = "com.crazycoderz.zunokids.ui.auth.AuthViewModel";

      static String com_crazycoderz_zunokids_ui_games_viewmodel_ShapeMatchViewModel = "com.crazycoderz.zunokids.ui.games.viewmodel.ShapeMatchViewModel";

      static String com_crazycoderz_zunokids_ui_parent_ParentViewModel = "com.crazycoderz.zunokids.ui.parent.ParentViewModel";

      static String com_crazycoderz_zunokids_ui_alphabet_AlphabetViewModel = "com.crazycoderz.zunokids.ui.alphabet.AlphabetViewModel";

      static String com_crazycoderz_zunokids_ui_games_viewmodel_CategoryViewModel = "com.crazycoderz.zunokids.ui.games.viewmodel.CategoryViewModel";

      static String com_crazycoderz_zunokids_ui_fruits_FruitsViewModel = "com.crazycoderz.zunokids.ui.fruits.FruitsViewModel";

      static String com_crazycoderz_zunokids_ui_games_viewmodel_MissingLetterViewModel = "com.crazycoderz.zunokids.ui.games.viewmodel.MissingLetterViewModel";

      static String com_crazycoderz_zunokids_ui_objects_ObjectsViewModel = "com.crazycoderz.zunokids.ui.objects.ObjectsViewModel";

      static String com_crazycoderz_zunokids_ui_colors_ColorsViewModel = "com.crazycoderz.zunokids.ui.colors.ColorsViewModel";

      static String com_crazycoderz_zunokids_ui_games_QuizViewModel = "com.crazycoderz.zunokids.ui.games.QuizViewModel";

      static String com_crazycoderz_zunokids_ui_games_MatchingGameViewModel = "com.crazycoderz.zunokids.ui.games.MatchingGameViewModel";

      static String com_crazycoderz_zunokids_ui_games_viewmodel_DragMatchViewModel = "com.crazycoderz.zunokids.ui.games.viewmodel.DragMatchViewModel";

      static String com_crazycoderz_zunokids_ui_numbers_NumbersViewModel = "com.crazycoderz.zunokids.ui.numbers.NumbersViewModel";

      static String com_crazycoderz_zunokids_ui_home_HomeViewModel = "com.crazycoderz.zunokids.ui.home.HomeViewModel";

      @KeepFieldType
      AuthViewModel com_crazycoderz_zunokids_ui_auth_AuthViewModel2;

      @KeepFieldType
      ShapeMatchViewModel com_crazycoderz_zunokids_ui_games_viewmodel_ShapeMatchViewModel2;

      @KeepFieldType
      ParentViewModel com_crazycoderz_zunokids_ui_parent_ParentViewModel2;

      @KeepFieldType
      AlphabetViewModel com_crazycoderz_zunokids_ui_alphabet_AlphabetViewModel2;

      @KeepFieldType
      CategoryViewModel com_crazycoderz_zunokids_ui_games_viewmodel_CategoryViewModel2;

      @KeepFieldType
      FruitsViewModel com_crazycoderz_zunokids_ui_fruits_FruitsViewModel2;

      @KeepFieldType
      MissingLetterViewModel com_crazycoderz_zunokids_ui_games_viewmodel_MissingLetterViewModel2;

      @KeepFieldType
      ObjectsViewModel com_crazycoderz_zunokids_ui_objects_ObjectsViewModel2;

      @KeepFieldType
      ColorsViewModel com_crazycoderz_zunokids_ui_colors_ColorsViewModel2;

      @KeepFieldType
      QuizViewModel com_crazycoderz_zunokids_ui_games_QuizViewModel2;

      @KeepFieldType
      MatchingGameViewModel com_crazycoderz_zunokids_ui_games_MatchingGameViewModel2;

      @KeepFieldType
      DragMatchViewModel com_crazycoderz_zunokids_ui_games_viewmodel_DragMatchViewModel2;

      @KeepFieldType
      NumbersViewModel com_crazycoderz_zunokids_ui_numbers_NumbersViewModel2;

      @KeepFieldType
      HomeViewModel com_crazycoderz_zunokids_ui_home_HomeViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.crazycoderz.zunokids.ui.alphabet.AlphabetViewModel 
          return (T) new AlphabetViewModel(singletonCImpl.provideUserPreferencesRepoProvider.get());

          case 1: // com.crazycoderz.zunokids.ui.auth.AuthViewModel 
          return (T) new AuthViewModel(singletonCImpl.authRepositoryImplProvider.get());

          case 2: // com.crazycoderz.zunokids.ui.games.viewmodel.CategoryViewModel 
          return (T) new CategoryViewModel(singletonCImpl.provideUserPreferencesRepoProvider.get(), singletonCImpl.provideSoundManagerProvider.get(), singletonCImpl.provideTtsManagerProvider.get());

          case 3: // com.crazycoderz.zunokids.ui.colors.ColorsViewModel 
          return (T) new ColorsViewModel(singletonCImpl.provideUserPreferencesRepoProvider.get());

          case 4: // com.crazycoderz.zunokids.ui.games.viewmodel.DragMatchViewModel 
          return (T) new DragMatchViewModel(singletonCImpl.provideUserPreferencesRepoProvider.get(), singletonCImpl.provideSoundManagerProvider.get());

          case 5: // com.crazycoderz.zunokids.ui.fruits.FruitsViewModel 
          return (T) new FruitsViewModel(singletonCImpl.provideUserPreferencesRepoProvider.get());

          case 6: // com.crazycoderz.zunokids.ui.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.provideUserPreferencesRepoProvider.get());

          case 7: // com.crazycoderz.zunokids.ui.games.MatchingGameViewModel 
          return (T) new MatchingGameViewModel(singletonCImpl.provideUserPreferencesRepoProvider.get(), singletonCImpl.provideSoundManagerProvider.get(), singletonCImpl.provideTtsManagerProvider.get());

          case 8: // com.crazycoderz.zunokids.ui.games.viewmodel.MissingLetterViewModel 
          return (T) new MissingLetterViewModel(singletonCImpl.provideUserPreferencesRepoProvider.get(), singletonCImpl.provideSoundManagerProvider.get(), singletonCImpl.provideTtsManagerProvider.get());

          case 9: // com.crazycoderz.zunokids.ui.numbers.NumbersViewModel 
          return (T) new NumbersViewModel(singletonCImpl.provideUserPreferencesRepoProvider.get());

          case 10: // com.crazycoderz.zunokids.ui.objects.ObjectsViewModel 
          return (T) new ObjectsViewModel(singletonCImpl.provideUserPreferencesRepoProvider.get());

          case 11: // com.crazycoderz.zunokids.ui.parent.ParentViewModel 
          return (T) new ParentViewModel(singletonCImpl.provideUserPreferencesRepoProvider.get());

          case 12: // com.crazycoderz.zunokids.ui.games.QuizViewModel 
          return (T) new QuizViewModel(singletonCImpl.provideUserPreferencesRepoProvider.get(), singletonCImpl.provideSoundManagerProvider.get(), singletonCImpl.provideTtsManagerProvider.get());

          case 13: // com.crazycoderz.zunokids.ui.games.viewmodel.ShapeMatchViewModel 
          return (T) new ShapeMatchViewModel(singletonCImpl.provideUserPreferencesRepoProvider.get(), singletonCImpl.provideSoundManagerProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends ZunoKidsApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends ZunoKidsApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends ZunoKidsApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<UserPreferencesRepo> provideUserPreferencesRepoProvider;

    private Provider<FirebaseAuth> provideFirebaseAuthProvider;

    private Provider<AuthRepositoryImpl> authRepositoryImplProvider;

    private Provider<SoundManager> provideSoundManagerProvider;

    private Provider<TtsManager> provideTtsManagerProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideUserPreferencesRepoProvider = DoubleCheck.provider(new SwitchingProvider<UserPreferencesRepo>(singletonCImpl, 0));
      this.provideFirebaseAuthProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseAuth>(singletonCImpl, 2));
      this.authRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepositoryImpl>(singletonCImpl, 1));
      this.provideSoundManagerProvider = DoubleCheck.provider(new SwitchingProvider<SoundManager>(singletonCImpl, 3));
      this.provideTtsManagerProvider = DoubleCheck.provider(new SwitchingProvider<TtsManager>(singletonCImpl, 4));
    }

    @Override
    public void injectZunoKidsApp(ZunoKidsApp arg0) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.crazycoderz.zunokids.data.UserPreferencesRepo 
          return (T) AppModule_Companion_ProvideUserPreferencesRepoFactory.provideUserPreferencesRepo(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 1: // com.crazycoderz.zunokids.data.repository.AuthRepositoryImpl 
          return (T) new AuthRepositoryImpl(singletonCImpl.provideFirebaseAuthProvider.get());

          case 2: // com.google.firebase.auth.FirebaseAuth 
          return (T) AppModule_Companion_ProvideFirebaseAuthFactory.provideFirebaseAuth();

          case 3: // com.crazycoderz.zunokids.util.SoundManager 
          return (T) AppModule_Companion_ProvideSoundManagerFactory.provideSoundManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.crazycoderz.zunokids.data.TtsManager 
          return (T) AppModule_Companion_ProvideTtsManagerFactory.provideTtsManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
