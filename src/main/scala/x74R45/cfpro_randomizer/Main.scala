package x74R45.cfpro_randomizer

import org.jnativehook.{GlobalScreen, NativeHookException}

import java.util.logging.{Level, Logger}

object Main {
  def main(args: Array[String]): Unit = {
    val logger = Logger.getLogger(classOf[GlobalScreen].getPackage.getName)
    logger.setLevel(Level.OFF)

    try GlobalScreen.registerNativeHook()
    catch {
      case ex: NativeHookException =>
        System.err.println("There was a problem registering the native hook.")
        System.err.println(ex.getMessage)
        System.exit(1)
    }

    GlobalScreen.addNativeKeyListener(new HotkeyListener())

    println("Press key that will run the Randomizer.")
  }
}
