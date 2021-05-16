package io.github.fuadreza.academy.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.fuadreza.academy.data.ContentEntity
import io.github.fuadreza.academy.data.CourseEntity
import io.github.fuadreza.academy.data.ModuleEntity
import io.github.fuadreza.academy.data.source.remote.RemoteDataSource
import io.github.fuadreza.academy.data.source.remote.response.ContentResponse
import io.github.fuadreza.academy.data.source.remote.response.CourseResponse
import io.github.fuadreza.academy.data.source.remote.response.ModuleResponse

class AcademyRepository private constructor(private val remoteDataSource: RemoteDataSource) : AcademyDataSource {

    companion object {
        @Volatile
        private var instance: AcademyRepository? = null
        fun getInstance(remoteData: RemoteDataSource): AcademyRepository =
            instance ?: synchronized(this) {
                instance ?: AcademyRepository(remoteData).apply { instance = this }
            }
    }

    override fun getAllCourses(): LiveData<ArrayList<CourseEntity>> {

//        val courseResponses = remoteDataSource.getAllCourses()
//        val courseList = ArrayList<CourseEntity>()
//        for (response in courseResponses) {
//            val course = CourseEntity(response.id,
//                response.title,
//                response.description,
//                response.date,
//                false,
//                response.imagePath)
//            courseList.add(course)
//        }
//        return courseList

        val courseResults = MutableLiveData<ArrayList<CourseEntity>>()
        remoteDataSource.getAllCourses(object : RemoteDataSource.LoadCoursesCallback {
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {
                val courseList = ArrayList<CourseEntity>()
                for (response in courseResponses) {
                    val course = CourseEntity(response.id,
                        response.title,
                        response.description,
                        response.date,
                        false,
                        response.imagePath)
                    courseList.add(course)
                }
                courseResults.postValue(courseList)
            }
        })

        return courseResults
    }

    override fun getBookmarkedCourses(): LiveData<ArrayList<CourseEntity>> {
//        val courseResponses = remoteDataSource.getAllCourses()
//        val courseList = ArrayList<CourseEntity>()
//        for (response in courseResponses) {
//            val course = CourseEntity(response.id,
//                response.title,
//                response.description,
//                response.date,
//                false,
//                response.imagePath)
//            courseList.add(course)
//        }
//        return courseList

        val courseResults = MutableLiveData<ArrayList<CourseEntity>>()
        remoteDataSource.getAllCourses(object : RemoteDataSource.LoadCoursesCallback {
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {
                val courseList = java.util.ArrayList<CourseEntity>()
                for (response in courseResponses) {
                    val course = CourseEntity(response.id,
                        response.title,
                        response.description,
                        response.date,
                        false,
                        response.imagePath)
                    courseList.add(course)
                }
                courseResults.postValue(courseList)
            }
        })
        return courseResults
    }

    override fun getCourseWithModules(courseId: String): LiveData<CourseEntity>{
//        val courseResponses = remoteDataSource.getAllCourses()
//        lateinit var course: CourseEntity
//        for (response in courseResponses) {
//            if (response.id == courseId) {
//                course = CourseEntity(response.id,
//                    response.title,
//                    response.description,
//                    response.date,
//                    false,
//                    response.imagePath)
//            }
//        }
//        return course

        val courseResult = MutableLiveData<CourseEntity>()
        remoteDataSource.getAllCourses(object : RemoteDataSource.LoadCoursesCallback {
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {
                lateinit var course: CourseEntity
                for (response in courseResponses) {
                    if (response.id == courseId) {
                        course = CourseEntity(response.id,
                            response.title,
                            response.description,
                            response.date,
                            false,
                            response.imagePath)
                    }
                }
                courseResult.postValue(course)
            }
        })
        return courseResult
    }

    override fun getAllModulesByCourse(courseId: String): LiveData<ArrayList<ModuleEntity>> {
//        val moduleResponses = remoteDataSource.getModules(courseId)
//        val moduleList = ArrayList<ModuleEntity>()
//        for(response in moduleResponses) {
//            val course = ModuleEntity(response.moduleId,
//                response.courseId,
//                response.title,
//                response.position,
//                false)
//            moduleList.add(course)
//        }
//        return moduleList

        val moduleResults = MutableLiveData<ArrayList<ModuleEntity>>()
        remoteDataSource.getModules(courseId, object : RemoteDataSource.LoadModulesCallback {
            override fun onAllModulesReceived(moduleResponses: List<ModuleResponse>) {
                val moduleList = ArrayList<ModuleEntity>()
                for(response in moduleResponses) {
                    val course = ModuleEntity(response.moduleId,
                        response.courseId,
                        response.title,
                        response.position,
                        false)

                    moduleList.add(course)
                }
                moduleResults.postValue(moduleList)
            }
        })
        return moduleResults
    }

    override fun getContent(courseId: String, moduleId: String): LiveData<ModuleEntity> {
//        val moduleResponses = remoteDataSource.getModules(courseId)
//        lateinit var module: ModuleEntity
//        for(response in moduleResponses) {
//            if (response.moduleId == moduleId) {
//                module = ModuleEntity(response.moduleId,
//                    response.courseId,
//                    response.title,
//                    response.position,
//                    false)
//                module.contentEntity = ContentEntity(remoteDataSource.getContent(moduleId).content)
//                break
//            }
//        }
//        return module

        val moduleResult = MutableLiveData<ModuleEntity>()
        remoteDataSource.getModules(courseId, object : RemoteDataSource.LoadModulesCallback {
            override fun onAllModulesReceived(moduleResponses: List<ModuleResponse>) {
                lateinit var module: ModuleEntity
                for (response in moduleResponses) {
                    if (response.moduleId == moduleId) {
                        module = ModuleEntity(response.moduleId,
                            response.courseId,
                            response.title,
                            response.position,
                            false)
                        remoteDataSource.getContent(moduleId, object : RemoteDataSource.LoadContentCallback {
                            override fun onContentReceived(contentResponse: ContentResponse) {
                                module.contentEntity = ContentEntity(contentResponse.content)
                                moduleResult.postValue(module)
                            }
                        })
                        break
                    }
                }
            }
        })
        return moduleResult
    }
}