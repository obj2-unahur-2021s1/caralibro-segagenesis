package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz")
    val fotoEnCuzco = Foto(768, 1024)

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
            val videoEnCalidadSd = Video(23)
            videoEnCalidadSd.espacioQueOcupaSd().shouldBe(23)
          }
          it("Para los videos HD 720p el tamaño es igual al triple de la duración en segundos del video") {
            val videoEnCalidadHd720p = Video(23)
            videoEnCalidadHd720p.espacioQueOcupaHd720p().shouldBe(69)
          }
          it("Para los videos de HD 1080p el tamaño es el doble de los HD 720p") {
            val videoEnCalidadHd1080p = Video (23)
            videoEnCalidadHd1080p.espacioQueOcupaHd1080p().shouldBe(138)
          }

        }

      }
    }

    describe("Un usuario") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
        val usuarioCaralibro = Usuario()

        usuarioCaralibro.agregarPublicacion(fotoEnCuzco)
        usuarioCaralibro.agregarPublicacion(saludoCumpleanios)

        usuarioCaralibro.espacioDePublicaciones().shouldBe(550548)
      }
    }
  }
})
