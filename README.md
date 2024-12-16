Bloom Explorer
==============

This is a toy project for exploring [Bloom Filters](http://codekata.com/kata/kata05-bloom-filters/), [Scala.js](https://www.scala-js.org), and [D3.js](https://d3js.org/).

The project generates a simple web page where you can enter words into a Bloom Filter and check for set membership. In addition, it gives you a nifty little visualization of which bits in the filter are set and which bits were checked for the word you entered.

You can see it in action here: [Bloom Filter](http://bloomexp.nfshost.com/)


Future Work
-----------

Considering that this was just a toy project to wrap my head around the basics of Scala.js and D3, there may not be any. But if it happens, here are some of the things I might work on.

- Bug fixes: there are some known functional issues, like the fact that you can enters multiple words in the lookup field and have it treat them as a single word (see "Bulk word checks", below). Speaking of which, I'm not 100% certain there aren't some corner cases in how I'm splitting words from the add text area. And I should probably do something if you enter punctuation in the check area, since we strip it when adding. The list goes on and on.

- Code cleanup: I feel like I probably have enough understanding at this point to do all the D3 code in Scala.js, which might allow me to clean up some of the engineering smell in the code (e.g., exposing the raw storage mechanism of the filter to client javascript). Of course, I'm also rather unimpressed with Scala.js, so I don't know how badly I actually want to do that.
 
- Better/alternate hash algorithms: I used the Shift-Add-XOR hash algorithm mainly because it was easy to write the code, once I figured out that I couldn't trivially use an md5 library. SAX does have the advantage of generating a failure uniform hash distribution, but it would be interesting to try more robust hashes and see how they behave.

- <a name="bulkword"></a> Bulk word checks: it would be nice to be able to check multiple words at once, but that requires some additional thinking about the UI. Supporting bulk checks is a requirement to do any sophisticated analysis of the collision behavior of different hash algorithms and bit set sizes.
 
- More insight: I'd also like to support viewing the history of all added words and drilling into individual buckets to see which words contributed.

- Performance: currently this thing gets very unhappy with even moderately large blocks of text. Pasting in the full text of [Henry IV](http://shakespeare.mit.edu/1henryiv/full.html) has caused the world to come tumbling down, before.

- More dynamic configuration: I'd like to allow resetting the Bloom Filter (and changing the number of buckets) without changing code. It shouldn't be too hard.


License
-------

In the unlikely but not impossible event that you want to reuse this code,
you may do so under the MIT License. You should have received a copy of the MIT License along with this program. If not, see
<http://www.opensource.org/licenses/mit-license.php>.
