package uz.gita.musicplayerapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.musicplayerapp.presentor.screen.home.MainDirection
import uz.gita.musicplayerapp.presentor.screen.home.MainDirectionImpl
import uz.gita.musicplayerapp.presentor.screen.play.PlayDirection
import uz.gita.musicplayerapp.presentor.screen.play.PlayDirectionImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DirectionModule {

    @[Binds Singleton]
    fun bindMainDirection(impl: MainDirectionImpl): MainDirection

    @[Binds Singleton]
    fun bindPlayDirection(impl: PlayDirectionImpl): PlayDirection
}