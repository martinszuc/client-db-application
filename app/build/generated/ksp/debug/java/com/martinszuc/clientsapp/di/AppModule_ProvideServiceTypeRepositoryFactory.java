package com.martinszuc.clientsapp.di;

import com.martinszuc.clientsapp.data.database.dao.ServiceTypeDao;
import com.martinszuc.clientsapp.data.local.data_repository.ServiceTypeRepository;
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
public final class AppModule_ProvideServiceTypeRepositoryFactory implements Factory<ServiceTypeRepository> {
  private final Provider<ServiceTypeDao> daoProvider;

  public AppModule_ProvideServiceTypeRepositoryFactory(Provider<ServiceTypeDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public ServiceTypeRepository get() {
    return provideServiceTypeRepository(daoProvider.get());
  }

  public static AppModule_ProvideServiceTypeRepositoryFactory create(
      Provider<ServiceTypeDao> daoProvider) {
    return new AppModule_ProvideServiceTypeRepositoryFactory(daoProvider);
  }

  public static ServiceTypeRepository provideServiceTypeRepository(ServiceTypeDao dao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideServiceTypeRepository(dao));
  }
}
