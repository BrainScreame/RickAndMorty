package com.osenov.rickandmorty.di.module

import androidx.lifecycle.ViewModel
import com.osenov.rickandmorty.data.remote.CharacterRemoteDataSource
import com.osenov.rickandmorty.di.scope.ViewModelKey
import com.osenov.rickandmorty.domain.GetSingleCharacterUseCase
import com.osenov.rickandmorty.ui.character_information.CharacterDetailInformationViewModel
import com.osenov.rickandmorty.ui.list_characters.CharactersListViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {

    @IntoMap
    @ViewModelKey(CharactersListViewModel::class)
    @Provides
    fun provideCharactersListViewModel(characterRemoteDataSource: CharacterRemoteDataSource): ViewModel {
        return CharactersListViewModel(characterRemoteDataSource)
    }

    @IntoMap
    @ViewModelKey(CharacterDetailInformationViewModel::class)
    @Provides
    fun provideCharacterDetailInformationViewModel(getSingleCharacterUseCase: GetSingleCharacterUseCase): ViewModel {
        return CharacterDetailInformationViewModel(getSingleCharacterUseCase)
    }

}