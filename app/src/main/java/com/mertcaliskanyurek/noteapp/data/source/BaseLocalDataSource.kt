package com.mertcaliskanyurek.noteapp.data.source

import com.mertcaliskanyurek.noteapp.data.dao.BaseRoomDao

abstract class BaseLocalDataSource<T>(private val baseRoomDao: BaseRoomDao<T>) {

    fun add(entity: T): SourceResult<T> = try {
        baseRoomDao.insert(entity)
        SourceResult.Success(entity)
    }catch (e: Exception) {
        SourceResult.Failure(e)
    }

    fun update(entity: T): SourceResult<T> = try {
        baseRoomDao.update(entity)
        SourceResult.Success(entity)
    }catch (e: Exception) {
        SourceResult.Failure(e)
    }

    fun remove(entity: T): SourceResult<T> = try {
        baseRoomDao.delete(entity)
        SourceResult.Success(entity)
    }catch (e: Exception) {
        SourceResult.Failure(e)
    }
}