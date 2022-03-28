package com.osenov.rickandmorty.di.module

import com.osenov.rickandmorty.data.repository.CharacterRepository
import com.osenov.rickandmorty.data.repository.DefaultCharacterRepository
import dagger.Binds
import dagger.Module

@Module
interface AppBindsModule {

    @Suppress("FunctionName")
    @Binds
    fun bindDefaultCharacterRepository_to_CharacterRepository(
        characterRepository: DefaultCharacterRepository
    ): CharacterRepository

}