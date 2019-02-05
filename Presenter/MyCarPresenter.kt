package mvp.implement.kotlin.Presenter

interface MyCarProtocol {
    fun loadCars(cars: MutableList<Car>?)
}

class MyCarPresenter {

    private lateinit var myCarProtocol: MyCarProtocol

    fun setView(myCarProtocol: MyCarProtocol) {
        this.myCarProtocol = myCarProtocol
    }

    fun loadCars() {
        myCarProtocol.loadCars(Global.cars)

    }
}
