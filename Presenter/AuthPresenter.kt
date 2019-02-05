package mvp.implement.kotlin.Presenter

interface AuthProtocol {
    fun logedIn(auth: AuthDTO?)
    fun errorAuth(message: String)
}

class AuthPresenter {

    lateinit var authProtocol: AuthProtocol
    private val compositeDisposable = CompositeDisposable()

    fun setView(authProtocol: AuthProtocol) {
        this.authProtocol = authProtocol
    }

    fun loadAuth(user: String, passwd: String) {


        var login = LoginDTO(user, passwd, 1)


        val disposable = ServiceManager.getAuth(login).subscribe ({

            if (it != null) {

                if (it.success){
                    //it.save()
                    authProtocol.logedIn(it)
                }else {
                    if (!it.message.isEmpty()){
                        authProtocol.errorAuth(it.message.toString())
                    }else{
                        authProtocol.errorAuth("Não foi possivel completar sua requisição")
                    }
                }


            }else {
                authProtocol.logedIn(null)
            }

        }, {
            if (it.message.toString().contains("401")) {
                authProtocol.errorAuth("Usuario ou senha inválidos!")
            }else{
                authProtocol.errorAuth(it.message.toString())
            }
        })
        compositeDisposable.add(disposable)

    }
}
