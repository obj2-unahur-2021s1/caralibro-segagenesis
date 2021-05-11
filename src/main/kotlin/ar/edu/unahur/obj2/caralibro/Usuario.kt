package ar.edu.unahur.obj2.caralibro

class Usuario {
    val publicaciones = mutableListOf<Publicacion>()
    val amigos = mutableListOf<Usuario>()
    val listaPermitidos = mutableListOf<Usuario>()
    val listaExcluidos = mutableListOf<Usuario>()

    fun agregarPublicacion(publicacion: Publicacion) {
        publicaciones.add(publicacion)
    }

    fun agregarAListaPermitidos(usuario: Usuario) = this.listaPermitidos.add(usuario)

    fun agregarAListaExcluidos(usuario: Usuario) = this.listaExcluidos.add(usuario)

    fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

    fun darLike(publicacion: Publicacion) {
        if (this.dioLikeEn(publicacion)) {
            throw Exception("Ya le has dado like a esta publicacion")
        }
        publicacion.likes += 1
        publicacion.usuariosQueDieronLike.add(this)
    }

    fun dioLikeEn(publicacion: Publicacion) = publicacion.usuariosQueDieronLike.contains(this)

    fun agregarAmigo(usuario: Usuario) {
        this.amigos.add(usuario)
    }

    fun esMasAmistosoQue(usuario: Usuario) = this.amigos.count() > usuario.amigos.count()

    fun puedeVerPublicacion(publicacion: Publicacion, usuarioQueDeseaVerPublicacion: Usuario) =
        publicacion.puedeSerVistaPor(this, usuarioQueDeseaVerPublicacion)

    fun cantidadLikes() = this.publicaciones.map { it.likes }.sum()

    fun amigoMasPopular() = this.amigos.maxByOrNull { it.cantidadLikes() }

    fun esMejorAmigo(usuarioMejorAmigo: Usuario): Boolean {
        return this.amigos.find { usuario: Usuario -> usuario == usuarioMejorAmigo } == usuarioMejorAmigo
    }

    fun publicacionesVistasPor(usuario: Usuario) =
        this.publicaciones.filter { publicacion: Publicacion -> usuario.dioLikeEn(publicacion) }

    fun noventaPorcientoDeLasPublicaciones() = this.publicaciones.size * 0.9

    fun esStalker(usuario: Usuario): Boolean {
        return this.publicacionesVistasPor(usuario).size > this.noventaPorcientoDeLasPublicaciones()
    }

}




