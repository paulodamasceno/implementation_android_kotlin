package mvp.implement.kotlin.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import mvp.implement.kotlin.R
import mvp.implement.kotlin.common.Global
import mvp.implement.kotlin.common.MessageUI
import mvp.implement.kotlin.view.adapter.StepsListener
import kotlinx.android.synthetic.main.fragment_step2.view.*
import org.jetbrains.anko.support.v4.selector
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.textColor


private const val STEP_INDICATOR = "step2"

class Step2Fragment : Fragment() {


    var idGroup: String = ""
    var idClaimed: String = ""

    var listener: StepsListener? = null
    companion object {
        fun newInstance(listener: StepsListener): Step2Fragment {
            val args = Bundle()
            //args.putSerializable(STEP_INDICATOR, indicador)
            val fragment = Step2Fragment()
            fragment.listener = listener
            //fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Global.experience.id_anomaly_private= 0

        /*arguments?.let {
            indicador = it.getSerializable(STEP_INDICATOR) as Indicador
        }*/
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_step2, container, false)



        if (Global.selectedGroup.isEmpty()) {
            view.txtSelect1.text = "Qual o sistema?"
            view.txtSelect1.textColor = resources.getColor(R.color.gray_9b9b9b)
        }else {
            view.txtSelect1.text = Global.selectedGroup
            view.txtSelect1.textColor = resources.getColor(R.color.colorPrimary)
        }

        if (Global.selectedClaimed.isEmpty()) {
            view.txtSelect2.text = "Qual item será indicado?"
            view.txtSelect2.textColor = resources.getColor(R.color.gray_9b9b9b)
        }else {
            view.txtSelect2.text = Global.selectedClaimed
            view.txtSelect2.textColor = resources.getColor(R.color.colorPrimary)
        }

        if (Global.selectedAnomaly.isEmpty()) {
            view.txtSelect3.text = "O que está irregular?"
            view.txtSelect3.textColor = resources.getColor(R.color.gray_9b9b9b)
        }else {
            view.txtSelect3.text = Global.selectedAnomaly
            view.txtSelect3.textColor = resources.getColor(R.color.colorPrimary)
        }




        view.selectOpt1.setOnClickListener {


            Global.step2 = Global.validadeStep2()
            var lista = Global.groups!!.map { it.group_name_pt }


            selector("Qual o sistema?", lista, { dialogInterface, i ->
                //toast("So you're living in ${countries[i]}, right?")
                view.txtSelect1.text = lista.get(i)
                Global.selectedGroup = lista.get(i)
                Global.experience.id_anomaly_private = 0
                idGroup = Global.groups?.get(i)?.id_group!!
                view.txtSelect1.textColor = resources.getColor(R.color.colorPrimary)


                view.txtSelect2.text = "Qual item será indicado?"
                view.txtSelect2.textColor = resources.getColor(R.color.gray_9b9b9b)
                view.txtSelect3.text = "O que está irregular?"
                view.txtSelect3.textColor = resources.getColor(R.color.gray_9b9b9b)
                Global.selectedAnomaly = ""
                Global.selectedClaimed = ""
                Global.step2 = Global.validadeStep2()




            })
        }

        view.selectOpt2.setOnClickListener {

            if (!idGroup.isEmpty()) {


                var lista = Global.claimeds!!.filter { it.id_group.equals(idGroup)}.map { it.claimed_name_pt }



                selector("Qual item será indicado?", lista, { dialogInterface, i ->
                    //toast("So you're living in ${countries[i]}, right?")
                    view.txtSelect2.text = lista.get(i)
                    Global.selectedClaimed = lista.get(i)
                    Global.step2 = Global.validadeStep2()
                    idClaimed = Global.claimeds!!.filter { it.id_group.equals(idGroup)}.get(i).id_claimed
                    view.txtSelect2.textColor = resources.getColor(R.color.colorPrimary)


                    view.txtSelect3.text = "O que está irregular?"
                    view.txtSelect3.textColor = resources.getColor(R.color.gray_9b9b9b)
                    Global.selectedAnomaly = ""
                    Global.step2 = Global.validadeStep2()
                })
            }
        }

        view.selectOpt3.setOnClickListener {
            if (!idClaimed.isEmpty()) {

                var lista = Global.anomaly!!.filter { it.id_claimed.equals(idClaimed)}.map { it.anomaly_name_pt }





                selector("O que está irregular?", lista, { dialogInterface, i ->
                    //toast("So you're living in ${countries[i]}, right?")
                    view.txtSelect3.text = lista.get(i)
                    Global.selectedAnomaly = lista.get(i)
                    Global.step2 = Global.validadeStep2()
                    view.txtSelect3.textColor = resources.getColor(R.color.colorPrimary)
                    Global.experience.id_anomaly_private = Global.anomaly!!.filter { it.id_claimed.equals(idClaimed)}.get(i).id_anomaly_private
                    Global.step2 = Global.validadeStep2()
                })
            }
        }



        view.btNextStep3.setOnClickListener {

            if (Global.experience.id_anomaly_private > 0) {
                this.listener?.gotToStep3()

                Global.step2 = true
            }
            else {
                MessageUI.showAlert(this.context!!, R.style.AlertDialogCustom, "CAMPOS OBRIGATORIOS", "Selecione as opções para prosseguir")
            }
        }

        return view
    }
}