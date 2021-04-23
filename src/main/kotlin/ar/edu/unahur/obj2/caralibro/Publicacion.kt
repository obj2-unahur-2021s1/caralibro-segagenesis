package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion {
    abstract fun espacioQueOcupa(): Int
}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {
  override fun espacioQueOcupa() = ceil(alto * ancho * factorCompresion.compresionActual).toInt()
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Video(val duracion: Int) : Publicacion() {
   override fun espacioQueOcupa() = 2

   fun espacioQueOcupaSd() = this.duracion

   fun espacioQueOcupaHd720p() = this.duracion * 3

   fun espacioQueOcupaHd1080p() = this.espacioQueOcupaHd720p() * 2

}

object factorCompresion {
  var compresionActual = 0.7
}

