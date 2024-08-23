package com.martinszuc.clientsapp.ui.viewmodel;

import com.martinszuc.clientsapp.data.local.data_repository.ServiceCategoryRepository;
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
public final class ServiceCategoryViewModel_Factory implements Factory<ServiceCategoryViewModel> {
  private final Provider<ServiceCategoryRepository> repositoryProvider;

  public ServiceCategoryViewModel_Factory(Provider<ServiceCategoryRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ServiceCategoryViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ServiceCategoryViewModel_Factory create(
      Provider<ServiceCategoryRepository> repositoryProvider) {
    return new ServiceCategoryViewModel_Factory(repositoryProvider);
  }

  public static ServiceCategoryViewModel newInstance(ServiceCategoryRepository repository) {
    return new ServiceCategoryViewModel(repository);
  }
}
