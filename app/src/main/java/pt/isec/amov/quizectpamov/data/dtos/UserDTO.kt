package pt.isec.amov.quizectpamov.data.dtos

data class UserDTO(val name : String, val email : String) {
    constructor() : this("", "")
}
