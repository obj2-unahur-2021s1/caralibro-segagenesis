package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion(var likes : Int) {
    abstract fun espacioQueOcupa(): Int
}

class Foto(val alto: Int, val ancho: Int, likes: Int ) : Publicacion(likes) {
  override fun espacioQueOcupa() = ceil(alto * ancho * factorCompresion.compresionActual).toInt()

}

class Texto(val contenido: String,likes: Int) : Publicacion(likes) {
  override fun espacioQueOcupa() = contenido.length
}

open class Video(val duracion: Int, var calidad: CalidadVideo,likes: Int ) : Publicacion(likes) {
   override fun espacioQueOcupa() = calidad.espacioQueOcupa(this)
}

object factorCompresion {
  var compresionActual = 0.7
}

abstract class CalidadVideo {
    abstract fun espacioQueOcupa(video: Video) : Int
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

