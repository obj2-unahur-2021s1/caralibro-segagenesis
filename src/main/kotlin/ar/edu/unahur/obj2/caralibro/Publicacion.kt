package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion(var likes: Int, var permiso: Permiso) {
    var usuariosQueDieronLike = mutableListOf<Usuario>()
    var accesos = mutableListOf<Usuario>()

    abstract fun espacioQueOcupa(): Int

    fun puedeSerVistaPor(usuario: Usuario,usuarioQueDeseaVerPublicacion:Usuario) = this.permiso.puedeSerVistaPor(usuario,usuarioQueDeseaVerPublicacion)
}

class Foto(val alto: Int, val ancho: Int, likes: Int, permiso: Permiso) : Publicacion(likes, permiso) {
    override fun espacioQueOcupa() = ceil(alto * ancho * FactorCompresion.compresionActual).toInt()
}

class Texto(val contenido: String, likes: Int, permiso: Permiso) : Publicacion(likes, permiso) {
    override fun espacioQueOcupa() = contenido.length
}

open class Video(val duracion: Int, var calidad: CalidadVideo, likes: Int, permiso: Permiso) : Publicacion(likes, permiso) {
    override fun espacioQueOcupa() = calidad.espacioQueOcupa(this)
}

object FactorCompresion {
    var compresionActual = 0.7
}

abstract class CalidadVideo {
    abstract fun espacioQueOcupa(video: Video): Int
}

object CalidadSd : CalidadVideo() {
    override fun espacioQueOcupa(video: Video) = video.duracion
}

object Calidad720p : CalidadVideo() {
    override fun espacioQueOcupa(video: Video) = video.duracion * 3
}

object Calidad1080p : CalidadVideo() {
    override fun espacioQueOcupa(video: Video) = Calidad720p.espacioQueOcupa(video) * 2
}

abstract class Permiso {
    abstract fun puedeSerVistaPor(usuario: Usuario,usuario2:Usuario) : Boolean
}

object Publico: Permiso() {
    override fun puedeSerVistaPor(usuario: Usuario,usuario2:Usuario) = true
}

object SoloAmigos: Permiso() {
    override fun puedeSerVistaPor(usuario: Usuario,usuario2:Usuario) = usuario2.amigos.contains(usuario)
}

object PrivadoConListaDePermitidos: Permiso() {
    override fun puedeSerVistaPor(usuario: Usuario,usuario2:Usuario) = usuario2.listaPermitidos.contains(usuario)
}

object PublicoConListaDeExcluidos: Permiso() {
    override fun puedeSerVistaPor(usuario: Usuario, usuario2: Usuario) = !usuario2.listaExcluidos.contains(usuario)
}



