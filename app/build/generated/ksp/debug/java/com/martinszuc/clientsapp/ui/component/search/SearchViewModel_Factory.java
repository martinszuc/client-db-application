package com.martinszuc.clientsapp.ui.component.search;

import com.martinszuc.clientsapp.data.local.data_repository.ClientRepository;
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
public final class SearchViewModel_Factory implements Factory<SearchViewModel> {
  private final Provider<ClientRepository> clientRepositoryProvider;

  private final Provider<ServiceRepository> serviceRepositoryProvider;

  public SearchViewModel_Factory(Provider<ClientRepository> clientRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider) {
    this.clientRepositoryProvider = clientRepositoryProvider;
    this.serviceRepositoryProvider = serviceRepositoryProvider;
  }

  @Override
  public SearchViewModel get() {
    return newInstance(clientRepositoryProvider.get(), serviceRepositoryProvider.get());
  }

  public static SearchViewModel_Factory create(Provider<ClientRepository> clientRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider) {
    return new SearchViewModel_Factory(clientRepositoryProvider, serviceRepositoryProvider);
  }

  public static SearchViewModel newInstance(ClientRepository clientRepository,
      ServiceRepository serviceRepository) {
    return new SearchViewModel(clientRepository, serviceRepository);
  }
}
