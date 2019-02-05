class MyCarActivity : AppCompatActivity(), MyCarProtocol, LamentationListener {

    private val myCarPresenter = MyCarPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_car)
        myCarPresenter.setView(this)
    }

    override fun onResume() {
        super.onResume()

        myCarPresenter.loadCars()
        init()
    }


    fun init(){

        if(Global.Companion.auth != null){


            var userName = Global.Companion.auth?.user_name?.toLowerCase()?.capitalize()
            userName = userName!!.split(" ")[0]

            var user = "Olá, ${userName}"
            welcome_user.setText(user)
        }


        btLogoff.setOnClickListener {



            MessageUI.showAlert(this, R.style.AlertDialogCustom, "LOGOFF", "Quer Realmente sair do aplicativo?","SIM") { d,x ->
                Global.auth = null
                Global.userId = null
                Prefs(this).apiAuth = ""
                Prefs(this).auth = ""
                Prefs(this).userId = ""
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }





        btCloseName.setOnClickListener {
            viewFormName.visibility = View.GONE
            fieldName.setText("")
        }


        btnSalvarName.setOnClickListener {

            if(fieldName.text.toString().isNotEmpty()){
                var name = fieldName.text.toString()
                updateNameCar(name)
                viewFormName.visibility = View.GONE
                fieldName.setText("")
            }

            inputMethodManager.hideSoftInputFromWindow( viewFormName.windowToken, 0)




        }





    }

    override fun loadCars(cars: MutableList<Car>?) {

        carsPageView.clipToPadding = false
        carsPageView.setPadding(60,20,60,10)
        carsPageView.pageMargin = 20

        carsPageView.adapter = MyCarAdapter(this, cars!!, this)
    }

    override fun newLamentationClicked(car: Car) {
        Global.selectedCar = car
        startActivity(Intent(this, NewLamentationActivity::class.java))
    }

    override fun showLamentations(car: Car) {
        Global.selectedCar = car
        startActivity(Intent(this, UserExperiencesActivity::class.java))
    }

    override fun editPhotoClicked(car: Car) {
        Global.selectedCar = car
        ImagePicker.create(this)
            .limit(1)
            .start()
    }

    override fun editNameClicked(car: Car) {
        Global.selectedCar = car

        if (Global.getCar(Global.selectedCar!!.license_plate) != null) {
            var cCar = Global.getCar(Global.selectedCar!!.license_plate) as SavedCar
            if (cCar.nick_name.isNotEmpty()){
                fieldName.setText(cCar.nick_name)
            }
        }



        viewFormName.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data)


            if (image != null){
                updatePlacaCar(image.path)
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun updateNameCar(name: String){

        if (Global.getCar(Global.selectedCar!!.license_plate) != null){
            var cCar = Global.getCar(Global.selectedCar!!.license_plate) as SavedCar
            cCar.nick_name = name
            Global.saveCar(cCar)
            myCarPresenter.loadCars()
            return
        }

        var car = SavedCar()
        car.license_plate = Global.selectedCar!!.license_plate
        car.nick_name = name
        Global.saveCar(car)
        myCarPresenter.loadCars()
    }


    fun updatePlacaCar(path: String){


        if (Global.getCar(Global.selectedCar!!.license_plate) != null){
            var cCar = Global.getCar(Global.selectedCar!!.license_plate) as SavedCar
            cCar.photo = path
            Global.saveCar(cCar)
            myCarPresenter.loadCars()
            return
        }

        var car = SavedCar()
        car.license_plate = Global.selectedCar!!.license_plate
        car.photo = path
        Global.saveCar(car)
        myCarPresenter.loadCars()

    }


}
