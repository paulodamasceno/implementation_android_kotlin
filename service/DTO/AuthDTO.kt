package mvp.implement.kotlin.service.DTO

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import mvp.implement.kotlin.Model.Auth
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.io.Serializable

@RealmClass
@JsonIgnoreProperties(ignoreUnknown = true)
open class AuthDTO(
    @JsonProperty var success: Boolean = false,
    @JsonProperty var message: String = "",
    @JsonProperty var token: String = "",
    @JsonProperty var expiresIn: String = "",
    @JsonProperty var user_name: String = ""
): RealmObject()