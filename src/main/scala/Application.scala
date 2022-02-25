import certificate.Drawer
import certificate.effects.{AreaRect, Arial, DrawImage, DrawText}

import java.io.File

object Application extends App {

  val src = new File("/Users/alexeysi/Downloads/certificate_blank.jpg")
  val signature = new File("/Users/alexeysi/Downloads/signature.png")
  val stamp = new File("/Users/alexeysi/Downloads/stamp.png")


  val effects = List(
    DrawText("Алексей Сиренко", AreaRect(
      topLeftX = 200,
      topLeftY = 300,
      bottomRightX = 800,
      bottomRightY = 360
    ), Arial(size = 60)),

    DrawText("награждается сертификатом за достижение", AreaRect(
      topLeftX = 200,
      topLeftY = 360,
      bottomRightX = 800,
      bottomRightY = 420
    ), Arial(size = 30)),

    DrawText("Я Сделяль", AreaRect(
      topLeftX = 200,
      topLeftY = 420,
      bottomRightX = 800,
      bottomRightY = 500
    ), Arial(size = 50)),

    DrawImage(signature, 100, 500, 200, 150),
    DrawImage(stamp, 700, 450, 200, 200)
  )

  val drawer = new Drawer(src)

  val output = drawer.draw(effects)

  println(output.getAbsolutePath)

}
