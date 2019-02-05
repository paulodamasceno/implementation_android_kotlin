package mvp.implement.kotlin.Presenter

interface SaveProtocol {
    fun savedIn(result: BaseDTO?)
    fun errorSave(message: String)
    fun userExperienceLoaded(userExperience: List<UserExperience>?)
    fun errorUserExperience(message: String)
}

class SavePresenter {

    lateinit var saveProtocol: SaveProtocol
    private val compositeDisposable = CompositeDisposable()

    fun setView(saveProtocol: SaveProtocol) {
        this.saveProtocol = saveProtocol
    }

    fun saveExperience(experiences: List<Experience>) {

        val disposable = ServiceManager.postUserExperience(experiences).subscribe ({

            if (it != null) {

                if (it.get(0).success){
                    //it.save()
                    saveProtocol.savedIn(it.get(0))
                }else {
                    if (!it.get(0).message.isEmpty()){
                        saveProtocol.errorSave(it.get(0).message.toString())
                    }else{
                        saveProtocol.errorSave("Não foi possivel completar sua requisição")
                    }
                }


            }else {
                saveProtocol.errorSave("Não foi possivel completar sua requisição")
            }

        }, {
            if (it.message.toString().contains("401")) {
                saveProtocol.errorSave("Usuario ou senha inválidos!")
            }else{
                saveProtocol.errorSave(it.message.toString())
            }
        })
        compositeDisposable.add(disposable)

    }

    fun loadUserExperience(user: String) {

        val disposable = ServiceManager.getUserExperience(user).subscribe ({

            if (it != null) {

                if (it.success){
                    //it.save()
                    saveProtocol.userExperienceLoaded(it.data)
                }else {
                    if (!it.message.isEmpty()){
                        saveProtocol.errorUserExperience(it.message.toString())
                    }else{
                        saveProtocol.errorUserExperience("Não foi possivel completar sua requisição")
                    }
                }


            }else {
                saveProtocol.userExperienceLoaded(ArrayList())
            }

        }, {
            if (it.message.toString().contains("401")) {
                saveProtocol.errorUserExperience("Usuario ou Token inválido.")
            }else{
                saveProtocol.errorUserExperience(it.message.toString())
            }
        })
        compositeDisposable.add(disposable)

    }
}
