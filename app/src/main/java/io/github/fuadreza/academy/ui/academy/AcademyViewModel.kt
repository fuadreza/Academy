package io.github.fuadreza.academy.ui.academy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.fuadreza.academy.data.CourseEntity
import io.github.fuadreza.academy.data.source.AcademyRepository
import io.github.fuadreza.academy.data.vo.Resource
import io.github.fuadreza.academy.utils.DataDummy

class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    fun getCourses(): LiveData<Resource<List<CourseEntity>>> = academyRepository.getAllCourses()
}