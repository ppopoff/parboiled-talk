package com.doingfp.bkv.recognizer

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, FunSpec}


@RunWith(classOf[JUnitRunner])
class BkvRecognizerSpec extends FunSpec with Matchers {
  describe ("Companion object") {
    describe ("Key symbol") {
      it ("should not contain whitespace characters") {
        BkvRecognizer.KeySymbol.matchesAny(" \t\n") shouldBe false
      }

      it ("may contain any alphanumeric symbol, or underscore") {
        BkvRecognizer.KeySymbol.matchesAll("AaZz0123456789_") shouldBe true
      }

      it ("should fail for other printable symbols") {
        BkvRecognizer.BlockNameSymbol.matchesAny(";/#-=(){}[]`!") shouldBe false
      }
    }

    describe ("Value symbol") {
      it ("should not contain whitespace characters") {
        BkvRecognizer.ValueSymbol.matchesAny(" \t\n") shouldBe false
      }

      it ("may contain any printable symbol") {
        BkvRecognizer.ValueSymbol.matchesAny("AaZz09_%^$#;.-+=(){}")
      }
    }

    describe("Block name symbol") {
      it ("should not contain whitespace characters") {
        BkvRecognizer.BlockNameSymbol.matchesAny(" \t\n") shouldBe false
      }

      it ("may contain any alphanumeric symbol, or underscore, or dot") {
        BkvRecognizer.BlockNameSymbol.matchesAll("AaZz0123456789_.") shouldBe true
      }

      it ("should fail for other printable symbols") {
        BkvRecognizer.BlockNameSymbol.matchesAny(";/#-=(){}[]`!") shouldBe false
      }
    }
  }

  describe ("Bkv Recognizer rules") {
    describe("WhiteSpace characters") {
      it ("should fail on empty input") {
        new BkvRecognizer("").WhiteSpace.run().isFailure shouldBe true
      }

      it ("should recognize whitespace char") {
        new BkvRecognizer(" ").WhiteSpace.run().isSuccess shouldBe true
      }

      it ("should recognize \\n as whitespace char") {
        new BkvRecognizer("\n").WhiteSpace.run().isSuccess shouldBe true
      }

      it ("should recognize \\t as whitespace char") {
        new BkvRecognizer("\t").WhiteSpace.run().isSuccess shouldBe true
      }
    }

    // todo: check are they fake?
    describe("Optional whitespaces") {
      describe ("matches zero or more whitespace chars") {
        it ("should match no whitespace chars at all") {
          new BkvRecognizer("").MayBeWS.run().isSuccess shouldBe true
        }

        it ("should match a single whitespace char") {
          new BkvRecognizer(" ").MayBeWS.run().isSuccess shouldBe true
        }

        it ("should match a multiple whitespace chars") {
          new BkvRecognizer("   ").MayBeWS.run().isSuccess shouldBe true
        }

        it ("should match different combinations of whitespace characters") {
          new BkvRecognizer("\\t\\n  ").MayBeWS.run().isSuccess shouldBe true
        }
      }
    }

    describe("Newline") {
      describe("Unix/Linux newline") {
        it ("should match unix/linux newline") {
          new BkvRecognizer("\n").NewLine.run().isSuccess shouldBe true
        }

        it ("should match Windows type of newline") {
          new BkvRecognizer("\r\n").NewLine.run().isSuccess shouldBe true
        }
      }
    }

    //todo: are they fake? add EOI somewhere
    describe("Key") {
      it ("should follow the same rules as identifier") {
//        new BkvRecognizer("valid_Key0123").Key.run().isSuccess shouldBe true
        new BkvRecognizer("invalid Key0123").Key.run().isSuccess shouldBe true
      }
    }

//    describe("Value") {
//      it ("should not allow newlines and whitespaces") {
//        val k =
//        Recognizer("value text").Value.run().isSuccess shouldBe true
//        println(k)
//      }

//      it ("value can be a text") {
        //TODO:
//      }
//    }


//    describe("Key Value pair") {
//
//    }


//    describe("Parser to have fun with") {

      import org.parboiled2._

/*      class ParserToHaveFunWith(val input: ParserInput) {
        def MyRule = rule {
          capture("a") ~ capture("b") ~ capture("c") ~ capture("d") ~ capture("e") ~ capture("f") ~ capture("g") ~
          capture("h") ~ capture("i") ~ capture("j") ~ capture("k") ~ capture("l") ~ capture("m") ~ capture("n") ~
          capture("o") ~ capture("p") ~ capture("q") ~ capture("r") ~ capture("s") ~ capture("t") ~ capture("u") ~
          capture("v") ~ capture("w") ~ capture("x") ~ capture("y") ~ capture("z") ~> (println(_ + _ + _ + _ + _ + _))
        }
      }*/

//      new ParserToHaveFunWith("abcdefghijklmnopqrstuvwxyz").MyRule.run()
//    }

  }
}
