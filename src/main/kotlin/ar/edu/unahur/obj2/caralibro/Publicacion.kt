package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion {
  abstract fun espacioQueOcupa(): Int
}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {
  val factorDeCompresion = 0.7
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt()
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Video(val calidad: Int,val duracion: Int) : Publicacion() {
  override fun espacioQueOcupa(){
    if(calidad==SD){
      return duracion
    }
    elseif (calidad==720){
      return duracion * 3
    }
    elseif (calidad==1080){
      return (duracion * 3) * 2
    }
}
