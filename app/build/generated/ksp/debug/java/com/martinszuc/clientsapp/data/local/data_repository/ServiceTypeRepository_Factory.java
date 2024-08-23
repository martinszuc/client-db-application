package com.martinszuc.clientsapp.data.local.data_repository;

import com.martinszuc.clientsapp.data.database.dao.ServiceTypeDao;
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
public final class ServiceTypeRepository_Factory implements Factory<ServiceTypeRepository> {
  private final Provider<ServiceTypeDao> daoProvider;

  public ServiceTypeRepository_Factory(Provider<ServiceTypeDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public ServiceTypeRepository get() {
    return newInstance(daoProvider.get());
  }

  public static ServiceTypeRepository_Factory create(Provider<ServiceTypeDao> daoProvider) {
    return new ServiceTypeRepository_Factory(daoProvider);
  }

  public static ServiceTypeRepository newInstance(ServiceTypeDao dao) {
    return new ServiceTypeRepository(dao);
  }
}
