package mvp.implement.kotlin.view.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import mvp.implement.kotlin.Model.Car
import mvp.implement.kotlin.R
import mvp.implement.kotlin.common.Global
import kotlinx.android.synthetic.main.item_new_lamentation.view.*
import java.io.File
import java.net.URI


interface LamentationListener {

    fun newLamentationClicked(car: Car)
    fun showLamentations(car: Car)
    fun editPhotoClicked(car: Car)
    fun editNameClicked(car: Car)

}


class MyCarAdapter(val context: Context, val cars: List<Car>, listener: LamentationListener) : PagerAdapter()  {


    val listener: LamentationListener = listener


    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 === p1
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {


        var car = cars.get(position)

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_new_lamentation, container, false) as ViewGroup
        container.addView(view)

        view.my_board.text = "${car.license_plate.subSequence(0,3)}-${car.license_plate.subSequence(3,7)}"
        view.car_name.text = car.model_car

        val lmNumber = Global.userExperience?.filter { it.license_plate.equals(car.license_plate) }?.count()
        view.lamentation_number.text = "Esse veículo possui $lmNumber lamentações"



        if (Global.getCar(car.license_plate) != null){

            var car = Global.getCar(car.license_plate)
            view.img_car.visibility = View.VISIBLE
            Glide.with(context)
                .load( File(car!!.photo))
                .into(view.img_car)



            if (car.nick_name.isNotEmpty()){
                view.my_car.text = car.nick_name
            }else{
                view.my_car.text = "Meu Carro"
            }


        }else{
            view.img_car.visibility = View.GONE
        }



        view.btAddPhoto.setOnClickListener {
            listener.editPhotoClicked(car)
        }


        view.btLamentations.setOnClickListener {
            this.listener.showLamentations(car)
        }



        view.btnNewLament.setOnClickListener {
            Global.experience.license_plate = car.license_plate
            this.listener.newLamentationClicked(car)
        }


        view.btEditName.setOnClickListener {
            listener.editNameClicked(car)
        }








        return view



    }


    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return cars.size
    }


}