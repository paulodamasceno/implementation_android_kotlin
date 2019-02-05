package mvp.implement.kotlin.common

import mvp.implement.kotlin.AppApplication
import mvp.implement.kotlin.Model.*
import mvp.implement.kotlin.service.DTO.AuthDTO
import com.google.gson.Gson

class Global {

    companion object {
        var gson = Gson()
        var auth: AuthDTO? = null

        var savedCars: List<SavedCar>? =  ArrayList()

        var step1: Boolean = false
        var step2: Boolean = false
        var step3: Boolean = false
        var step4: Boolean = false

        fun validateStep1() : Boolean {
            if (Global.experience.fuel_type.isEmpty())  return  false
            if (Global.experience.fuel_consumption == 0.0) return false
            if (Global.experience.road_type.isEmpty()) return false
            if (Global.experience.km_road == 0) return false
            if (Global.experience.people_inside == 0) return false
            return true
        }

        fun validadeStep2(): Boolean {
            return Global.experience.id_anomaly_private > 0
        }

        fun validadeStep3(): Boolean {
            return !Global.experience.original_description.isEmpty()
        }

    }

}
