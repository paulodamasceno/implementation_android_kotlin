 UserExperiencesActivity : AppCompatActivity(), ListExperienceListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_experiences)
        init()
    }



    fun init() {
        val car = Global.selectedCar


        myBoard.text = "${car!!.license_plate.subSequence(0,3)}-${car.license_plate.subSequence(3,7)}"
        carName.text = car.model_car

        if (Global.getCar(Global.selectedCar!!.license_plate) != null) {
            var cCar = Global.getCar(Global.selectedCar!!.license_plate) as SavedCar
            if (cCar.nick_name.isNotEmpty()){
                myCar.setText(cCar.nick_name)
            }
        }



        val experiences = Global.userExperience?.filter { it.license_plate.equals(car!!.license_plate) }
        experienceRV.adapter = ListExperienceAdapter(experiences,this)
        experienceRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        btnBackList.setOnClickListener {
            Global.userExperienceSelected = null
            finish()
        }

    }

    override fun onDetailSelected(experience: UserExperience) {
        Global.userExperienceSelected = experience
        startActivity(Intent(this, DetailLamentationActivity::class.java))
    }
}
