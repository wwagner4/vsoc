package vsoc.training.vizparam

import vsoc.training.{MetaParamRun, MetaParamSeries}

object Vizparam {

  def html(run: MetaParamRun): String = {
    s"""
       |<html>
       |<head>
       |$css
       |</head
       |<body>
       |</body>
       |${tableOuter(run)}
       |</html>
     """.stripMargin
  }

  def css: String = {
    s"""
       |<style>
       |$cssProps
       |</style>
     """.stripMargin
  }

  def cssProps: String ={
    """
      |table.darkTable {
      |  font-family: Tahoma, Geneva, sans-serif;
      |  border: 2px solid #000000;
      |  background-color: #4A4A4A;
      |  width: 100%;
      |  height: 200px;
      |  text-align: center;
      |  border-collapse: collapse;
      |}
      |table.darkTable td, table.darkTable th {
      |  border: 1px solid #4A4A4A;
      |  padding: 3px 2px;
      |}
      |table.darkTable tbody td {
      |  font-size: 13px;
      |  color: #E6E6E6;
      |}
      |table.darkTable tr:nth-child(even) {
      |  background: #888888;
      |}
      |table.darkTable thead {
      |  background: #000000;
      |  border-bottom: 3px solid #000000;
      |}
      |table.darkTable thead th {
      |  font-size: 15px;
      |  font-weight: bold;
      |  color: #E6E6E6;
      |  text-align: center;
      |  border-left: 2px solid #4A4A4A;
      |}
      |table.darkTable thead th:first-child {
      |  border-left: none;
      |}
      |
      |table.darkTable tfoot {
      |  font-size: 12px;
      |  font-weight: bold;
      |  color: #E6E6E6;
      |  background: #000000;
      |  background: -moz-linear-gradient(top, #404040 0%, #191919 66%, #000000 100%);
      |  background: -webkit-linear-gradient(top, #404040 0%, #191919 66%, #000000 100%);
      |  background: linear-gradient(to bottom, #404040 0%, #191919 66%, #000000 100%);
      |  border-top: 1px solid #4A4A4A;
      |}
      |table.darkTable tfoot td {
      |  font-size: 12px;
      |}
    """.stripMargin
  }

  def tableOuter(run: MetaParamRun): String = {
    s"""
       |<table class="darkTable">
       |${rowsOuter(run)}
       |</table>
     """.stripMargin
  }

  def rowsOuter(run: MetaParamRun): String = {
    val rows: Iterator[Seq[MetaParamSeries]] = run.series.grouped(run.columns)
    rows.map(r => colsOuter(r)).mkString("<tr>", "</tr><tr>", "</tr>")
  }

  def colsOuter(cols: Seq[MetaParamSeries]): String = {
    cols.map(col => colOuter(col)).mkString("\n")
  }

  def colOuter(mp: MetaParamSeries): String = {
    s"""
      |<td>
      |${tableInner(mp)}
      |</td>
    """.stripMargin
  }

  def tableInner(ser: MetaParamSeries): String = {
    ser.description
  }
}
