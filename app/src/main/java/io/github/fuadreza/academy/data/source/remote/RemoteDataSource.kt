package io.github.fuadreza.academy.data.source.remote

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.fuadreza.academy.data.source.remote.response.ContentResponse
import io.github.fuadreza.academy.data.source.remote.response.CourseResponse
import io.github.fuadreza.academy.data.source.remote.response.ModuleResponse
import io.github.fuadreza.academy.utils.EspressoIdlingResource
import io.github.fuadreza.academy.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper).apply { instance = this }
            }
    }

//    fun getAllCourses(): List<CourseResponse> = jsonHelper.loadCourses()
//
//    fun getModules(courseId: String): List<ModuleResponse> = jsonHelper.loadModule(courseId)
//
//    fun getContent(moduleId: String): ContentResponse = jsonHelper.loadContent(moduleId)

//    fun getAllCourses(callback: LoadCoursesCallback) {
//        EspressoIdlingResource.increment()
//        handler.postDelayed({
//            callback.onAllCoursesReceived(jsonHelper.loadCourses())
//            EspressoIdlingResource.decrement()
//                            }, SERVICE_LATENCY_IN_MILLIS)
//    }

    fun getAllCourses(): LiveData<ApiResponse<List<CourseResponse>>> {
        EspressoIdlingResource.increment()
        val resultCourse = MutableLiveData<ApiResponse<List<CourseResponse>>>()
        handler.postDelayed({
            resultCourse.value = ApiResponse.success(jsonHelper.loadCourses())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultCourse
    }

//    fun getModules(courseId: String, callback: LoadModulesCallback) {
//        EspressoIdlingResource.increment()
//        handler.postDelayed({
//            callback.onAllModulesReceived(jsonHelper.loadModule(courseId))
//            EspressoIdlingResource.decrement()
//                            }, SERVICE_LATENCY_IN_MILLIS)
//    }

    fun getModules(courseId: String): LiveData<ApiResponse<List<ModuleResponse>>> {
        EspressoIdlingResource.increment()
        val resultModules = MutableLiveData<ApiResponse<List<ModuleResponse>>>()
        handler.postDelayed({
            resultModules.value = ApiResponse.success(jsonHelper.loadModule(courseId))
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultModules
    }

//    fun getContent(moduleId: String, callback: LoadContentCallback) {
//        EspressoIdlingResource.increment()
//        handler.postDelayedu({
//            callback.onContentReceived(jsonHelper.loadContent(moduleId))
//            EspressoIdlingResource.decrement()
//                            }, SERVICE_LATENCY_IN_MILLIS)
//    }

    fun getContent(moduleId: String): LiveData<ApiResponse<ContentResponse>> {
        EspressoIdlingResource.increment()
        val resultContent = MutableLiveData<ApiResponse<ContentResponse>>()
        handler.postDelayed({
            resultContent.value = ApiResponse.success(jsonHelper.loadContent(moduleId))
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultContent
    }

    interface LoadCoursesCallback {
        fun onAllCoursesReceived(courseResponses: List<CourseResponse>)
    }
    interface LoadModulesCallback {
        fun onAllModulesReceived(moduleResponses: List<ModuleResponse>)
    }
    interface LoadContentCallback {
        fun onContentReceived(contentResponse: ContentResponse)
    }

}