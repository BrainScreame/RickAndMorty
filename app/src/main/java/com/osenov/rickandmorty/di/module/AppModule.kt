package com.osenov.rickandmorty.di.module

import dagger.Module

@Module(includes = [ViewModelModule::class, NetworkModule::class, DatabaseModule::class])
class AppModule {
}