package dk.alfabetacain.expressionlanguage

sealed trait Expr
final case class IntExpr(value: Int) extends Expr
final case class Add(left: Expr, right: Expr) extends Expr
final case class Let(declarations: List[VariableDeclaration], body: Expr) extends Expr
final case class Variable(name: String) extends Expr
final case class Lambda(parameters: List[String], body: Expr) extends Expr
final case class Call(fExpr: Expr, arguments: List[Expr]) extends Expr

final case class VariableDeclaration(name: String, value: Expr)

sealed trait Value
final case class IntValue(value: Int) extends Value
final case class Closure(params: List[String], body: Expr, env: Map[String, Value]) extends Value

object Language {
  type Environment = Map[String, Value]
  def eval(expr: Expr, env: Environment): Value = {
    expr match {
      case IntExpr(v) => IntValue(v)
      case Add(left, right) =>
        (eval(left, env), eval(right, env)) match {
          case (IntValue(v1), IntValue(v2)) =>
            IntValue(v1 + v2)
        }
      case Let(declarations, body) =>
        val withVariables = declarations.foldLeft(env){ case (acc, VariableDeclaration(name, value)) => acc + (name -> eval(value, acc)) }
        eval(body, withVariables)
      case Variable(x) =>
        env.get(x) match {
          case None =>
            ???
          case Some(e) =>
            e
        }
      case Call(fExpr, arguments) =>
        eval(fExpr, env) match {
          case Closure(params, body, closureEnv) =>
            val withParams = (params.zip(arguments)).foldLeft(closureEnv){ case (acc, (k, v)) => acc + (k -> eval(v, env)) }
            eval(body, withParams)
        }
      case Lambda(params, body) =>
        Closure(params, body, env)
    }
  }

}

object Main {

  def main(args: Array[String]): Unit = {
    println("hello world")
  }

}
