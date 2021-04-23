package ar.edu.unahur.obj2.caralibro

class Usuario {
    val publicaciones = mutableListOf<Publicacion>()
    val amigos = mutableListOf<Usuario>()
    val mejoresAmigos = mutableListOf<Usuario>()

    fun agregarPublicacion(publicacion: Publicacion) {
        publicaciones.add(publicacion)
    }

    fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

    fun darLike(publicacion: Publicacion) {
        if (this.dioLikeEn(publicacion)) {
            throw Exception ("Ya le has dado like a esta publicacion")
        }
        publicacion.likes += 1
        publicacion.usuariosQueDieronLike.add(this)
    }

    fun dioLikeEn(publicacion: Publicacion) = publicacion.usuariosQueDieronLike.contains(this)

    fun agregarAmigo(usuario: Usuario) {
        this.amigos.add(usuario)
    }

    fun esMasAmistosoQue(usuario: Usuario) = this.amigos.count() > usuario.amigos.count()

    fun puedeVerPublicacion(publicacion: Publicacion) = publicacion.permiso.puedeSerVistaPor(this)


    fun agregarMejorAmigo(usuarioMejorAmigo: Usuario) {
        this.mejoresAmigos.add(usuarioMejorAmigo)
        usuarioMejorAmigo.puedeVerPublicacion()
    }

    fun tieneComoMejorAmigoA(usuarioARevisar: Any) {
        this.mejoresAmigos.contains(usuarioARevisar)
    }

    fun puedeVerTodasLasPublicaciones(): Boolean {
        this.puedeVerPublicacion(publicaciones)

    }

}



