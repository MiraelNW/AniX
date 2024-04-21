package com.miraeldev.models.paging

enum class LoadState {
    REQUEST_INACTIVE,
    REFRESH_LOADING,
    APPEND_LOADING,
    REFRESH_ERROR,
    APPEND_ERROR,
    REACH_END,
    EMPTY,
    INITIAL
}