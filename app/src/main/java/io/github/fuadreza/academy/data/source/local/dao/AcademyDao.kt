package io.github.fuadreza.academy.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.fuadreza.academy.data.CourseEntity
import io.github.fuadreza.academy.data.CourseWithModule
import io.github.fuadreza.academy.data.ModuleEntity

@Dao
interface AcademyDao {

    @Query("SELECT * FROM courseentities")
    fun getCourses(): LiveData<List<CourseEntity>>

    @Query("SELECT * FROM courseentities where bookmarked = 1")
    fun getBookmarkedCourse(): LiveData<List<CourseEntity>>

    @Transaction
    @Query("SELECT * FROM courseentities WHERE courseId = :courseId")
    fun getCourseWithModuleById(courseId: String): LiveData<CourseWithModule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourses(courses: ArrayList<CourseEntity>)

    @Update
    fun updateCourse(course: CourseEntity)

    @Query("SELECT * FROM moduleentities WHERE courseId = :courseId")
    fun getModulesByCourseId(courseId: String): LiveData<List<ModuleEntity>>

    @Query("SELECT * FROM moduleentities WHERE moduleId = :moduleId")
    fun getModuleById(moduleId: String): LiveData<ModuleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModules(module: ArrayList<ModuleEntity>)

    @Update
    fun updateModule(module: ModuleEntity)

    @Query("UPDATE moduleentities SET content = :content WHERE moduleId = :moduleId")
    fun updateModuleByContent(content: String, moduleId: String)
}