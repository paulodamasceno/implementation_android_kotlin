package mvp.implement.kotlin.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SeekBar

import mvp.implement.kotlin.R
import mvp.implement.kotlin.common.Global
import mvp.implement.kotlin.common.MessageUI
import mvp.implement.kotlin.view.adapter.StepsListener
import kotlinx.android.synthetic.main.fragment_step1.view.*
import org.w3c.dom.Text


private const val STEP_INDICATOR = "step1"

interface Updateable {
    fun update()
}

class Step1Fragment : Fragment() {

    var listener: StepsListener? = null


    companion object {
        fun newInstance(listener: StepsListener): Step1Fragment {
            val args = Bundle()

            //args.putSerializable(STEP_INDICATOR, indicador)
            val fragment = Step1Fragment()
            fragment.listener = listener
            //fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*arguments?.let {
            indicador = it.getSerializable(STEP_INDICATOR) as Indicador
        }*/
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_step1, container, false)


        view.btnGasolina.isActivated = view.btnGasolina.text.equals(Global.experience.fuel_type)
        view.btnAlcool.isActivated = view.btnAlcool.text.equals(Global.experience.fuel_type)
        view.btnDiesel.isActivated = view.btnDiesel.text.equals(Global.experience.fuel_type)


        view.btnUrbano.isActivated = view.btnUrbano.text.equals(Global.experience.road_type)
        view.btnRodovia.isActivated = view.btnRodovia.text.equals(Global.experience.road_type)
        view.btnMisto.isActivated = view.btnMisto.text.equals(Global.experience.road_type)

        //if(Global.experience.km_road > 0) {
            view.kmRunned.setText("${Global.experience.km_road}")
        //}

        view.progressQuantity.progress =  Global.experience.people_inside
        view.progressKm.progress = (Global.experience.fuel_consumption*10).toInt()

        view.txtQuantit.text = "${Global.experience.people_inside} incluindo o motorista"
        view.txtKm.text = String.format("%.1f Km/l", Global.experience.fuel_consumption)


        view.btnGasolina.setOnClickListener {
            view.btnGasolina.isActivated = true
            view.btnAlcool.isActivated = false
            view.btnDiesel.isActivated = false
            Global.experience.fuel_type = view.btnGasolina.text.toString()
            Global.step1 = Global.validateStep1()
        }

        view.btnAlcool.setOnClickListener {
            view.btnGasolina.isActivated = false
            view.btnAlcool.isActivated = true
            view.btnDiesel.isActivated = false
            Global.experience.fuel_type = view.btnAlcool.text.toString()
            Global.step1 = Global.validateStep1()

        }

        view.btnDiesel.setOnClickListener {
            view.btnGasolina.isActivated = false
            view.btnAlcool.isActivated = false
            view.btnDiesel.isActivated = true
            Global.experience.fuel_type = view.btnDiesel.text.toString()
            Global.step1 = Global.validateStep1()

        }



        view.btnUrbano.setOnClickListener {
            view.btnUrbano.isActivated = true
            view.btnRodovia.isActivated = false
            view.btnMisto.isActivated = false
            Global.experience.road_type = view.btnUrbano.text.toString()
            Global.step1 = Global.validateStep1()

        }

        view.btnRodovia.setOnClickListener {
            view.btnRodovia.isActivated = true
            view.btnUrbano.isActivated = false
            view.btnMisto.isActivated = false
            Global.experience.road_type = view.btnRodovia.text.toString()
            Global.step1 = Global.validateStep1()

        }

        view.btnMisto.setOnClickListener {
            view.btnMisto.isActivated = true
            view.btnUrbano.isActivated = false
            view.btnRodovia.isActivated = false
            Global.experience.road_type = view.btnMisto.text.toString()
            Global.step1 = Global.validateStep1()

        }


        view.kmRunned.setOnFocusChangeListener { view, b ->
            if (view.kmRunned.text.toString().equals("0")){
                view.kmRunned.setText("")
            }
        }

        view.kmRunned.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

                var value = p0.toString()
                value = p0.toString().replace(",", "")
                value = p0.toString().replace(".", "")

                if (value.toIntOrNull() != null) {
                    Global.experience.km_road = value.toInt()
                    Global.step1 = Global.validateStep1()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        view.progressQuantity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                view.txtQuantit.text = "$i incluindo o motorista"
                Global.experience.people_inside = i
                Global.step1 = Global.validateStep1()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })


        view.progressKm.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                var km = i * 0.1
                view.txtKm.text = String.format("%.1f Km/l", km)
                Global.experience.fuel_consumption = km
                Global.step1 = Global.validateStep1()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })


        view.btNextStep.setOnClickListener {


            if (validateFields()){
                this.listener?.gotToStep2()
                Global.step1 = true
            }
            else {
                MessageUI.showAlert(this.context!!,
                    R.style.AlertDialogCustom,"CAMPOS EM BRANCO", "Confira todos os campos de formulario antes de prosseguir")
            }
        }

        return view
    }


    fun validateFields() : Boolean {

        if (Global.experience.fuel_type.isEmpty())  return  false
        if (Global.experience.fuel_consumption == 0.0) return false
        if (Global.experience.road_type.isEmpty()) return false
        if (Global.experience.km_road == 0) return false
        if (Global.experience.people_inside == 0) return false
        return true

    }
}
