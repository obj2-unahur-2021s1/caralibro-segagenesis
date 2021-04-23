package ar.edu.unahur.obj2.caralibro

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz",0)
    val fotoEnCuzco = Foto(768, 1024,0)
    val video = Video(23,CalidadSd, 0)

    describe("Una publicación") {
      describe("de tipo foto") {
        it("ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldBe(550503)
        }
      }

      describe("de tipo texto") {
        it("ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldBe(45)
        }
      }

      describe("de tipo video") {
        describe("Ocupa espacio dependiendo su calidad de video") {
          it("Para la calidad SD, el tamaño es igual a la duración del video en segundos") {
            video.calidad = CalidadSd
            video.espacioQueOcupa().shouldBe(23)
          }
          it("Para los videos HD 720p el tamaño es igual al triple de la duración en segundos del video") {
            video.calidad = Calidad720p
            video.espacioQueOcupa().shouldBe(69)
          }
          it("Para los videos de HD 1080p el tamaño es el doble de los HD 720p") {
            video.calidad = Calidad1080p
            video.espacioQueOcupa().shouldBe(138)
          }
        }
      }
    }

    describe("Un usuario") {
      val usuarioCaralibro = Usuario()
      it("puede calcular el espacio que ocupan sus publicaciones") {
        usuarioCaralibro.agregarPublicacion(fotoEnCuzco)
        usuarioCaralibro.agregarPublicacion(saludoCumpleanios)
        usuarioCaralibro.agregarPublicacion(video)
        usuarioCaralibro.espacioDePublicaciones().shouldBe(550571)
      }
      describe("Puede darle like") {
        it("A una publicación") {
          usuarioCaralibro.darLike(fotoEnCuzco)
          fotoEnCuzco.likes.shouldBe(1)
        }
        it("No puede darle like 2 veces a la misma publicacion") {
          usuarioCaralibro.darLike(fotoEnCuzco)
          shouldThrowAny{ usuarioCaralibro.darLike(fotoEnCuzco) }
        }
      }

      describe("Es mas amistoso que otro.") {
        val usuarioAmistoso = Usuario()
        val usuarioNoAmistoso = Usuario()
        val usuarioAmigo = Usuario()
        val usuarioAmigo2= Usuario()
        usuarioAmistoso.agregarAmigo(usuarioAmigo)
        usuarioAmistoso.agregarAmigo(usuarioAmigo2)
        usuarioNoAmistoso.agregarAmigo(usuarioAmigo)

        it("Cuando el primero tiene mas amigos") {
          usuarioAmistoso.esMasAmistosoQue(usuarioNoAmistoso).shouldBeTrue()
        }
        it("No lo es cuando el segundo usuario tiene mas amigos") {
          usuarioNoAmistoso.esMasAmistosoQue(usuarioAmistoso).shouldBeFalse()
        }
      }

    }

    describe("Cantidad de amigos") {
      val francisco = Usuario()
      val karen = Usuario()
      val matias = Usuario()
      val fernando = Usuario()

      francisco.agregarAmigo(karen)
      francisco.agregarAmigo(matias)

      it("das"){
        //francisco.tieneMasAmigosQue(fernando).shouldBeTrue()
      }
    }

    describe("Permisos de acceso"){
      val fotoEnCuzco = Foto(768, 1024,0)
      val karen = Usuario()
       fotoEnCuzco.agregarAcceso(karen)
// no cargaaaaaaaaaaaaa
      it("Accesos a una publicacion"){
        //karen.tieneAccesoA(fotoEnCuzco).shouldBeTrue()
      }
      it("Un usuario puede ver tal publicacion"){
       // karen.puedeVer(fotoEnCuzco).shoulBeTrue()
      }
    }
  }
})
