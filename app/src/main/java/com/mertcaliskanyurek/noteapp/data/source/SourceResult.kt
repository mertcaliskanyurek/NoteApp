package com.mertcaliskanyurek.noteapp.data.source

sealed class SourceResult<out R> {
    data class Success<out R>(val data: R): SourceResult<R>()
    data class Failure(val exception: Exception): SourceResult<Nothing>()
    object Loading: SourceResult<Nothing>()
}