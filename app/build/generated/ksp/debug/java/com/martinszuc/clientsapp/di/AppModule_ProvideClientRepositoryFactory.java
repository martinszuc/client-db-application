package com.martinszuc.clientsapp.di;

import com.martinszuc.clientsapp.data.database.dao.ClientDao;
import com.martinszuc.clientsapp.data.local.data_repository.ClientRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideClientRepositoryFactory implements Factory<ClientRepository> {
  private final Provider<ClientDao> clientDaoProvider;

  public AppModule_ProvideClientRepositoryFactory(Provider<ClientDao> clientDaoProvider) {
    this.clientDaoProvider = clientDaoProvider;
  }

  @Override
  public ClientRepository get() {
    return provideClientRepository(clientDaoProvider.get());
  }

  public static AppModule_ProvideClientRepositoryFactory create(
      Provider<ClientDao> clientDaoProvider) {
    return new AppModule_ProvideClientRepositoryFactory(clientDaoProvider);
  }

  public static ClientRepository provideClientRepository(ClientDao clientDao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideClientRepository(clientDao));
  }
}
