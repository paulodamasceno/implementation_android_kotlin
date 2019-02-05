package mvp.implement.kotlin.service.DTO

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
open class BaseDTO(
    @JsonProperty var success: Boolean = false,
    @JsonProperty var message: String = ""
)