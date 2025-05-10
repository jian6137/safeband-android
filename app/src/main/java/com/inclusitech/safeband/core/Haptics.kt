package com.inclusitech.safeband.core

import androidx.compose.ui.hapticfeedback.HapticFeedback

class HapticsManager {
    companion object {
        fun triggerHapticFeedback(haptic: HapticFeedback) {
            haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
        }
    }
}