package uz.gita.musicplayerapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.musicplayerapp.data.source.local.ContentResolverHelper
import uz.gita.musicplayerapp.data.source.local.impl.ContentResolverHelperImpl
import uz.gita.musicplayerapp.domain.repository.AudioRepository
import uz.gita.musicplayerapp.domain.repository.impl.AudioRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun bindAudioRepository(imp: AudioRepositoryImpl): AudioRepository

    @[Binds Singleton]
    fun bindContentResolverHelper(imp: ContentResolverHelperImpl): ContentResolverHelper
}