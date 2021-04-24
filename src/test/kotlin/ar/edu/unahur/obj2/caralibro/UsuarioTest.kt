package ar.edu.unahur.obj2.caralibro

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz",0,Publico)
    val fotoEnCuzco = Foto(768, 1024,0,Publico)
    val video = Video(23,CalidadSd, 0,Publico)

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

        it("Cuando el primer usuario tiene mas amigos") {
          usuarioAmistoso.esMasAmistosoQue(usuarioNoAmistoso).shouldBeTrue()
        }
        it("No lo es cuando el segundo usuario tiene mas amigos") {
          usuarioNoAmistoso.esMasAmistosoQue(usuarioAmistoso).shouldBeFalse()
        }
      }

      describe("Puede ver una cierta publicacion") {
        it("Cuando su permiso es publico"){
          val publicacionPublica = Foto(1920, 1080,0,Publico)
          val usuario = Usuario()
          val usuario2 = Usuario()
          usuario.puedeVerPublicacion(publicacionPublica,usuario2).shouldBeTrue()
        }
        describe("Cuando su permiso es de solo amigos") {
          it("El usuario pertenece a la lista de amigos del creador de la publicacion") {
            val publicacionSoloAmigos = Foto(1920,1080,0,SoloAmigos)
            val usuario = Usuario()
            val usuarioAmigo = Usuario()
            usuarioAmigo.agregarAmigo(usuario)
            usuario.puedeVerPublicacion(publicacionSoloAmigos,usuarioAmigo).shouldBeTrue()
          }
          it("El usuario no pertence a la lista de amigos del creador de la publicacion") {
            val publicacionSoloAmigos = Foto(1920,1080,0,SoloAmigos)
            val usuario = Usuario()
            val usuario2 = Usuario()
            usuario.puedeVerPublicacion(publicacionSoloAmigos,usuario2).shouldBeFalse()
          }
        }

        describe("Cuando el permiso de la publicacion es privado Con lista de permitidos") {
          val publicacioPrivadanConListaPermitidos = Foto(1920,1080,0,PrivadoConListaDePermitidos)
          val usuario = Usuario()
          val usuario2 = Usuario()
          it("El usuario esta en la lista de permitidos del usuario al que quiere ver la publcacion") {
            usuario2.agregarAListaPermitidos(usuario)
            usuario.puedeVerPublicacion(publicacioPrivadanConListaPermitidos,usuario2).shouldBeTrue()
          }
         it("Caso contrario el usuario no esta en la lista de permitidos del usuario al que quiere ver la publicacion") {
           usuario.puedeVerPublicacion(publicacioPrivadanConListaPermitidos,usuario2).shouldBeFalse()
         }
        }
        describe("Cuando el permiso de la publicacion es publico Con lista de excluidos") {
          val publicacionPublicaConListaExcluidos = Foto(1920,1080,0,PublicoConListaDeExcluidos)
          val usuario = Usuario()
          val usuario2 = Usuario()
          it("El usuario esta en la lista de excluidos del usuario al que quiere ver la publcacion"){
            usuario2.agregarAListaExcluidos(usuario)
            usuario.puedeVerPublicacion(publicacionPublicaConListaExcluidos,usuario2).shouldBeFalse()
          }
          it("El usuario no esta en la lista de excluidos del usuario al que quiere ver la publcacion") {
            usuario.puedeVerPublicacion(publicacionPublicaConListaExcluidos,usuario2).shouldBeTrue()
          }
        }

        it("El amigo mas popular que tiene agregado el usuario") {
          val publicacionFoto = Foto(1920,1080,4,Publico)
          val publicacionTexto = Texto("Hola a todos",1,Publico)
          val publicacionVideo = Video (25,Calidad1080p,3,Publico)
          val usuario = Usuario()
          val usuarioMasPopular = Usuario()
          val usuarioPopular = Usuario()
          usuarioPopular.agregarPublicacion(publicacionFoto)
          usuarioPopular.agregarPublicacion(publicacionTexto)
          usuarioPopular.agregarPublicacion(publicacionVideo)
          usuario.agregarAmigo(usuarioPopular)
          usuario.agregarAmigo(usuarioMasPopular)

          usuario.amigoMasPopular().shouldBe(usuarioPopular)

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

      it("a"){
      }
    }

  }
})
