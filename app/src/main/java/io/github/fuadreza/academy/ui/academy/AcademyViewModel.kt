package io.github.fuadreza.academy.ui.academy

import androidx.lifecycle.ViewModel
import io.github.fuadreza.academy.data.CourseEntity
import io.github.fuadreza.academy.utils.DataDummy

class AcademyViewModel : ViewModel() {

    fun getCourses(): List<CourseEntity> = DataDummy.generateDummyCourses()
}