package com.miraelDev.anix.domain.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.google.android.exoplayer2.Player

@Immutable
@Stable
data class PlayerWrapper(
    val exoPlayer: Player
)
