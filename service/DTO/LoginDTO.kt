package mvp.implement.kotlin.service.DTO

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import mvp.implement.kotlin.Model.Auth
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
class LoginDTO(
    @JsonProperty var id_user: String,
    @JsonProperty var user_pass: String = "",
    @JsonProperty var id_push: Int = 1
)