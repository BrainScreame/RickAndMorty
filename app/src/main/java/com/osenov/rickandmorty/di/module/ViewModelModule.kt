package com.osenov.rickandmorty.di.module

import androidx.lifecycle.ViewModel
import com.osenov.rickandmorty.data.remote.CharacterRemoteDataSource
import com.osenov.rickandmorty.di.scope.ViewModelKey
import com.osenov.rickandmorty.domain.GetCharacterEpisodesUseCase
import com.osenov.rickandmorty.domain.GetQueryCharactersUseCase
import com.osenov.rickandmorty.domain.GetSingleCharacterUseCase
import com.osenov.rickandmorty.ui.character_information.CharacterDetailInformationViewModel
import com.osenov.rickandmorty.ui.list_characters.CharactersListViewModel
import com.osenov.rickandmorty.ui.list_episodes.EpisodesListViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
class ViewModelModule {

    @IntoMap
    @ViewModelKey(CharactersListViewModel::class)
    @Provides
    fun provideCharactersListViewModel(getQueryCharactersUseCase: Provider<GetQueryCharactersUseCase>): ViewModel {
        return CharactersListViewModel(getQueryCharactersUseCase)
    }

//    @IntoMap
//    @ViewModelKey(CharacterDetailInformationViewModel::class)
//    @Provides
//    fun provideCharacterDetailInformationViewModel(getSingleCharacterUseCase: GetSingleCharacterUseCase): ViewModel {
//        return CharacterDetailInformationViewModel(getSingleCharacterUseCase)
//    }
//
//    @IntoMap
//    @ViewModelKey(EpisodesListViewModel::class)
//    @Provides
//    fun provideEpisodesListViewModelViewModel(getCharacterEpisodesUseCase: GetCharacterEpisodesUseCase): ViewModel {
//        return EpisodesListViewModel(getCharacterEpisodesUseCase)
//    }

}