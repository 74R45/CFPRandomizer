package x74R45.cfpro_randomizer

import org.jnativehook.keyboard.{NativeKeyEvent, NativeKeyListener}

import java.text.SimpleDateFormat
import java.util.Date

class HotkeyListener extends NativeKeyListener {
  var keyCode: Int = -1

  override def nativeKeyTyped(nativeKeyEvent: NativeKeyEvent): Unit = {}

  override def nativeKeyPressed(nativeKeyEvent: NativeKeyEvent): Unit = {}

  override def nativeKeyReleased(nativeKeyEvent: NativeKeyEvent): Unit = {
    val key = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode)

    if (keyCode == -1) {
      keyCode = nativeKeyEvent.getKeyCode
      println(s"Got it! Press '$key' to run the Randomizer.\n")
    }
    else if (nativeKeyEvent.getKeyCode == keyCode) {
      print(s"${new SimpleDateFormat("HH:mm:ss").format(new Date())}  ")
      RandomComboSelector.run()
    }
  }
}
