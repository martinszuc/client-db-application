package com.martinszuc.clientsapp.data.local.data_repository;

import com.martinszuc.clientsapp.data.database.dao.ServiceCategoryDao;
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
public final class ServiceCategoryRepository_Factory implements Factory<ServiceCategoryRepository> {
  private final Provider<ServiceCategoryDao> daoProvider;

  public ServiceCategoryRepository_Factory(Provider<ServiceCategoryDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public ServiceCategoryRepository get() {
    return newInstance(daoProvider.get());
  }

  public static ServiceCategoryRepository_Factory create(Provider<ServiceCategoryDao> daoProvider) {
    return new ServiceCategoryRepository_Factory(daoProvider);
  }

  public static ServiceCategoryRepository newInstance(ServiceCategoryDao dao) {
    return new ServiceCategoryRepository(dao);
  }
}
