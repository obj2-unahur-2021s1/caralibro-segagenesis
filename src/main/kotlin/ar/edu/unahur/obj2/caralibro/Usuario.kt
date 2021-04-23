package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val amigos = mutableListOf<Usuario>()

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

  fun darLike(publicacion: Publicacion) {
    publicacion.likes += 1
  }

  fun agregarAmigo(usuario: Usuario){
    this.amigos.add(usuario)
  }

  fun tieneMasAmigosQue(usuario: Usuario){
    if (this.amigos.size > usuario.amigos.size) {
      return true
    }
  }
}
