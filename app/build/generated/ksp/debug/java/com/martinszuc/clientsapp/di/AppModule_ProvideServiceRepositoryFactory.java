package com.martinszuc.clientsapp.di;

import com.martinszuc.clientsapp.data.database.dao.ServiceDao;
import com.martinszuc.clientsapp.data.database.dao.ServicePhotoDao;
import com.martinszuc.clientsapp.data.local.data_repository.ServiceRepository;
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
public final class AppModule_ProvideServiceRepositoryFactory implements Factory<ServiceRepository> {
  private final Provider<ServiceDao> serviceDaoProvider;

  private final Provider<ServicePhotoDao> servicePhotoDaoProvider;

  public AppModule_ProvideServiceRepositoryFactory(Provider<ServiceDao> serviceDaoProvider,
      Provider<ServicePhotoDao> servicePhotoDaoProvider) {
    this.serviceDaoProvider = serviceDaoProvider;
    this.servicePhotoDaoProvider = servicePhotoDaoProvider;
  }

  @Override
  public ServiceRepository get() {
    return provideServiceRepository(serviceDaoProvider.get(), servicePhotoDaoProvider.get());
  }

  public static AppModule_ProvideServiceRepositoryFactory create(
      Provider<ServiceDao> serviceDaoProvider, Provider<ServicePhotoDao> servicePhotoDaoProvider) {
    return new AppModule_ProvideServiceRepositoryFactory(serviceDaoProvider, servicePhotoDaoProvider);
  }

  public static ServiceRepository provideServiceRepository(ServiceDao serviceDao,
      ServicePhotoDao servicePhotoDao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideServiceRepository(serviceDao, servicePhotoDao));
  }
}
