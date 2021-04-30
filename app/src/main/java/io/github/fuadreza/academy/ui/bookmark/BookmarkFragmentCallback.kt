package io.github.fuadreza.academy.ui.bookmark

import io.github.fuadreza.academy.data.CourseEntity

interface BookmarkFragmentCallback {
    fun onShareClick(course: CourseEntity)
}
