package mvp.implement.kotlin.Model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.io.Serializable


@JsonIgnoreProperties(ignoreUnknown = true)
class Car (
        @JsonProperty var license_plate: String = "",
        @JsonProperty var model_car: String = "",
        @JsonProperty var fuel_type: String = "",
        @JsonProperty var car_color: String = "",
        @JsonProperty var model_year: Int = 0
        )


