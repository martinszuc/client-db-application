package com.martinszuc.clientsapp.ui.viewmodel;

import com.martinszuc.clientsapp.data.local.data_repository.ServiceTypeRepository;
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
public final class ServiceTypeViewModel_Factory implements Factory<ServiceTypeViewModel> {
  private final Provider<ServiceTypeRepository> repositoryProvider;

  public ServiceTypeViewModel_Factory(Provider<ServiceTypeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ServiceTypeViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ServiceTypeViewModel_Factory create(
      Provider<ServiceTypeRepository> repositoryProvider) {
    return new ServiceTypeViewModel_Factory(repositoryProvider);
  }

  public static ServiceTypeViewModel newInstance(ServiceTypeRepository repository) {
    return new ServiceTypeViewModel(repository);
  }
}
