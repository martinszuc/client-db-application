package com.martinszuc.clientsapp.ui.viewmodel;

import com.martinszuc.clientsapp.data.local.data_repository.ClientRepository;
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
public final class SharedClientViewModel_Factory implements Factory<SharedClientViewModel> {
  private final Provider<ClientRepository> clientRepositoryProvider;

  public SharedClientViewModel_Factory(Provider<ClientRepository> clientRepositoryProvider) {
    this.clientRepositoryProvider = clientRepositoryProvider;
  }

  @Override
  public SharedClientViewModel get() {
    return newInstance(clientRepositoryProvider.get());
  }

  public static SharedClientViewModel_Factory create(
      Provider<ClientRepository> clientRepositoryProvider) {
    return new SharedClientViewModel_Factory(clientRepositoryProvider);
  }

  public static SharedClientViewModel newInstance(ClientRepository clientRepository) {
    return new SharedClientViewModel(clientRepository);
  }
}
