package window

import model.RegisterModel
import model.UserModel
import org.unq.ui.model.UsedEmail

import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.Dialog

import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class RegisterWindow(owner: WindowOwner, model: RegisterModel) : Dialog<RegisterModel>(owner, model) {

    fun textBox(panel: Panel, propiedad: String){
        TextBox(panel) with {
            bindTo(propiedad)
        }
    }
    fun labelText(panel:Panel ,texto: String ){
        Label(panel) with {
            text = texto
        }
    }

    fun passwordField(panel:Panel,pass : String) {
        PasswordField(panel) with {
            bindTo(pass)
        }
    }

    override fun addActions(actionPanel: Panel) {
        Button(actionPanel) with {
            text = "Register"

            onClick {

                if ( modelObject.tieneCamposVacio()) {
                    throw UserException("This field cannot by empty")
                }
                if (!( modelObject.passwordAndPasswordCheckAreEqual(modelObject.password, modelObject.passwordCheck))) {
                    throw UserException("Passwords do not match")
                }
                try {
                    val user = modelObject.register(
                        modelObject.name,
                        modelObject.email,
                        modelObject.password,
                        modelObject.image,
                    )
                    val usermodel = UserModel(user, modelObject.system)
                    thisWindow.close() ; UserWindow(thisWindow, usermodel).open()
                } catch ( e : UsedEmail ) {
                    throw UserException(e.message)
                }

            }
        }

        Button(actionPanel) with {
            text = "Cancel"
            onClick {
                cancel()
            }
        }


    }

    override fun createFormPanel(mainPanel: Panel) {
        title = "Create User"
        iconImage = "instagram.png"

        labelText(mainPanel,"Name: ")
        textBox(mainPanel,"name")

        labelText(mainPanel,"Email: ")
        textBox(mainPanel,"email")

        labelText(mainPanel,"Image: ")
        textBox(mainPanel,"image")

        labelText(mainPanel,"Password: ")
        passwordField(mainPanel,"password")

        labelText(mainPanel,"PasswordCheck: ")
        passwordField(mainPanel,"passwordCheck")

        labelText(mainPanel,"You accept terms and conditions")


    }

}
