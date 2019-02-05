package mvp.implement.kotlin.Model

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.io.Serializable

@RealmClass
open class Auth(
    val token: String = "",
    val expiresIn: String = "",
    val user_name: String = ""
): RealmObject(), Serializable