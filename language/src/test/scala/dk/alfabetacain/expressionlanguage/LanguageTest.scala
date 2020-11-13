package dk.alfabetacain.expressionlanguage
import org.scalatest._
import flatspec._
import matchers._

class LanguageTest extends AnyFlatSpec with should.Matchers {
  "1 + 1" should "return 2" in {
    val program = Add(IntExpr(1), IntExpr(1))

    Language.eval(program, Map.empty) should be (IntValue(2))
  }

  "1 + variable (1)" should "return 2" in {
    val program = Let(List(VariableDeclaration("x", IntExpr(1))), Add(IntExpr(1), Variable("x")))
    Language.eval(program, Map.empty) should be (IntValue(2))
  }

  "1 + id(1)" should "return 2" in {
    val program = Let(List(VariableDeclaration("id", Lambda(List("a"), Variable("a")))), Add(IntExpr(1), Call(Variable("id"), List(IntExpr(1)))))
    Language.eval(program, Map.empty) should be (IntValue(2))
  }

  "0 + add2(0)" should "return 2" in {
    val program = Let(List(VariableDeclaration("add2", Lambda(List("a"), Add(Variable("a"), IntExpr(2))))), Add(IntExpr(0), Call(Variable("add2"), List(IntExpr(0)))))
    Language.eval(program, Map.empty) should be (IntValue(2))
  }
}
