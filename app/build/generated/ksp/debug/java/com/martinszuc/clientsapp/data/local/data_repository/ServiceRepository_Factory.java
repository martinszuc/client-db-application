package com.martinszuc.clientsapp.data.local.data_repository;

import com.martinszuc.clientsapp.data.database.dao.ServiceDao;
import com.martinszuc.clientsapp.data.database.dao.ServicePhotoDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ServiceRepository_Factory implements Factory<ServiceRepository> {
  private final Provider<ServiceDao> serviceDaoProvider;

  private final Provider<ServicePhotoDao> servicePhotoDaoProvider;

  public ServiceRepository_Factory(Provider<ServiceDao> serviceDaoProvider,
      Provider<ServicePhotoDao> servicePhotoDaoProvider) {
    this.serviceDaoProvider = serviceDaoProvider;
    this.servicePhotoDaoProvider = servicePhotoDaoProvider;
  }

  @Override
  public ServiceRepository get() {
    return newInstance(serviceDaoProvider.get(), servicePhotoDaoProvider.get());
  }

  public static ServiceRepository_Factory create(Provider<ServiceDao> serviceDaoProvider,
      Provider<ServicePhotoDao> servicePhotoDaoProvider) {
    return new ServiceRepository_Factory(serviceDaoProvider, servicePhotoDaoProvider);
  }

  public static ServiceRepository newInstance(ServiceDao serviceDao,
      ServicePhotoDao servicePhotoDao) {
    return new ServiceRepository(serviceDao, servicePhotoDao);
  }
}
