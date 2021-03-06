package app.akilesh.qacc.ui.colorpicker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import app.akilesh.qacc.model.Accent
import app.akilesh.qacc.utils.workers.CreateWorker
import java.util.*

class ColorPickerViewModel(application: Application) : AndroidViewModel(application) {

    val workManager = WorkManager.getInstance(application)
    var createWorkerId: UUID? = null
    val accent by lazy {
        MutableLiveData<Accent>()
    }

    internal fun create(accent: Accent) {
        val data = workDataOf(
            "pkg" to accent.pkgName,
            "name" to accent.name,
            "light" to accent.colorLight,
            "dark" to accent.colorDark
        )
        val createRequest = OneTimeWorkRequestBuilder<CreateWorker>()
            .setInputData(data)
            .build()
        createWorkerId = createRequest.id
        workManager.enqueue(createRequest)
    }
}
