
class SplashActivity : AppCompatActivity(), ContentProtocol {

    lateinit var contentPresenter: ContentPresenter
    val gson = Gson()
    var prefs: Prefs?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        contentPresenter = ContentPresenter()
        prefs = Prefs(this)
        contentPresenter.setView(this)

        if (prefs?.apiAuth!!.toString().isEmpty() || prefs?.userId!!.isEmpty()){
            startActivity(Intent(this, LoginActivity::class.java))
        }else {
            Global.auth = gson.fromJson(prefs?.auth, AuthDTO::class.java)
            Global.loadSavedCars()
            showLoading()
        }
    }


    fun showLoading(){
        val animation = AnimationUtils.loadAnimation(this, R.anim.rounded_animate)
        splashLoader.startAnimation(animation)
        contentPresenter.loadGroup()
    }


    override fun groupLoaded(groups: List<Group>?) {
        prefs?.groups = gson.toJson(groups)
        Global.Companion.groups = groups
        contentPresenter.loadClaimed()
        progressBar.progress = 20
    }

    override fun claimedLoaded(claimedList: List<Claimed>?) {
        prefs?.claimeds = gson.toJson(claimedList)
        Global.Companion.claimeds = claimedList
        contentPresenter.loadAnomaly()
        progressBar.progress = 35
    }

    override fun anomalyLoaded(anomalyList: List<Anomaly>?) {
        prefs?.anomalies = gson.toJson(anomalyList)
        Global.Companion.anomaly = anomalyList
        contentPresenter.loadCar(Global.userId!!)
        progressBar.progress = 50
    }

    override fun errorGroup(message: String) {
        contentPresenter.loadClaimed()
        progressBar.progress = 20
    }

    override fun errorClaimed(message: String) {
        contentPresenter.loadAnomaly()
        progressBar.progress = 35
    }

    override fun errorAnomaly(message: String) {
        var msg = ""
        if(message.isEmpty()){
            msg = "Erro ao carregar os dados verifique suas conexões de internet e tente novamente."
        }else{
            msg = message
        }

        MessageUI.showAlert(
            this, R.style.AlertDialogCustom,
            "ERROR",
            msg,"OK") { i,j ->
                finish()
            }
    }

    override fun carLoaded(cars: MutableList<Car>?) {
        Global.Companion.cars = cars
        prefs?.cars = gson.toJson(cars)
        contentPresenter.loadUserExperience(Global.userId!!)
        progressBar.progress = 80
        //finish()
    }

    override fun errorCar(message: String) {
        var msg = ""
        if(message.isEmpty()){
            msg = "Erro ao carregar os dados verifique suas conexões de internet e tente novamente."
        }else{
            msg = message
        }

        MessageUI.showAlert(
            this, R.style.AlertDialogCustom,
            "ERROR",
            msg,"OK") { i,j ->
            finish()
        }
    }

    override fun userExperienceLoaded(userExperience: List<UserExperience>?) {
        Global.Companion.userExperience = userExperience
        prefs?.userExperience = gson.toJson(userExperience)
        contentPresenter.loadUserProfile(Global.userId!!)

        progressBar.progress = 98

    }

    override fun errorUserExperience(message: String) {
        var msg = ""
        if(message.isEmpty()){
            msg = "Erro ao carregar os dados verifique suas conexões de internet e tente novamente."
        }else{
            msg = message
        }

        MessageUI.showAlert(
            this, R.style.AlertDialogCustom,
            "ERROR",
            msg,"OK") { i,j ->
            finish()
        }
    }


    override fun driverProfileLoaded(driverProfile: List<DriverProfile>?) {
        Global.driverProfile = driverProfile!!.get(0)
        Global.experience.id_driver_profile = Global.driverProfile!!.id_driver_profile
        startActivity(Intent(this, MyCarActivity::class.java))
        finish()
    }

    override fun errorDriverProfile(message: String) {
        var msg = ""
        if(message.isEmpty()){
            msg = "Erro ao carregar os dados verifique suas conexões de internet e tente novamente."
        }else{
            msg = message
        }

        MessageUI.showAlert(
            this, R.style.AlertDialogCustom,
            "ERROR",
            msg,"OK") { i,j ->
            finish()
        }
    }


}
