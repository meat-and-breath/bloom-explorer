package bloom.scalajs

import org.scalajs.dom.raw.{KeyboardEvent, MouseEvent}

import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

// scalajs d3 wrappers; pulling these in to make d3 available to the javascript side
import org.singlespaced.d3js.d3
import org.singlespaced.d3js.Ops._

import org.scalajs.jquery.jQuery

object BloomApp extends JSApp {

  val NumberOfBuckets = 1000

  @JSExport
  val bloom = new Bloom(NumberOfBuckets)

  /**
    * UX callback for adding a set of words to the bloom.
    */
  def addWords() = {
    val words = addWordsTextArea.value.split("""\b""").map(_.trim.toLowerCase).filterNot(_.isEmpty)
    words.foreach(bloom.addWord)
    addWordsTextArea.value = ""

    // Call the d3 update function defined in javascript
    js.Dynamic.global.update()

    // DEBUG -- lazy man's logging goes to JS console in browser
    println(s"Added ${words.length} words to bloom.")
  }

  /**
    * UX callback for checking if a word is in the bloom.
    */
  def checkForText() = {
    val word = checkWordInput.value.trim.toLowerCase

    checkWordResponse.textContent = if (bloom.containsWord(word)) s"'$word' may be present" else s"'$word' is definitely not present"
    checkWordInput.value = ""

    // Call the d3 update function defined in javascript
    js.Dynamic.global.update()
  }

  // UX Components
  // I don't like defining these here. TODO: put them in the HTML and clean this up.
  val addWordsTextArea = textarea(id := "add-text", `type` := "text", placeholder := "Enter words to insert", autofocus).render
  val addWordsButton = button(id := "add-button")("Add").render
  val checkWordInput = input(id := "check-text", `type` := "text", placeholder := "Enter a word to look for").render
  val checkWordButton = button(id := "check-button")("Check").render
  val checkWordResponse = div(id := "check-response")("").render

  def main(): Unit = {

    // Handle events from the UI
    addWordsButton.onclick = (e: MouseEvent) => addWords()
    checkWordInput.onkeypress = (e: KeyboardEvent) => if (e.keyCode == 13) { checkForText() }
    checkWordButton.onclick = (e: MouseEvent) => checkForText()

    val addBox = div(addWordsTextArea, addWordsButton).render
    val checkBox = div(checkWordInput, checkWordButton, checkWordResponse).render

    jQuery(addBox).appendTo(jQuery("#add-box"))
    jQuery(checkBox).insertAfter(jQuery("#check-header"))
  }
}
