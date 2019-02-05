package mvp.implement.kotlin.Presenter

interface ContentProtocol {
    fun groupLoaded(groups: List<Group>?)
    fun claimedLoaded(claimedList: List<Claimed>?)
    fun anomalyLoaded(anomalyList: List<Anomaly>?)
    fun carLoaded(cars: MutableList<Car>?)
    fun userExperienceLoaded(userExperience: List<UserExperience>?)
    fun driverProfileLoaded(driverProfile: List<DriverProfile>?)
    fun errorGroup(message: String)
    fun errorAnomaly(message: String)
    fun errorClaimed(message: String)
    fun errorCar(message: String)
    fun errorUserExperience(message: String)
    fun errorDriverProfile(message: String)
}

class ContentPresenter {

    lateinit var contentProtocol: ContentProtocol
    private val compositeDisposable = CompositeDisposable()
    var prefs: Prefs = Prefs(AppApplication.applicationContext())

    fun setView(contentProtocol: ContentProtocol) {
        this.contentProtocol = contentProtocol
    }

    fun loadGroup() {

        val disposable = ServiceManager.getGroup().subscribe ({

            if (it != null) {

                if (it.success){
                    //it.save()
                    contentProtocol.groupLoaded(it.data)
                }else {
                    if (!it.message.isEmpty()){
                        contentProtocol.errorGroup(it.message.toString())
                    }else{
                        contentProtocol.errorGroup("Não foi possivel completar sua requisição")
                    }
                }


            }else {
                contentProtocol.groupLoaded(ArrayList())
            }

        }, {
            if (it.message.toString().contains("401")) {
                contentProtocol.errorGroup("Usuario ou Token inválido!")
                Global.auth = null
                Global.userId = null
                prefs.apiAuth = ""
                prefs.auth = ""
                prefs.userId = ""
            }else{
                contentProtocol.errorGroup(it.message.toString())
            }
        })
        compositeDisposable.add(disposable)

    }

    fun loadClaimed() {

        val disposable = ServiceManager.getClaimed().subscribe ({

            if (it != null) {

                if (it.success){
                    //it.save()
                    contentProtocol.claimedLoaded(it.data)
                }else {
                    if (!it.message.isEmpty()){
                        contentProtocol.errorClaimed(it.message.toString())
                    }else{
                        contentProtocol.errorClaimed("Não foi possivel completar sua requisição")
                    }
                }


            }else {
                contentProtocol.claimedLoaded(ArrayList())
            }

        }, {
            if (it.message.toString().contains("401")) {
                contentProtocol.errorClaimed("Usuario ou Token inválido.")
            }else{
                contentProtocol.errorClaimed(it.message.toString())
            }
        })
        compositeDisposable.add(disposable)

    }

    fun loadAnomaly() {

        val disposable = ServiceManager.getAnomaly().subscribe ({

            if (it != null) {

                if (it.success){
                    //it.save()
                    contentProtocol.anomalyLoaded(it.data)
                }else {
                    if (!it.message.isEmpty()){
                        contentProtocol.errorAnomaly(it.message.toString())
                    }else{
                        contentProtocol.errorAnomaly("Não foi possivel completar sua requisição")
                    }
                }


            }else {
                contentProtocol.anomalyLoaded(ArrayList())
            }

        }, {
            if (it.message.toString().contains("401")) {
                contentProtocol.errorAnomaly("Usuario ou Token inválido.")
            }else{
                contentProtocol.errorAnomaly(it.message.toString())
            }
        })
        compositeDisposable.add(disposable)

    }



    fun loadCar(user: String) {

        val disposable = ServiceManager.getCars(user, "DESIGNADO").subscribe ({

            if (it != null) {

                if (it.success){
                    //it.save()
                    contentProtocol.carLoaded(it.data)
                }else {
                    if (!it.message.isEmpty()){
                        contentProtocol.errorCar(it.message.toString())
                    }else{
                        contentProtocol.errorCar("Não foi possivel completar sua requisição")
                    }
                }


            }else {
                contentProtocol.carLoaded(ArrayList())
            }

        }, {
            if (it.message.toString().contains("401")) {
                contentProtocol.errorCar("Usuario ou Token inválido.")
            }else{
                contentProtocol.errorCar(it.message.toString())
            }
        })
        compositeDisposable.add(disposable)

    }



    fun loadUserProfile(user: String) {

        val disposable = ServiceManager.getDriverProfile(user, "DESIGNADO").subscribe ({

            if (it != null) {

                if (it.success){
                    //it.save()
                    contentProtocol.driverProfileLoaded(it.data)
                }else {
                    if (!it.message.isEmpty()){
                        contentProtocol.errorDriverProfile(it.message.toString())
                    }else{
                        contentProtocol.errorDriverProfile("Não foi possivel completar sua requisição")
                    }
                }


            }else {
                contentProtocol.errorDriverProfile("Não foi possivel completar sua requisição")
            }

        }, {
            if (it.message.toString().contains("401")) {
                contentProtocol.errorDriverProfile("Usuario ou Token inválido.")
            }else{
                contentProtocol.errorDriverProfile(it.message.toString())
            }
        })
        compositeDisposable.add(disposable)

    }



    fun loadUserExperience(user: String) {

        val disposable = ServiceManager.getUserExperience(user).subscribe ({

            if (it != null) {

                if (it.success){
                    //it.save()
                    contentProtocol.userExperienceLoaded(it.data)
                }else {
                    if (!it.message.isEmpty()){
                        contentProtocol.errorUserExperience(it.message.toString())
                    }else{
                        contentProtocol.errorUserExperience("Não foi possivel completar sua requisição")
                    }
                }


            }else {
                contentProtocol.userExperienceLoaded(ArrayList())
            }

        }, {
            if (it.message.toString().contains("401")) {
                contentProtocol.errorUserExperience("Usuario ou Token inválido.")
            }else{
                contentProtocol.errorUserExperience(it.message.toString())
            }
        })
        compositeDisposable.add(disposable)

    }
}
