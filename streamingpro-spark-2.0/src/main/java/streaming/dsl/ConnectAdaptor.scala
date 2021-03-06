package streaming.dsl

import _root_.streaming.dsl.parser.DSLSQLParser._

/**
  * Created by allwefantasy on 27/8/2017.
  */
class ConnectAdaptor(scriptSQLExecListener: ScriptSQLExecListener) extends DslAdaptor {
  override def parse(ctx: SqlContext): Unit = {

    var option = Map[String, String]()

    (0 to ctx.getChildCount() - 1).foreach { tokenIndex =>
      ctx.getChild(tokenIndex) match {
        case s: FormatContext =>
          option += ("format" -> s.getText)

        case s: ExpressionContext =>
          option += (cleanStr(s.identifier().getText) -> cleanStr(s.STRING().getText))
        case s: BooleanExpressionContext =>
          option += (cleanStr(s.expression().identifier().getText)-> cleanStr(s.expression().STRING().getText))
        case s: DbContext =>
          ScriptSQLExec.options(s.getText, option)
        case _ =>

      }
    }
  }
}
