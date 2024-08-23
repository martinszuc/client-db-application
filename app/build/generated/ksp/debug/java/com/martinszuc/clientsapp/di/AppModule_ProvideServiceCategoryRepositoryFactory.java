package com.martinszuc.clientsapp.di;

import com.martinszuc.clientsapp.data.database.dao.ServiceCategoryDao;
import com.martinszuc.clientsapp.data.local.data_repository.ServiceCategoryRepository;
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
public final class AppModule_ProvideServiceCategoryRepositoryFactory implements Factory<ServiceCategoryRepository> {
  private final Provider<ServiceCategoryDao> daoProvider;

  public AppModule_ProvideServiceCategoryRepositoryFactory(
      Provider<ServiceCategoryDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public ServiceCategoryRepository get() {
    return provideServiceCategoryRepository(daoProvider.get());
  }

  public static AppModule_ProvideServiceCategoryRepositoryFactory create(
      Provider<ServiceCategoryDao> daoProvider) {
    return new AppModule_ProvideServiceCategoryRepositoryFactory(daoProvider);
  }

  public static ServiceCategoryRepository provideServiceCategoryRepository(ServiceCategoryDao dao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideServiceCategoryRepository(dao));
  }
}
