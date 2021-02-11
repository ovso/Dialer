package io.github.ovso.dialer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.github.ovso.dialer.data.DialerRepository
import io.github.ovso.dialer.data.DialerRepositoryImpl
import io.github.ovso.dialer.data.HomeRepository
import io.github.ovso.dialer.data.HomeRepositoryImpl
import io.github.ovso.dialer.data.local.LocalDataSource
import io.github.ovso.dialer.data.local.LocalDataSourceImpl

@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryModule {

  @Binds
  abstract fun bindHomeRepository(homeRepository: HomeRepositoryImpl): HomeRepository

  @Binds
  abstract fun bindLocalDataSource(localDataSource: LocalDataSourceImpl): LocalDataSource

  @Binds
  abstract fun bindDialerRepository(dialerRepository: DialerRepositoryImpl): DialerRepository
}
