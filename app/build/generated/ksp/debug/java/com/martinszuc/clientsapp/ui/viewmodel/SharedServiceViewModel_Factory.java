package com.martinszuc.clientsapp.ui.viewmodel;

import com.martinszuc.clientsapp.data.local.LocalStorageRepository;
import com.martinszuc.clientsapp.data.remote.FirebaseStorageRepository;
import com.martinszuc.clientsapp.data.local.data_repository.ServiceRepository;
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
public final class SharedServiceViewModel_Factory implements Factory<SharedServiceViewModel> {
  private final Provider<ServiceRepository> serviceRepositoryProvider;

  private final Provider<LocalStorageRepository> localStorageRepositoryProvider;

  private final Provider<FirebaseStorageRepository> firebaseStorageRepositoryProvider;

  public SharedServiceViewModel_Factory(Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<LocalStorageRepository> localStorageRepositoryProvider,
      Provider<FirebaseStorageRepository> firebaseStorageRepositoryProvider) {
    this.serviceRepositoryProvider = serviceRepositoryProvider;
    this.localStorageRepositoryProvider = localStorageRepositoryProvider;
    this.firebaseStorageRepositoryProvider = firebaseStorageRepositoryProvider;
  }

  @Override
  public SharedServiceViewModel get() {
    return newInstance(serviceRepositoryProvider.get(), localStorageRepositoryProvider.get(), firebaseStorageRepositoryProvider.get());
  }

  public static SharedServiceViewModel_Factory create(
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<LocalStorageRepository> localStorageRepositoryProvider,
      Provider<FirebaseStorageRepository> firebaseStorageRepositoryProvider) {
    return new SharedServiceViewModel_Factory(serviceRepositoryProvider, localStorageRepositoryProvider, firebaseStorageRepositoryProvider);
  }

  public static SharedServiceViewModel newInstance(ServiceRepository serviceRepository,
      LocalStorageRepository localStorageRepository,
      FirebaseStorageRepository firebaseStorageRepository) {
    return new SharedServiceViewModel(serviceRepository, localStorageRepository, firebaseStorageRepository);
  }
}
