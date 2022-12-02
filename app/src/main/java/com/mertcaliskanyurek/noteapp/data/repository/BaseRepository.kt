package com.mertcaliskanyurek.noteapp.data.repository

import com.mertcaliskanyurek.noteapp.data.source.BaseLocalDataSource

abstract class BaseRepository<T>(private val localDataSource: BaseLocalDataSource<T>) {
    fun add(entity: T) = localDataSource.add(entity)
    fun update(entity: T) = localDataSource.update(entity)
    fun remove(entity: T) = localDataSource.remove(entity)
}