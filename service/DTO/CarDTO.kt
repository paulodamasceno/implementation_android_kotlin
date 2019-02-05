package mvp.implement.kotlin.service.DTO

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import mvp.implement.kotlin.Model.Car

@JsonIgnoreProperties(ignoreUnknown = true)
open class CarDTO(
    @JsonProperty var success: Boolean = false,
    @JsonProperty var message: String = "",
    @JsonProperty var data: MutableList<Car> = ArrayList()
)