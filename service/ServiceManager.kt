package mvp.implement.kotlin.service

object ServiceManager {

    //private var serviceClient:MvpImplementService = RetrofitInitializer.retrofit().create(MvpImplementService::class.java)

    fun getAuth(login: LoginDTO) : Observable<AuthDTO> {
        var serviceClient:MvpImplementService = RetrofitInitializer.retrofit().create(MvpImplementService::class.java)
        return serviceClient.getAuth(login).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun getCars(user: String, profile: String) : Observable<CarDTO> {
        var serviceClient:MvpImplementService = RetrofitInitializer.retrofit().create(MvpImplementService::class.java)
        return serviceClient.getCar(user, profile).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}
