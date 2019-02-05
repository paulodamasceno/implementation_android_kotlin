package mvp.implement.kotlin.Model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
class Anomaly (
    @JsonProperty var id_anomaly_private: Int = 0,
    @JsonProperty var id_anomaly: String = "",
    @JsonProperty var id_claimed: String = "",
    @JsonProperty var anomaly_tesis_label: String = "",
    @JsonProperty var anomaly_name_pt: String = "",
    @JsonProperty var anomaly_inactive: Boolean = false
)