package com.martinszuc.clientsapp.ui.viewmodel;

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
public final class ServicePicturesViewModel_Factory implements Factory<ServicePicturesViewModel> {
  private final Provider<ServiceRepository> serviceRepositoryProvider;

  public ServicePicturesViewModel_Factory(Provider<ServiceRepository> serviceRepositoryProvider) {
    this.serviceRepositoryProvider = serviceRepositoryProvider;
  }

  @Override
  public ServicePicturesViewModel get() {
    return newInstance(serviceRepositoryProvider.get());
  }

  public static ServicePicturesViewModel_Factory create(
      Provider<ServiceRepository> serviceRepositoryProvider) {
    return new ServicePicturesViewModel_Factory(serviceRepositoryProvider);
  }

  public static ServicePicturesViewModel newInstance(ServiceRepository serviceRepository) {
    return new ServicePicturesViewModel(serviceRepository);
  }
}
